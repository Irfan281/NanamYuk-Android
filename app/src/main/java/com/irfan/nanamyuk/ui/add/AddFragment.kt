package com.irfan.nanamyuk.ui.add

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.irfan.nanamyuk.ui.form.FormActivity
import com.irfan.nanamyuk.databinding.FragmentAddBinding
import com.irfan.nanamyuk.ui.pilih.PilihActivity

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
                    val intent = Intent(activity, FormActivity::class.java)
                    startActivity(intent)
                }"rbChoice" -> {
                    val intent = Intent(activity, PilihActivity::class.java)
                    intent.putExtra("method", "pilih")
                    startActivity(intent)
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