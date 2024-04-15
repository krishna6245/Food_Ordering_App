package com.example.adminpanel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.adminpanel.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySignupBinding
    private lateinit var locationList : MutableList<String>
    private lateinit var locationItemAdapter : ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
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
        binding.signupActivityLocationList.setAdapter(locationItemAdapter)
    }
    private fun setListeners(){
        binding.apply {
            signupActivityAlreadyHaveAcountButton.setOnClickListener {
                finish()
            }
            signupActivityCreateAccountButton.setOnClickListener {
                val intent = Intent(this@SignupActivity , MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
    }
}