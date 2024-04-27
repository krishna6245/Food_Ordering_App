package com.example.foodorderingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatDelegate
import com.example.foodorderingapp.databinding.ActivitySplashScreenBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class SplashScreen : AppCompatActivity() {
    private lateinit var binding : ActivitySplashScreenBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){
        initialiseUiReferences()
        redirect()
    }
    private fun initialiseUiReferences(){
        FirebaseApp.initializeApp(this)

        auth = FirebaseAuth.getInstance()
    }
    private fun redirect(){
        val user = auth.currentUser
        if(user == null){
            Handler().postDelayed({
                val intent = Intent(this , StartActivity::class.java)
                startActivity(intent)
                finish()
            },2000)
        }
        else{
            Handler().postDelayed({
                val intent = Intent(this , MainActivity::class.java)
                startActivity(intent)
                finish()
            },2000)
        }
    }
}