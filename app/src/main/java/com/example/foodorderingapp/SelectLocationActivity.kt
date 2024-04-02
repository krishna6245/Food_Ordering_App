package com.example.foodorderingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ArrayAdapter
import com.example.foodorderingapp.databinding.ActivitySelectLocationBinding

class SelectLocationActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySelectLocationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val locationList = arrayOf("Agra","Firozabad","Mathura","Hathras","Tundla","Samshabad","Aligarh","Shikohabad","Mainpuri")
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,locationList)
        binding.selectLocationActivityLocationList.setAdapter(adapter)

        binding.selectLocationActivityCreateAccount.setOnClickListener{
            Handler().postDelayed({
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            },0)
        }
    }
}