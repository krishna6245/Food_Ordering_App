package com.example.adminpanel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.adminpanel.databinding.ActivityAddFoodItemBinding

class AddFoodItemActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddFoodItemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFoodItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){
        setListeners()
    }
    private fun setListeners(){
        binding.addFoodItemActivityBackButton.setOnClickListener {
            finish()
        }
    }
}