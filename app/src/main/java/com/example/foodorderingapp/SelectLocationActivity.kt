package com.example.foodorderingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.example.foodorderingapp.databinding.ActivitySelectLocationBinding

class SelectLocationActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySelectLocationBinding
    private lateinit var locationList: MutableList<String>

    private lateinit var location : String

    private lateinit var locationAutoCompleteTextView: AutoCompleteTextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init(){
        setAdapters()
        initializeUiElements()
        setListeners()
    }
    private fun setAdapters(){
        locationList = mutableListOf("Agra","Firozabad","Mathura","Hathras","Tundla","Samshabad","Aligarh","Shikohabad","Mainpuri")
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,locationList)
        binding.selectLocationActivityLocationList.setAdapter(adapter)
    }
    private fun initializeUiElements(){
        locationAutoCompleteTextView = binding.selectLocationActivityLocationList
    }
    private fun setListeners(){
        binding.selectLocationActivityCreateAccount.setOnClickListener {
            getUserData()
            if(validateUserdata()) {
                createNewUser()

                val intent = Intent(this@SelectLocationActivity, MainActivity::class.java)
                startActivity(intent)
                finish()

            }
        }

        binding.selectLocationActivityLocationList.setOnItemClickListener { _, _, _, _ ->
            locationAutoCompleteTextView.error = null
        }
    }
    private fun getUserData(){
        location = locationAutoCompleteTextView.text.toString()
    }
    private fun validateUserdata() : Boolean{

        // Checks on Location
        if (location.isEmpty()){
            locationAutoCompleteTextView.error = "Select a Location"
            locationAutoCompleteTextView.requestFocus()
            return false
        }
        return true
    }
    private fun createNewUser(){}

}