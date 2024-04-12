package com.example.foodorderingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatDelegate
import com.example.foodorderingapp.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){
        setListeners()
    }
    private fun setListeners(){
        binding.startActivityNextButton.setOnClickListener{
            Handler().postDelayed({
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
                finish()
            },0)
        }
    }
}