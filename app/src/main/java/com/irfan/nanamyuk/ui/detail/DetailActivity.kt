package com.irfan.nanamyuk.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.irfan.nanamyuk.HomeActivity
import com.irfan.nanamyuk.adapter.UserPlantsAdapter.Companion.ID
import com.irfan.nanamyuk.adapter.UserPlantsAdapter.Companion.NAME
import com.irfan.nanamyuk.adapter.UserPlantsAdapter.Companion.UID
import com.irfan.nanamyuk.data.datastore.SessionPreferences
import com.irfan.nanamyuk.databinding.ActivityDetailBinding
import com.irfan.nanamyuk.ui.ViewModelFactory


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private var token = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.extras?.get(ID).toString()
        val name = intent.extras?.get(NAME).toString()
        val uid = intent.extras?.get(UID).toString()

        Log.e("tes intent", id)

        setupViewModel()
        setupAction(id, name, uid)
    }

    private fun setupViewModel() {
        detailViewModel = ViewModelProvider(this, ViewModelFactory(SessionPreferences.getInstance(dataStore)))[DetailViewModel::class.java]
    }

    private fun setupAction(id : String, name : String, uid: String) {
        detailViewModel.getUserToken().observe(this) {
            detailViewModel.getPlants(it.token, id)
            token = it.token
        }

        detailViewModel.plants.observe(this){ plants ->
            Glide.with(this).load(plants.image).into(binding.imagePlant)
            binding.apply {
                plantTag.text = name
                plantName.text = plants.namaTanaman
                textTemperature.text = plants.suhu
                textKelembapan.text = plants.kelembapan
                textRainfall.text = plants.rainfall
                textTanah.text = plants.tanah
                textCahaya.text = plants.cahaya
                textDurasi.text = plants.durasiSiram
                textDeskripsi.text = plants.deskripsi
                textTutorial.text = plants.tutorial
            }
        }

        binding.delete.setOnClickListener{
            detailViewModel.deleteUserPlants(token, uid)

            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

            }
        }
}
