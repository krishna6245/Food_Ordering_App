package com.example.foodorderingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.foodorderingapp.databinding.ActivityPlaceOrderBinding
import com.example.foodorderingapp.fragment.bottomSheets.OrderPlacedBottomSheetFragment

class PlaceOrderActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPlaceOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPlaceOrderBinding.inflate(layoutInflater)
        init()
        setContentView(binding.root)
    }
    private fun init(){
        setListeners()
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