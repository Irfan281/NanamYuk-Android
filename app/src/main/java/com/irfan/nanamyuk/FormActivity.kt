package com.irfan.nanamyuk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.irfan.nanamyuk.databinding.ActivityFormBinding
import com.irfan.nanamyuk.databinding.ActivityHomeBinding

class FormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}