package com.irfan.nanamyuk.ui.rekomendasi

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.irfan.nanamyuk.R
import com.irfan.nanamyuk.adapter.PilihAdapter
import com.irfan.nanamyuk.databinding.ActivityPilihBinding
import com.irfan.nanamyuk.databinding.ActivityRekomendasiBinding

class RekomendasiActivity : AppCompatActivity() {
    private val datas = mutableListOf<String>()
    private lateinit var binding: ActivityRekomendasiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRekomendasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = intent.getStringExtra("title")

        createData()

        binding.rvRekomendasi.layoutManager = LinearLayoutManager(this)
        val singleAdapter = PilihAdapter(this, datas)
        binding.rvRekomendasi.adapter = singleAdapter

        singleAdapter.setOnItemClickLitener(object : PilihAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                singleAdapter.setSelection(position)
            }

        })
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

    private fun createData() {
        for (i in 0..19) {
            datas.add("Bunga$i")
        }
    }
}