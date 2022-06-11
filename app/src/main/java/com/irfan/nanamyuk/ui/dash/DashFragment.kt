package com.irfan.nanamyuk.ui.dash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.irfan.nanamyuk.HomeActivity
import com.irfan.nanamyuk.adapter.UserPlantsAdapter
import com.irfan.nanamyuk.data.api.UserPlantsResponseItem
import com.irfan.nanamyuk.data.datastore.SessionPreferences
import com.irfan.nanamyuk.databinding.FragmentDashboardBinding
import com.irfan.nanamyuk.ui.ViewModelFactory
import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DashFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private lateinit var dashViewModel: DashViewModel
//    private lateinit var adapterNotFinish: UserPlantsAdapter
//    private lateinit var adapterFinish: UserPlantsAdapter

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

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()

        dashViewModel.getUserToken().observe(viewLifecycleOwner) {
            binding.tvHalo.text = "Halo, " + it.name
            dashViewModel.getUserPlants(it.token)
            token = it.token
        }

        setupAction()

    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat", "NotifyDataSetChanged")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupAction() {
        val calendar = Calendar.getInstance()
        val date = SimpleDateFormat("EEEE, dd MMM yyyy", Locale.getDefault())

        binding.tvDate.text = date.format(calendar.time)

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

            val adapterNotFinish = UserPlantsAdapter(notFinish)
            val adapterFinish = UserPlantsAdapter(finish)

            if (UserPlants.isNotEmpty()){
                binding.rvNotFinish.layoutManager = LinearLayoutManager(activity)
                binding.rvNotFinish.adapter = adapterNotFinish

                binding.rvFinish.layoutManager = LinearLayoutManager(activity)
                binding.rvFinish.adapter = adapterFinish
            }

            adapterFinish.notifyDataSetChanged()
            adapterNotFinish.notifyDataSetChanged()

            adapterNotFinish.setOnItemClickLitener(object  : UserPlantsAdapter.OnItemClickListener {
                override fun onItemClick(view: View, position: Int, id: String, date: String) {
                    val map = hashMapOf<String, Any>(
                        "State" to true,
                        "Date" to setTanggal(date)
                    )

                    dashViewModel.updateUserPlants(token, map, id)

                    val i = Intent(activity, HomeActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(i)
                }

            })
        }

        dashViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progress.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun setTanggal(date: String): String {
        val hour = formatDate(date, "H").toInt()
        var h = 0

        val timeZone = TimeZone.of("UTC")
        var next: LocalDateTime = Clock.System.now().toLocalDateTime(timeZone)

        val d = LocalDateTime.parse(date.replace("Z", ""))
        val instant = d.toInstant(timeZone)

        return if (hour == 8) {
            instant.toString().replace("08", "17") + ":00.000Z"
        } else {

            val instantOneDayLater = instant.plus(1, DateTimeUnit.DAY, timeZone)
            next = instantOneDayLater.toLocalDateTime(timeZone)

            next.toString().replace("17", "08") + ":00.000Z"
        }
    }

    fun formatDate(currentDateString: String, pattern: String): String {
        val instant = Instant.parse(currentDateString)
        val formatter = DateTimeFormatter.ofPattern(pattern)
            .withZone(ZoneId.of("UTC"))
        return formatter.format(instant)
    }


    private fun setupViewModel(){
        dashViewModel = ViewModelProvider(this, ViewModelFactory(SessionPreferences.getInstance(requireContext().dataStore)))[DashViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}