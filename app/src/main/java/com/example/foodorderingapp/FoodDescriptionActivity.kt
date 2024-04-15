package com.example.foodorderingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.BulletSpan
import com.example.foodorderingapp.databinding.ActivityFoodDescriptionBinding

class FoodDescriptionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFoodDescriptionBinding
    private lateinit var foodName : String
    private lateinit var foodDescription : String
    private lateinit var foodIngredient : String
    private lateinit var foodIngredientsList : ArrayList<String>
    private var foodImage = 0

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
        foodName = intent.getStringExtra("key_name") ?: "Food Name"
        foodImage = intent.getIntExtra("key_image" , R.drawable.dummy_image)
        foodDescription = intent.getStringExtra("key_description") ?:  "Sample Description"
        foodIngredientsList = intent.getStringArrayListExtra("key_ingredients") ?: arrayListOf()

        val stringBuilder = StringBuilder()
        for(item in foodIngredientsList){
            stringBuilder.append("• ").append(item).append("\n")
        }

        foodIngredient = stringBuilder.toString()
    }
    private fun setLayouts(){
        binding.apply {
            foodDescriptionActivityFoodName.text = foodName
            foodDescriptionActivityFoodImage.setImageResource(foodImage)
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