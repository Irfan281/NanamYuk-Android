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
import com.irfan.nanamyuk.FormActivity
import com.irfan.nanamyuk.HomeActivity
import com.irfan.nanamyuk.adapter.PilihAdapter
import com.irfan.nanamyuk.data.datastore.SessionPreferences
import com.irfan.nanamyuk.databinding.ActivityPilihBinding
import com.irfan.nanamyuk.ui.ViewModelFactory
import com.irfan.nanamyuk.ui.add.AddFragment
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class PilihActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPilihBinding
    private lateinit var pilihViewModel: PilihViewModel

    private lateinit var adapter: PilihAdapter

    private var tanamanId = ""

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
            pilihViewModel.getPlants(it.token)
        }

        pilihViewModel.plants.observe(this) { plants ->
            when(method) {
                "rekomendasi" -> {

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

            val current = LocalDateTime.now()
            val tambah = current.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " Asia/Jakarta"
            val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z")
            val format = ZonedDateTime.parse(tambah, pattern).toString().split("+")

            val date = format[0] + "Z"
            Log.e("tes convert", date)

        binding.nextButton.setOnClickListener {
            pilihViewModel.getUserToken().observe(this) {

                val namaPenanda = binding.tvPenanda.text.toString()
                val user = listOf(it.id)
                val plant = listOf(tanamanId)
                val state = false

                val map = hashMapOf(
                    "Date" to date,
                    "Nama Penanda" to namaPenanda,
                    "User" to user,
                    "Plant" to plant,
                    "State" to state
                )

                if (namaPenanda.isNotEmpty()){
                    pilihViewModel.postUserPlants(it.token, map)
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    Toast.makeText(this, "Isi Terlebih Dahulu Nama Penanda", Toast.LENGTH_SHORT).show()
                }

            }
        }


    }

    private fun setupViewModel(){
        pilihViewModel = ViewModelProvider(this, ViewModelFactory(SessionPreferences.getInstance(dataStore)))[PilihViewModel::class.java]
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        currentFocus?.let {
            val closeKeyboard: InputMethodManager = getSystemService(
                Context.INPUT_METHOD_SERVICE
            ) as (InputMethodManager)
            closeKeyboard.hideSoftInputFromWindow(it.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    companion object {
        const val METHOD = "method"
    }
}