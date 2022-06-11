package com.irfan.nanamyuk.ui.pilih

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.irfan.nanamyuk.HomeActivity
import com.irfan.nanamyuk.adapter.PilihAdapter
import com.irfan.nanamyuk.data.datastore.SessionPreferences
import com.irfan.nanamyuk.databinding.ActivityPilihBinding
import com.irfan.nanamyuk.ui.ViewModelFactory
import com.parassidhu.simpledate.toDateYMDInDigits
import com.parassidhu.simpledate.toTimeStandardWithoutSeconds
import com.parassidhu.simpledate.toZuluFormat

import me.moallemi.tools.extension.date.adjust
import me.moallemi.tools.extension.date.now
import me.moallemi.tools.extension.date.today


import java.util.*

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class PilihActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPilihBinding
    private lateinit var pilihViewModel: PilihViewModel

    private lateinit var adapter: PilihAdapter

    private var tanamanId = ""
    private var token = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPilihBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val method = intent.extras?.get(METHOD).toString()

        setupViewModel()
        setupAction(method)
    }


    @SuppressLint("NewApi")
    private fun setupAction(method: String) {
        val layoutManager = LinearLayoutManager(this)
        binding.rvPlant.layoutManager = layoutManager
      
        pilihViewModel.getUserToken().observe(this) {
            token = it.token
            pilihViewModel.getPlants(it.token)
        }

        pilihViewModel.plants.observe(this) { plants ->
            when(method) {
                "rekomendasi" -> {
                    binding.tvTambah.text = "Rekomendasi Tanaman"
                    val tanah = intent.extras?.get("idTanah").toString()
                    val intensitas = intent.extras?.get("intensitas").toString()
                    val kota = intent.extras?.get("kota").toString()

                    pilihViewModel.getRecom(token, tanah, intensitas, kota)
                    pilihViewModel.recoms.observe(this) { recomPlants ->
                        if (recomPlants.response == 404) {
                            Toast.makeText(this, "Kota tidak ditemukan :(", Toast.LENGTH_SHORT).show()
                            finish()
                        } else if(recomPlants.response == 200) {
                            val recoms = mutableListOf<PlantResponseItem>()

                            for(plant in plants) {
                                when (plant.namaTanaman) {
                                    recomPlants.plant1 -> {
                                        recoms.add(plant)
                                    }
                                    recomPlants.plant2 -> {
                                        recoms.add(plant)
                                    }
                                    recomPlants.plant3 -> {
                                        recoms.add(plant)
                                    }
                                    recomPlants.plant4 -> {
                                        recoms.add(plant)
                                    }
                                    recomPlants.plant5 -> {
                                        recoms.add(plant)
                                    }
                                }
                            }

                            Log.e("fafu", recoms.toString())

                            adapter = PilihAdapter(recoms)

                            binding.rvPlant.adapter = adapter

                            adapter.setOnItemClickLitener(object : PilihAdapter.OnItemClickListener {
                                override fun onItemClick(view: View, position: Int) {

                                    tanamanId = adapter.SingleViewHolder(view).mTvId.text.toString()

                                    adapter.setSelection(position)
                                }
                            })
                        }
                    }
                }
                "pilih" -> {
                    adapter = PilihAdapter(plants)

                    binding.rvPlant.adapter = adapter

                    adapter.setOnItemClickLitener(object : PilihAdapter.OnItemClickListener {
                        override fun onItemClick(view: View, position: Int) {

                            tanamanId = adapter.SingleViewHolder(view).mTvId.text.toString()

                            adapter.setSelection(position)
                        }
                    })
                }
            }
        }

        pilihViewModel.isLoading.observe(this) { isLoading ->
            binding.progress.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        binding.nextButton.setOnClickListener {
            pilihViewModel.getUserToken().observe(this) {

                val namaPenanda = binding.tvPenanda.text.toString()
                val user = listOf(it.id)
                val plant = listOf(tanamanId)
                val state = false

                val map = hashMapOf(
                    "Date" to setTanggal(),
                    "Nama Penanda" to namaPenanda,
                    "User" to user,
                    "Plant" to plant,
                    "State" to state
                )

                if (namaPenanda.isNotEmpty()){
                    pilihViewModel.postUserPlants(it.token, map)
                    pilihViewModel.state.observe(this){ state ->
                        Log.e("nilai state", state.toString())
                        if (state) {
                            val intent = Intent(this, HomeActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        }
                    }

                } else {
                    Toast.makeText(this, "Isi Terlebih Dahulu Nama Penanda", Toast.LENGTH_SHORT).show()
                }

            }
        }


    }

    private fun setTanggal(): String {
        return now().toZuluFormat()
    }

    private fun setupViewModel(){
        pilihViewModel = ViewModelProvider(this, ViewModelFactory(SessionPreferences.getInstance(dataStore)))[PilihViewModel::class.java]
    }

    companion object {
        const val METHOD = "method"
    }
}