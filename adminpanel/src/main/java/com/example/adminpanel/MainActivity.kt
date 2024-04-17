package com.example.adminpanel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.adminpanel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding :ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){
        setListeners()
    }
    private fun setListeners(){
        binding.apply {
            mainActivityAddItemButton.setOnClickListener {
                val intent = Intent(this@MainActivity , AddFoodItemActivity::class.java)
                startActivity(intent)
            }
            mainActivityViewMenuButtom.setOnClickListener {
                val intent = Intent(this@MainActivity , ViewMenuActivity::class.java)
                startActivity(intent)
            }
        }
    }
}