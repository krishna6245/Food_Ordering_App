package com.example.adminpanel

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.adminpanel.databinding.ActivitySplashScreenBinding


@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){
        Handler().postDelayed({
            val intent = Intent(this , LoginActivity::class.java)
            startActivity(intent)
            finish()
        },3000)
    }
}