package com.example.foodorderingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.location.LocationRequestCompat.Quality
import com.example.foodorderingapp.dataModels.UserModel
import com.example.foodorderingapp.databinding.ActivityPlaceOrderBinding
import com.example.foodorderingapp.fragment.bottomSheets.OrderPlacedBottomSheetFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.math.log

class PlaceOrderActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPlaceOrderBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var userId: String

    private var userData : UserModel? = null
    private lateinit var userReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPlaceOrderBinding.inflate(layoutInflater)
        init()
        setContentView(binding.root)
    }
    private fun init(){
        setLayout()
        setListeners()
    }
    private fun setLayout(){
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        userId = auth.currentUser?.uid ?: return
        userReference = database.reference.child("food ordering app").child("users").child(userId)

        userReference.child("user data").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    userData = dataSnapshot.getValue(UserModel::class.java)
                    if (userData != null) {
                        fillUserData()
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("Tag", "loadPost:onCancelled", databaseError.toException())
            }
        })

        val totalPrice = intent.getIntExtra("key_cart_total",0)
        val viewPrice = "Rs.$totalPrice"
        binding.orderActivityAmount.text = viewPrice
    }
    private fun fillUserData(){
        binding.apply {
            orderActivityUserName.setText(userData?.userName)
            orderActivityUserPhoneNumber.setText(userData?.phoneNumber)

        }
    }
    private fun updateUserData(){
        if(userData != null){
            userData!!.userName = binding.orderActivityUserName.text.toString()
            userData!!.address = binding.orderActivityUserAddress.text.toString()
            userData!!.phoneNumber = binding.orderActivityUserPhoneNumber.text.toString()
        }
    }
    private fun setListeners(){
        binding.apply {
            orderActivityBackButton.setOnClickListener{
                updateUserData()
                userReference.child("user data").setValue(userData)
                finish()
            }
            orderActivityProccedButton.setOnClickListener {
                updateUserData()
                userReference.child("user data").setValue(userData)
                val orderPlacedBottomSheetDialog = OrderPlacedBottomSheetFragment()
                orderPlacedBottomSheetDialog.show(supportFragmentManager,"Tag")
            }
        }
    }
}