package com.example.adminpanel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminpanel.adapters.ViewMenuAdapter
import com.example.adminpanel.databinding.ActivityViewMenuBinding

class ViewMenuActivity : AppCompatActivity() {
    private lateinit var binding : ActivityViewMenuBinding
    private lateinit var allItemFoodNames : MutableList<String>
    private lateinit var allItemFoodRestaurants : MutableList<String>
    private lateinit var allItemFoodImages : MutableList<Int>
    private lateinit var allItemFoodPrices : MutableList<Int>
    private lateinit var allItemFoodQuantities : MutableList<Int>
    private lateinit var viewMenuAdapter: ViewMenuAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){
        setLists()
        setAdapters()
        setListeners()
    }
    private fun setLists(){
        allItemFoodNames = mutableListOf("Biryani","Burger","Pizza","Momos","Rolls","Fries","Sandwich","Muffins")
        allItemFoodPrices = mutableListOf(100,70,150,60,75,80,40,60)
        allItemFoodRestaurants = mutableListOf("Surya Restaurant","Monarch Hotel","Shivam Restaurant","Mukesh Momos","Monarch Hotel","Lal Bagh Ka Raja","Durga Canteen","Dreams Bakery")
        val a:Int = R.drawable.dummy_image
        val b:Int = R.drawable.dummy_image_1
        allItemFoodImages = mutableListOf(a,b,a,b,a,b,a,b)
        allItemFoodQuantities = mutableListOf(1,2,3,10,5,2,8,5)

    }
    private fun setAdapters(){
        viewMenuAdapter = ViewMenuAdapter(applicationContext , allItemFoodNames , allItemFoodRestaurants , allItemFoodImages , allItemFoodPrices , allItemFoodQuantities)
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
}