package com.example.adminpanel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminpanel.adapters.ViewMenuAdapter
import com.example.adminpanel.dataModels.MenuItemModel
import com.example.adminpanel.dataModels.UserModel
import com.example.adminpanel.databinding.ActivityViewMenuBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ViewMenuActivity : AppCompatActivity() {
    private lateinit var binding : ActivityViewMenuBinding

    private lateinit var auth : FirebaseAuth
    private lateinit var database: FirebaseDatabase

    private lateinit var userId: String
    private lateinit var userData: UserModel
    private lateinit var restaurantName: String

    private lateinit var userReference: DatabaseReference
    private lateinit var menuReference: DatabaseReference
    private lateinit var userMenuReference: DatabaseReference

    private lateinit var menuList : MutableList<MenuItemModel>
    private lateinit var menuListReference : MutableList<String>
    private lateinit var userMenuListReference: MutableList<String>
    private lateinit var viewMenuAdapter: ViewMenuAdapter

    private lateinit var menuListener: ChildEventListener


    private fun toast(message : Any?){
        Toast.makeText(this,"$message", Toast.LENGTH_SHORT).show()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){
        initializeUiElements()
        setListeners()
        setAdapters()
        retrieveMenuItems()
    }
    private fun initializeUiElements(){
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        userId = auth.currentUser!!.uid
//        menuReference = database.reference.child("admin panel").child(userId).child()

        userReference = database.reference.child("admin panel").child("users").child(userId)
        menuReference = database.reference.child("menu")
        userMenuReference = userReference.child("menu")

        userReference.child("user data").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userData = snapshot.getValue(UserModel::class.java)!!
                restaurantName = userData.restaurantName.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                toast("Can't connect to server!! Try Again Later")
            }
        })

        menuList = mutableListOf()
        menuListReference = mutableListOf()
        userMenuListReference = mutableListOf()
    }
    private fun retrieveMenuItems(){
        menuListener = object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val currentItemReference = snapshot.getValue(String::class.java)
                if(currentItemReference != null){
                    menuReference.child(currentItemReference).get().addOnSuccessListener {
                        val menuItem = it.getValue(MenuItemModel::class.java)!!

                        menuItem.let {
                            menuList.add(menuItem)
                        }
                        menuListReference.add(currentItemReference)
                        userMenuListReference.add(snapshot.key!!)

                        viewMenuAdapter.notifyItemInserted(menuList.size-1)
                    }
                }
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?){}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onCancelled(error: DatabaseError) {}
        }
        userMenuReference.addChildEventListener(menuListener)
    }
    private fun setAdapters(){
        viewMenuAdapter = ViewMenuAdapter(this,menuList,menuListReference,userMenuListReference)
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
    override fun onDestroy() {
        super.onDestroy()
        userMenuReference.removeEventListener(menuListener)
    }
}