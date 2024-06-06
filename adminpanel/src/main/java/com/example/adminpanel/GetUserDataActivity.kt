package com.example.adminpanel

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.adminpanel.dataModels.UserModel
import com.example.adminpanel.databinding.ActivityGetUserDataBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.ref.Reference

class GetUserDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGetUserDataBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    private lateinit var userId: String
    private lateinit var userReference: DatabaseReference
    private lateinit var userData: UserModel

    fun toast(data : Any?){
        Toast.makeText(this,"$data",Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetUserDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){
        initializeUiElements()
        setListeners()
    }
    private fun initializeUiElements(){
        val titleText = intent.getStringExtra("key_title")?: "Complete your profile"
        binding.getUserDataActivityAppTitleText.text = titleText

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        userId = auth.currentUser!!.uid
        userReference = database.reference.child("admin panel").child("users").child(userId)

        val email = auth.currentUser!!.email
        userData = UserModel(email = email)

        userReference.child("user data").addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    if(snapshot.key != null){
                        userData = snapshot.getValue(UserModel::class.java)!!
                        fillUserData()
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                toast("Can't connect to server\nCheck your network connection")
            }
        })
    }
    private fun fillUserData(){
        binding.apply {
            getUserDataActivityOwnerName.setText(userData.userName)
            getUserDataActivityBusinessName.setText(userData.restaurantName)
            getUserDataActivityPhoneNumber.setText(userData.phoneNumber)
        }
    }
    private fun setListeners(){
        binding.getUserDataActivitySaveInformationButton.setOnClickListener {
            if(checkUserData()){
                userData.userName = binding.getUserDataActivityOwnerName.text.toString()
                userData.restaurantName = binding.getUserDataActivityBusinessName.text.toString()
                userData.phoneNumber = binding.getUserDataActivityPhoneNumber.text.toString()

                userReference.child("user data").setValue(userData).addOnSuccessListener {
                    toast("Information saved successfully")

                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                    .addOnFailureListener{
//                        toast("Failed to save information")
                    }
            }
        }
    }
    private fun checkUserData(): Boolean{
        val phoneNumberEditText =  binding.getUserDataActivityPhoneNumber
        val ownerNameEditText = binding.getUserDataActivityOwnerName
        val businessNameEditText = binding.getUserDataActivityBusinessName
        val userPhoneNumber = phoneNumberEditText.text.toString()
        val ownerName = ownerNameEditText.text.toString()
        val businessName = businessNameEditText.text.toString()

        // Checks on Owner Name
        if (ownerName.isEmpty()){
            ownerNameEditText.error = "Owner Name can't be Empty"
            ownerNameEditText.requestFocus()
            return false
        }
        if (ownerName.isBlank()){
            ownerNameEditText.error = "Invalid Owner Name"
            ownerNameEditText.setText("")
            ownerNameEditText.requestFocus()
            return false
        }

        // Checks on Business Name
        if (businessName.isEmpty()){
            businessNameEditText.error = "Business Name can't be empty"
            businessNameEditText.requestFocus()
            return false
        }
        if (businessName.isBlank()){
            businessNameEditText.error = "Invalid Business Name"
            businessNameEditText.setText("")
            businessNameEditText.requestFocus()
            return false
        }

        //checks on Phone Number
        if(userPhoneNumber.isBlank()){
            phoneNumberEditText.requestFocus()
            phoneNumberEditText.error = "Phone Number can not be blank!!"
            return false
        }
        if(userPhoneNumber[0] == '+'){
            phoneNumberEditText.requestFocus()
            phoneNumberEditText.error = "Enter Phone Number without Country Code!!"
            return false
        }
        if(userPhoneNumber.toLongOrNull() == null){
            phoneNumberEditText.requestFocus()
            phoneNumberEditText.error = "Invalid Input!!"
            return false
        }
        if(userPhoneNumber.toLong() < 1e9){
            phoneNumberEditText.requestFocus()
            phoneNumberEditText.error = "Phone number is invalid!!"
            return false
        }
        if(userPhoneNumber.length != 10){
            phoneNumberEditText.requestFocus()
            phoneNumberEditText.error = "Phone number is too long!!"
            return false
        }
        return true
    }
}