package my.mjitsi.eyesgenetic.ui.main

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
        const val BROWN = "BROWN"
        const val GREY = "GREY"
        const val GREEN = "GREEN"
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


        binding.cardMother.setOnClickListener{
            findNavController().navigate(
                R.id.action_mainFragment_to_selectFragment,
                bundleOf(SelectFragment.PARENT_PERSON to "Mother",
                )
            )
        }
        binding.cardFather.setOnClickListener{
            findNavController().navigate(
                R.id.action_mainFragment_to_selectFragment,
                bundleOf(SelectFragment.PARENT_PERSON to "Father",
                )
            )
        }

        // getting data when return from selectFragment
        parentFragmentManager.setFragmentResultListener(SelectFragment.REQUEST_CODE, viewLifecycleOwner) { _, data ->
            val eyesColor = data.getString(SelectFragment.EYES_COLOR)
            Toast.makeText(requireContext(), "Color: $eyesColor", Toast.LENGTH_SHORT).show()

            when (eyesColor) {
                BROWN -> binding.cardMother.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.eyesColorBrown))
                GREY -> binding.cardMother.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.eyesColorGrey))
                GREEN -> binding.cardMother.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.eyesColorGreen))
            }

        }

    }

}