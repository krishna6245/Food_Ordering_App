package com.example.adminpanel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.adminpanel.databinding.ActivityProfileBinding
import android.widget.Toast
import com.example.adminpanel.dataModels.MenuItemModel
import com.example.adminpanel.dataModels.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    private lateinit var userId: String
    private lateinit var userReference: DatabaseReference
    private lateinit var menuReference: DatabaseReference
    private lateinit var userMenuReference: DatabaseReference
    private lateinit var businessName: String
    private var userData: UserModel = UserModel()

    private var canEdit: Boolean = false

    private fun toast(message: Any?){
        Toast.makeText(this, message.toString(), Toast.LENGTH_SHORT).show()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){
        initializeUiElements()
        setListeners()
    }
    private fun initializeUiElements(){
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        userId = auth.currentUser?.uid.toString()
        userReference = database.reference.child("admin panel").child("users").child(userId)
        menuReference = database.reference.child("menu")
        userMenuReference = userReference.child("menu")

        userReference.child("user data").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userData = snapshot.getValue(UserModel::class.java)!!
                businessName = userData.restaurantName!!
                fillUserData()
            }
            override fun onCancelled(error: DatabaseError) {
                toast("Can't connect to server")
            }
        })


        binding.profileActivityEmail.isEnabled = false
        editUserData()

        binding.profileActivityClickToEditButton.setOnClickListener{
            editUserData()
        }
    }
    private fun setListeners(){
        binding.profileActivityBackButton.setOnClickListener{
            finish()
        }
        binding.profileActivitySaveButton.setOnClickListener {
            updateUserData()
        }
    }
    private fun fillUserData(){
        binding.apply {
            profileActivityUserName.setText(userData.userName)
            profileActivityBusinessName.setText(userData.restaurantName)
            profileActivityEmail.setText(userData.email)
            profileActivityPhoneNumber.setText(userData.phoneNumber)
        }
    }
    private fun editUserData(){
        binding.apply {
            profileActivityUserName.isEnabled = canEdit
            profileActivityBusinessName.isEnabled = canEdit
            profileActivityPhoneNumber.isEnabled = canEdit
            canEdit = !canEdit
        }
    }
    private fun updateUserData(){
        userData.userName = binding.profileActivityUserName.text.toString()
        userData.restaurantName = binding.profileActivityBusinessName.text.toString()
        userData.phoneNumber = binding.profileActivityPhoneNumber.text.toString()

        if(businessName != userData.restaurantName){
            toast("Please wait while we update your menu items")
            updateMenuItems()
        }
        else{
            saveUserData()
        }
    }
    private fun saveUserData(){
        userReference.child("user data").setValue(userData)
            .addOnSuccessListener {
                toast("Data updated")
                binding.profileActivitySaveButton.isEnabled = false
                finish()
            }
            .addOnFailureListener{
                toast("Can't update data")
            }
    }
    private fun updateMenuItems(){
        userMenuReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for( menuSnapshot in snapshot.children){
                    val menuItemReference = menuSnapshot.getValue(String::class.java)
                    if (menuItemReference != null) {
                        menuReference.child(menuItemReference).get().addOnSuccessListener {
                            val menuItem = it.getValue(MenuItemModel::class.java)
                            if (menuItem != null) {
                                menuItem.restaurantName = userData.restaurantName
                                menuReference.child(menuItemReference).setValue(menuItem)
                            }
                        }
                    }
                }
                saveUserData()
            }

            override fun onCancelled(error: DatabaseError) {
                toast("Can't change")
            }

        })

    }
}