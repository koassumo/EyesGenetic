package my.mjitsi.eyesgenetic.ui.main

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import my.mjitsi.eyesgenetic.R
import my.mjitsi.eyesgenetic.databinding.SelectFragmentBinding

class SelectFragment : Fragment() {

    companion object {
        fun newInstance() = SelectFragment()
        // для получения данных из предыдущего фрагмента
        const val PARENT_PERSON = "PARENT_PERSON"
        // для возвращения данных в предыдущий фрагмент
        const val REQUEST_CODE = "RANDOM_NUMBER_REQUEST_CODE"
        const val EYES_COLOR = "EYES_COLOR"
    }

    private lateinit var viewModel: SelectViewModel

    private var _binding: SelectFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.select_fragment, container, false)
        _binding = SelectFragmentBinding.bind(view)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SelectViewModel::class.java)

        // get data from bundle
        val parentPerson = requireArguments().getString(PARENT_PERSON)
        binding.messageSelect.text = "Select $parentPerson's Eyes Color"

        // transfer data back to mainFragment
        binding.cardBrown.setOnClickListener{
            parentFragmentManager.setFragmentResult(REQUEST_CODE, bundleOf(EYES_COLOR to MainFragment.BROWN))
            findNavController().popBackStack()
        }
        binding.cardGrey.setOnClickListener{
            parentFragmentManager.setFragmentResult(REQUEST_CODE, bundleOf(EYES_COLOR to MainFragment.GREY))
            findNavController().popBackStack()
        }
        binding.cardGreen.setOnClickListener{
            parentFragmentManager.setFragmentResult(REQUEST_CODE, bundleOf(EYES_COLOR to MainFragment.GREEN))
            findNavController().popBackStack()
        }
    }

}