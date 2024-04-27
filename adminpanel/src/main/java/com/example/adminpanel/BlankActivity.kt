package com.example.adminpanel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.FirebaseApp
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
        initialiseUiReferences()
        redirect()
    }
    private fun initialiseUiReferences(){
        FirebaseApp.initializeApp(this)
    }
    private fun redirect(){
        val intent = Intent(this , SplashScreen::class.java)
        startActivity(intent)
        finish()
    }
}