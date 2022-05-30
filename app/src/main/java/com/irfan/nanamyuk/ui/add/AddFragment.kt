package com.irfan.nanamyuk.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.irfan.nanamyuk.databinding.FragmentAddBinding

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null

    private val binding get() = _binding!!
    private var addMethod = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nextButton.setOnClickListener {
            val addMethodID = binding.rgMethod.checkedRadioButtonId

            if(addMethodID != -1) {
                addMethod = resources.getResourceEntryName(addMethodID)
            }

            when(addMethod){
                "rbRecom" -> {
                    Toast.makeText(activity, "Ini rekomendasi", Toast.LENGTH_SHORT).show()
                }"rbChoice" -> {
                    Toast.makeText(activity, "Ini pilih tanaman", Toast.LENGTH_SHORT).show()
                }else -> {
                    Toast.makeText(activity, "Silakan pilih dulu opsi yang tersedia", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}