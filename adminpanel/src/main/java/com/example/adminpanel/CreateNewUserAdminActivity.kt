package com.example.adminpanel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.adminpanel.databinding.ActivityCreateNewUserAdminBinding

class CreateNewUserAdminActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCreateNewUserAdminBinding
    private lateinit var locationList : MutableList<String>
    private lateinit var locationItemAdapter : ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNewUserAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){
        setLists()
        setAdapters()
        setListeners()
    }
    private fun setLists(){
        locationList = mutableListOf("Agra","Firozabad","Mathura","Hathras","Tundla","Samshabad","Aligarh","Shikohabad","Mainpuri")
    }
    private fun setAdapters(){
        locationItemAdapter = ArrayAdapter(this , android.R.layout.simple_list_item_1 , locationList)
        binding.createNewUserActivityLocationList.setAdapter(locationItemAdapter)
    }
    private fun setListeners(){
        binding.apply {
            createNewUserActivityBackButton.setOnClickListener{
                finish()
            }
            createNewUserActivityCreateAccountButton.setOnClickListener {
                val intent = Intent(this@CreateNewUserAdminActivity , MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}