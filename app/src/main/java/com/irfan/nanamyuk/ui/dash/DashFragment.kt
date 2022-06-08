package com.irfan.nanamyuk.ui.dash

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.irfan.nanamyuk.adapter.UserPlantsAdapter
import com.irfan.nanamyuk.data.api.UserPlantsResponseItem
import com.irfan.nanamyuk.data.datastore.SessionPreferences
import com.irfan.nanamyuk.databinding.FragmentDashboardBinding
import com.irfan.nanamyuk.ui.ViewModelFactory
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DashFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private lateinit var dashViewModel: DashViewModel

    private var token = ""

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupAction()

    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupAction() {
        val current = LocalDateTime.now()

        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
        val formatted = current.format(formatter)

        val calendar = Calendar.getInstance()
        val date = SimpleDateFormat("EEEE, dd MMM yyyy", Locale.getDefault())

        binding.tvDate.text = date.format(calendar.time)

        dashViewModel.getUserToken().observe(viewLifecycleOwner) {
            binding.tvHalo.text = "Halo, " + it.name
            dashViewModel.getUserPlants(it.token)
            token = it.token
        }

        dashViewModel.userplants.observe(viewLifecycleOwner) { UserPlants ->
            val notFinish = mutableListOf<UserPlantsResponseItem>()
            val finish = mutableListOf<UserPlantsResponseItem>()

            for (i in UserPlants) {
                if (i.state){
                    finish.add(i)
                } else {
                    notFinish.add(i)
                }
            }
            binding.rvNotFinish.layoutManager = LinearLayoutManager(activity)
            val adapterNotFinish = UserPlantsAdapter(notFinish)
            binding.rvNotFinish.adapter = adapterNotFinish

            binding.rvFinish.layoutManager = LinearLayoutManager(activity)
            val adapterFinish = UserPlantsAdapter(finish)
            binding.rvFinish.adapter = adapterFinish

            adapterNotFinish.setOnItemClickLitener(object  : UserPlantsAdapter.OnItemClickListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onItemClick(view: View, position: Int, id: String) {
                    val map = hashMapOf<String, Any>(
                        "State" to true
                    )
                    dashViewModel.updateUserPlants(token, map, id)


                    Toast.makeText(activity, "ini fab waterr", Toast.LENGTH_SHORT).show()
                }

            })
        }

        dashViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progress.visibility = if (isLoading) View.VISIBLE else View.GONE
        }


    }

    private fun setupViewModel(){
        dashViewModel = ViewModelProvider(this, ViewModelFactory(SessionPreferences.getInstance(requireContext().dataStore)))[DashViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}