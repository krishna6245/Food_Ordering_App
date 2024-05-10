package com.example.adminpanel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.disklrucache.DiskLruCache.Value
import com.example.adminpanel.adapters.ViewMenuAdapter
import com.example.adminpanel.dataModels.MenuItemModel
import com.example.adminpanel.databinding.ActivityViewMenuBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ViewMenuActivity : AppCompatActivity() {
    private lateinit var binding : ActivityViewMenuBinding

    private lateinit var auth : FirebaseAuth
    private lateinit var database: FirebaseDatabase

    private lateinit var menuList : MutableList<MenuItemModel>
    private lateinit var allItemFoodQuantities : MutableList<Int>

    private lateinit var viewMenuAdapter: ViewMenuAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){
        setListeners()
        initializeUiElements()
    }
    private fun initializeUiElements(){
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        menuList = mutableListOf()

        allItemFoodQuantities = mutableListOf(1,2,3,10,5,2,8,5)

        retrieveMenuItems()

    }
    private fun retrieveMenuItems(){
        val menuReference = database.reference.child("menu")

        menuReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                menuList.clear()

                for (menuItemSnapshot in snapshot.children){
                    val menuItem = menuItemSnapshot.getValue(MenuItemModel::class.java)
                    menuItem?.let {
                        menuList.add(menuItem)
                    }
                }
                setAdapters()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Error","Database Error in ViewMenuActivity:- ${error.toString()}")
            }
        })
    }
    private fun setAdapters(){
        viewMenuAdapter = ViewMenuAdapter(this , menuList , allItemFoodQuantities)
        binding.viewMenuActivityItemList.layoutManager = LinearLayoutManager(applicationContext)
        binding.viewMenuActivityItemList.adapter = viewMenuAdapter
    }
    private fun setListeners(){
        binding.apply {
            viewMenuActivityBackButton.setOnClickListener {
                finish()
            }
        }
    }
}