package com.example.foodorderingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ArrayAdapter
import com.example.foodorderingapp.databinding.ActivitySelectLocationBinding

class SelectLocationActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySelectLocationBinding
    private lateinit var locationList: MutableList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init(){
        setAdapters()
        setListeners()
    }
    private fun setAdapters(){
        locationList = mutableListOf("Agra","Firozabad","Mathura","Hathras","Tundla","Samshabad","Aligarh","Shikohabad","Mainpuri")
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,locationList)
        binding.selectLocationActivityLocationList.setAdapter(adapter)
    }
    private fun setListeners(){
        binding.selectLocationActivityCreateAccount.setOnClickListener{
            Handler().postDelayed({
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            },0)
        }
    }

}