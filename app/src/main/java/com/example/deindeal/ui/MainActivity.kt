package com.example.deindeal.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.deindeal.R
import com.example.deindeal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)

    }
}