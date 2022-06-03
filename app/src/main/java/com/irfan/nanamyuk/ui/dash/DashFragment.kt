package com.irfan.nanamyuk.ui.dash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.irfan.nanamyuk.adapter.UserPlantsAdapter
import com.irfan.nanamyuk.databinding.FragmentDashboardBinding

class DashFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val datas = mutableListOf<String>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        for (i in 0..3) {
            datas.add("Bunga $i")
        }

        binding.rvNotFinish.layoutManager = LinearLayoutManager(activity)
        val adapter = UserPlantsAdapter(datas)
        binding.rvNotFinish.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}