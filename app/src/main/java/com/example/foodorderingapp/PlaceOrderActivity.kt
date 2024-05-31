package com.example.foodorderingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.foodorderingapp.dataModels.CartItemModel
import com.example.foodorderingapp.dataModels.OrderItemModel
import com.example.foodorderingapp.dataModels.UserModel
import com.example.foodorderingapp.databinding.ActivityPlaceOrderBinding
import com.example.foodorderingapp.fragment.bottomSheets.OrderPlacedBottomSheetFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PlaceOrderActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPlaceOrderBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var userId: String

    private lateinit var cartList : MutableList<CartItemModel>
    private var totalPrice: Int = 0

    private var userData : UserModel? = null
    private lateinit var userReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPlaceOrderBinding.inflate(layoutInflater)
        init()
        setContentView(binding.root)
    }
    private fun init(){
        initializeUiElements()
        setListeners()
    }
    private fun initializeUiElements(){
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

        cartList = intent.getParcelableArrayListExtra("key_cart_list") ?: mutableListOf()

        calculateCartTotal()

        val viewPrice = "Rs.$totalPrice"
        binding.orderActivityAmount.text = viewPrice
    }
    private fun calculateCartTotal(){
        for( cartItem in cartList){
            val menuItem = cartItem.menuItem
            totalPrice += cartItem.quantity!! * menuItem?.foodPrice!!
        }
    }
    private fun fillUserData(){
        binding.apply {
            orderActivityUserName.setText(userData?.userName)
            orderActivityUserPhoneNumber.setText(userData?.phoneNumber)
            orderActivityUserAddress.setText(userData?.address)
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
                if(checkCompleteUserData()){
                    placeOrder()
                }
            }
        }
    }
    private fun placeOrder(){

        val currentTime = System.currentTimeMillis()
        val itemKey = database.reference.child("Order details").push().key

        val orderItem = OrderItemModel(
            userId,
            userData?.userName,
            userData?.address,
            userData?.phoneNumber,
            totalPrice,
            cartList,
            orderAccepted = false,
            paymentReceived = false,
            itemKey,
            currentTime
        )

        database.reference.child("Order details").child(itemKey!!).setValue(orderItem)
            .addOnSuccessListener {
                userReference.child("user data").setValue(userData)
                val orderPlacedBottomSheetDialog = OrderPlacedBottomSheetFragment()
                orderPlacedBottomSheetDialog.show(supportFragmentManager,"Tag")
                emptyCart()
            }
            .addOnFailureListener {
                Toast.makeText(this,"Order not placed",Toast.LENGTH_SHORT).show()
            }
    }
    private fun emptyCart(){
        database.reference.child("food ordering app").child("users").child(userId).child("cart items").removeValue()
    }
    private fun checkCompleteUserData() : Boolean{
        val userName =  binding.orderActivityUserName.text.toString()
        val userAddress =  binding.orderActivityUserAddress.text.toString()
        val userPhoneNumber =  binding.orderActivityUserPhoneNumber.text.toString()
        if(userName.isBlank()){
            binding.orderActivityUserName.requestFocus()
            binding.orderActivityUserName.error = "Name can not be blank!!"
            return false
        }
        if(userAddress.isBlank()){
            binding.orderActivityUserAddress.requestFocus()
            binding.orderActivityUserAddress.error = "Address can not be blank!!"
            return false
        }
        if(userPhoneNumber.isBlank()){
            binding.orderActivityUserPhoneNumber.requestFocus()
            binding.orderActivityUserPhoneNumber.error = "Phone Number can not be blank!!"
            return false
        }
        if(userPhoneNumber[0] == '+'){
            binding.orderActivityUserPhoneNumber.requestFocus()
            binding.orderActivityUserPhoneNumber.error = "Enter Phone Number without country code!!"
            return false
        }
        if(userPhoneNumber.length != 10){
            binding.orderActivityUserPhoneNumber.requestFocus()
            binding.orderActivityUserPhoneNumber.error = "Phone Number is invalid!!"
            return false
        }
        return true
    }
}