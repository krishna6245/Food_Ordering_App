package com.example.adminpanel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminpanel.adapters.OrderDispatchAdapter
import com.example.adminpanel.databinding.ActivityOrderDispatchBinding

class OrderDispatchActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOrderDispatchBinding
    private lateinit var customerNames : MutableList<String>
    private lateinit var deliveryStatus : MutableList<Boolean>
    private lateinit var paymentStatus : MutableList<Boolean>
    private lateinit var itemAdapter: OrderDispatchAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDispatchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){
        setLists()
        setAdapters()
        setListeners()
    }
    private fun setLists(){
        customerNames = mutableListOf("Sahil","Krishna","Kritarth","Rudra","Nandani")
        deliveryStatus = mutableListOf(true,true,false,false,true)
        paymentStatus = mutableListOf(false,true,false,false,true)
    }
    private fun setAdapters(){
        itemAdapter = OrderDispatchAdapter(applicationContext , customerNames , paymentStatus , deliveryStatus)
        binding.orderDispatchActivityItemList.layoutManager = LinearLayoutManager(applicationContext)
        binding.orderDispatchActivityItemList.adapter = itemAdapter
    }
    private fun setListeners(){
        binding.orderDispatchActivityBackButton.setOnClickListener{
            finish()
        }
    }
}