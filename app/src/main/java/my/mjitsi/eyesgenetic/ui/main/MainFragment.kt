package my.mjitsi.eyesgenetic.ui.main

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import my.mjitsi.eyesgenetic.R
import my.mjitsi.eyesgenetic.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
        const val NOT_SELECTED = "NOT_SELECTED"
        const val BROWN = "BROWN"
        const val GREY = "GREY"
        const val GREEN = "GREEN"
        const val MOTHER = "MOTHER"
        const val FATHER = "FATHER"

        var eyesMotherColor = NOT_SELECTED
        var eyesFatherColor = NOT_SELECTED
    }

    private lateinit var viewModel: MainViewModel


    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        _binding = MainFragmentBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        fillCardMotherColor()
        fillCardFatherColor()
        checkChildRenderNeeded()

        binding.cardMother.setOnClickListener{
            findNavController().navigate(
                R.id.action_mainFragment_to_selectFragment,
                bundleOf(SelectFragment.PARENT_PERSON to MOTHER,
                )
            )
        }
        binding.cardFather.setOnClickListener{
            findNavController().navigate(
                R.id.action_mainFragment_to_selectFragment,
                bundleOf(SelectFragment.PARENT_PERSON to FATHER,
                )
            )
        }

        // getting data when return from selectFragment
        parentFragmentManager.setFragmentResultListener(SelectFragment.REQUEST_CODE_MOTHER, viewLifecycleOwner) { _, data ->
            eyesMotherColor = data.getString(SelectFragment.EYES_COLOR).toString()
            fillCardMotherColor()
            checkChildRenderNeeded()
        }
        // getting data when return from selectFragment
        parentFragmentManager.setFragmentResultListener(SelectFragment.REQUEST_CODE_FATHER, viewLifecycleOwner) { _, data ->
            eyesFatherColor = data.getString(SelectFragment.EYES_COLOR).toString()
            fillCardFatherColor()
            checkChildRenderNeeded()
        }
    }

    private fun fillCardMotherColor() {
        if (eyesMotherColor == NOT_SELECTED) {
            binding.ivMotherQuestion.visibility = View.VISIBLE
            binding.ivMotherEye.visibility = View.GONE
        } else {
            binding.ivMotherQuestion.visibility = View.GONE
            binding.ivMotherEye.visibility = View.VISIBLE
            when (eyesMotherColor) {
                BROWN -> binding.cardMother.setCardBackgroundColor(
                    ContextCompat.getColor(requireContext(), R.color.eyesColorBrown)
                )
                GREY -> binding.cardMother.setCardBackgroundColor(
                    ContextCompat.getColor(requireContext(), R.color.eyesColorGrey)
                )
                GREEN -> binding.cardMother.setCardBackgroundColor(
                    ContextCompat.getColor(requireContext(), R.color.eyesColorGreen)
                )
            }
        }
    }

    private fun fillCardFatherColor() {
        if (eyesFatherColor == NOT_SELECTED) {
            binding.ivFatherQuestion.visibility = View.VISIBLE
            binding.ivFatherEye.visibility = View.GONE
        } else {
            binding.ivFatherQuestion.visibility = View.GONE
            binding.ivFatherEye.visibility = View.VISIBLE
            when (eyesFatherColor) {
                BROWN -> binding.cardFather.setCardBackgroundColor(
                    ContextCompat.getColor(requireContext(), R.color.eyesColorBrown)
                )
                GREY -> binding.cardFather.setCardBackgroundColor(
                    ContextCompat.getColor(requireContext(), R.color.eyesColorGrey)
                )
                GREEN -> binding.cardFather.setCardBackgroundColor(
                    ContextCompat.getColor(requireContext(), R.color.eyesColorGreen)
                )
            }
        }
    }

    private fun checkChildRenderNeeded() {
        if (eyesMotherColor!=NOT_SELECTED && eyesFatherColor!=NOT_SELECTED) countColor()
    }

    private fun countColor() {
        val parentsColor = Pair (eyesMotherColor, eyesFatherColor)
        // if (parentsColor == BROWN to BROWN) {val sfdad: Int = 23}
        //Toast.makeText(requireContext(), "Colors: $parentsColor", Toast.LENGTH_SHORT).show()

        val childColor = when (parentsColor) {
            BROWN to BROWN -> Triple (75.0, 6.25, 18.75)
            GREEN to BROWN -> Triple (50.0, 12.5, 37.5)
            BROWN to GREEN -> Triple (50.0, 12.5, 37.5)
            GREY to BROWN -> Triple (50.0, 50.0, 0.0)
            BROWN to GREY -> Triple (50.0, 50.0, 0.0)
            GREEN to GREEN -> Triple (1.0, 25.0, 75.0)
            GREEN to GREY -> Triple (0.0, 50.0, 50.0)
            GREY to GREEN -> Triple (0.0, 50.0, 50.0)
            GREY to GREY -> Triple (0.0, 99.0, 1.0)
            else ->         Triple (0.0, 0.0, 0.0)
        }
        displayChildColor (childColor)
    }

    @SuppressLint("SetTextI18n")
    private fun displayChildColor (childColor: Triple <Double, Double, Double>) {
        binding.cardBrown.visibility = View.GONE
        binding.cardGrey.visibility = View.GONE
        binding.cardGreen.visibility = View.GONE
        binding.messageBrown.visibility = View.GONE
        binding.messageGrey.visibility = View.GONE
        binding.messageGreen.visibility = View.GONE

        binding.messageChild.visibility = View.VISIBLE
        binding.messageBrown.text = "${childColor.first} %"
        binding.messageGrey.text = "${childColor.second} %"
        binding.messageGreen.text = "${childColor.third} %"

        if (childColor.first != 0.0) {
            binding.cardBrown.visibility = View.VISIBLE
            binding.messageBrown.visibility = View.VISIBLE
        }
        if (childColor.second != 0.0) {
            binding.cardGrey.visibility = View.VISIBLE
            binding.messageGrey.visibility = View.VISIBLE
        }
        if (childColor.third != 0.0) {
            binding.cardGreen.visibility = View.VISIBLE
            binding.messageGreen.visibility = View.VISIBLE
        }
    }
}