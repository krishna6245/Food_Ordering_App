package com.example.adminpanel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminpanel.adapters.PendingOrderAdapter
import com.example.adminpanel.databinding.ActivityPendingOrderBinding

class PendingOrderActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPendingOrderBinding
    private lateinit var customerNames : MutableList<String>
    private lateinit var images : MutableList<Int>
    private lateinit var quantities : MutableList<Int>
    private lateinit var adapter: PendingOrderAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendingOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){
        setLists()
        setAdapters()
        setListeners()
    }
    private fun setLists(){
        customerNames = mutableListOf("Kritarth","Sahil","Krishna","Rudra","Sanidhya","Piyush","Dhruv","Sarthak")
        quantities = mutableListOf(2,1,2,4,3,10,5,1)
        val a = R.drawable.dummy_image
        val b = R.drawable.dummy_image_1
        images = mutableListOf(a,b,a,b,a,b,a,b)
    }
    private fun setAdapters(){
        adapter = PendingOrderAdapter(applicationContext,customerNames,images,quantities)
        binding.pendingOrderActivityItemList.layoutManager = LinearLayoutManager(applicationContext)
        binding.pendingOrderActivityItemList.adapter = adapter
    }
    private fun setListeners(){
        binding.pendingOrderActivityBackButton.setOnClickListener{
            finish()
        }
    }
}