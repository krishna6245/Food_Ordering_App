package com.example.foodorderingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.location.LocationRequestCompat.Quality
import com.example.foodorderingapp.databinding.ActivityPlaceOrderBinding
import com.example.foodorderingapp.fragment.bottomSheets.OrderPlacedBottomSheetFragment
import kotlin.math.log

class PlaceOrderActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPlaceOrderBinding
    private lateinit var getIntent : Intent
    private lateinit var placeOrderActivityFoodNames : MutableList<String>
    private lateinit var placeOrderActivityFoodImages : MutableList<Int>
    private lateinit var placeOrderActivityFoodPrices : MutableList<Int>
    private lateinit var placeOrderActivityFoodQualities : MutableList<Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPlaceOrderBinding.inflate(layoutInflater)
        init()
        setContentView(binding.root)
    }
    private fun init(){
        setLists()
        setLayout()
        setListeners()
    }
    private fun setLists(){
            placeOrderActivityFoodNames = intent.getStringArrayListExtra("key_names") ?: arrayListOf()
            placeOrderActivityFoodImages = intent.getIntegerArrayListExtra("key_images") ?: arrayListOf()
            placeOrderActivityFoodPrices = intent.getIntegerArrayListExtra("key_prices") ?: arrayListOf()
            placeOrderActivityFoodQualities = intent.getIntegerArrayListExtra("key_quantities") ?: arrayListOf()
    }
    private fun setLayout(){
        val size = placeOrderActivityFoodPrices.size
        var totalPrice = 0
        for((index , price) in placeOrderActivityFoodPrices.withIndex()){
            totalPrice += price * placeOrderActivityFoodQualities[index]
        }
        val viewPrice = "Rs.$totalPrice"
        binding.orderActivityAmount.text = viewPrice
    }
    private fun setListeners(){
        binding.apply {
            orderActivityBackButton.setOnClickListener{
                finish()
            }
            orderActivityProccedButton.setOnClickListener {
                val orderPlacedBottomSheetDialog = OrderPlacedBottomSheetFragment()
                orderPlacedBottomSheetDialog.show(supportFragmentManager,"Tag")
            }
        }
    }
}