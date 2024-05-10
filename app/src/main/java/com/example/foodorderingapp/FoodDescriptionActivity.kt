package com.example.foodorderingapp

import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.BulletSpan
import android.util.Log
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.example.foodorderingapp.databinding.ActivityFoodDescriptionBinding

class FoodDescriptionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFoodDescriptionBinding

    private var foodName : String? = null
    private var foodDescription : String? = null
    private var foodIngredient : String? = null
    private var foodIngredientsList : ArrayList<String>? = null
    private var foodImageUri : Uri? = null


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
        setLists()
        setLayouts()
        setListeners()
    }
    private fun setLists(){
        val foodImage = intent.getStringExtra("key_image")

        foodName = intent.getStringExtra("key_name")
        foodDescription = intent.getStringExtra("key_description")
        foodIngredientsList = intent.getStringArrayListExtra("key_ingredients")
        foodImageUri = Uri.parse(foodImage)

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
        }
    }
}