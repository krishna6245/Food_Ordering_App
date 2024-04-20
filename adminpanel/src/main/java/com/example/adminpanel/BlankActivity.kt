package com.example.adminpanel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatDelegate
import com.example.adminpanel.databinding.ActivityBlankBinding

class BlankActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBlankBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding =  ActivityBlankBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){
        Handler().postDelayed({
            val intent = Intent(this , LoginActivity::class.java)
            startActivity(intent)
            finish()
        },0)
    }
}