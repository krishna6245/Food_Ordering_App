package com.example.foodorderingapp

import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.BulletSpan
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.example.foodorderingapp.dataModels.CartItemModel
import com.example.foodorderingapp.dataModels.MenuItemModel
import com.example.foodorderingapp.databinding.ActivityFoodDescriptionBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FoodDescriptionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFoodDescriptionBinding

    private var foodName : String? = null
    private var restaurantName : String? = null
    private var foodDescription : String? = null
    private var foodIngredient : String? = null
    private var foodIngredientsList : ArrayList<String>? = null
    private var foodImageUri : Uri? = null
    private var foodPrice : Int = 0


    private lateinit var auth : FirebaseAuth
    private lateinit var database: DatabaseReference


    private fun log(s : String?){
        Log.d("TTag","$s")
    }
    private fun log(s : Int?){
        Log.d("TTag","$s")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){
        initializeUiElements()
        setLayouts()
        setListeners()
    }
    private fun initializeUiElements(){

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        val foodImage = intent.getStringExtra("key_image")

        foodName = intent.getStringExtra("key_name")
        restaurantName = intent.getStringExtra("key_restaurant")
        foodDescription = intent.getStringExtra("key_description")
        foodIngredientsList = intent.getStringArrayListExtra("key_ingredients")
        foodImageUri = Uri.parse(foodImage)
        foodPrice = intent.getIntExtra("key_price",0)

        val stringBuilder = StringBuilder()
        if(foodIngredientsList != null){
            for(item in foodIngredientsList!!){
                stringBuilder.append("• ").append(item).append("\n")
            }
        }
        foodIngredient = stringBuilder.toString()
    }
    private fun setLayouts(){
        binding.apply {
            foodDescriptionActivityFoodName.text = foodName
            Glide.with(this@FoodDescriptionActivity).load(foodImageUri).into(foodDescriptionActivityFoodImage)
            foodDescriptionActivityFoodDescription.text = foodDescription
            foodDescriptionActivityFoodIngredients.text = foodIngredient
        }
    }
    private fun setListeners(){
        binding.apply {
            foodDescriptionActivityBackButton.setOnClickListener{
                finish()
            }
            foodDescriptionActivityAddToCartButton.setOnClickListener {

                val userId = auth.currentUser!!.uid
                val menuItem = MenuItemModel(foodName,restaurantName,foodPrice,foodImageUri.toString(),foodDescription,foodIngredientsList)
                val cartItem = CartItemModel(menuItem,1)

                database.child("food ordering app").child("users").child(userId).child("cart items").push().setValue(cartItem)
                finish()

            }
        }
    }
}