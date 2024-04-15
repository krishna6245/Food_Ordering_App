package com.example.foodorderingapp.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderingapp.PlaceOrderActivity
import com.example.foodorderingapp.R
import com.example.foodorderingapp.adapters.CartItemAdapter
import com.example.foodorderingapp.databinding.FragmentCartBinding

class CartFragment : Fragment() {
    private lateinit var binding:FragmentCartBinding
    private lateinit var cartFragmentAdapter: CartItemAdapter
    private lateinit var cartFragmentFoodNames : MutableList<String>
    private lateinit var cartFragmentFoodImages : MutableList<Int>
    private lateinit var cartFragmentFoodPrices : MutableList<Int>
    private lateinit var cartFragmentFoodQuantities : MutableList<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater,container,false)
        init()
        return binding.root
    }
    private fun init(){
        setLists()
        setAdapters()
        setListeners()
    }

    private fun setLists(){
        cartFragmentFoodNames = mutableListOf("Biryani","Burger","Pizza","Momos","Rolls","Fries","Sandwich","Muffins")
        cartFragmentFoodPrices = mutableListOf(100,70,150,60,75,80,40,60)
        val a:Int = R.drawable.dummy_image
        val b:Int = R.drawable.dummy_image_1
        cartFragmentFoodImages = mutableListOf(a,b,a,b,a,b,a,b)
        cartFragmentFoodQuantities = mutableListOf(1,2,3,10,5,2,8,5)
    }
    private fun setAdapters(){
        cartFragmentAdapter = CartItemAdapter(cartFragmentFoodNames,cartFragmentFoodImages,cartFragmentFoodPrices,cartFragmentFoodQuantities,requireActivity())
        binding.cartFragmentCartItemList.layoutManager = LinearLayoutManager(requireContext())
        binding.cartFragmentCartItemList.adapter = cartFragmentAdapter
    }
    private fun setListeners(){
        binding.apply {
            cartFragmentProccedButton.setOnClickListener {
                Handler().postDelayed({
                    val intent = Intent(requireContext() , PlaceOrderActivity::class.java)

                    Log.d("TTTT","${cartFragmentFoodImages.size}")
                    intent.putExtra("hello" , 1)
                    intent.putStringArrayListExtra("key_names", ArrayList(cartFragmentFoodNames))
                    intent.putIntegerArrayListExtra("key_images", ArrayList(cartFragmentFoodImages))
                    intent.putIntegerArrayListExtra("key_prices", ArrayList(cartFragmentFoodPrices))
                    intent.putIntegerArrayListExtra("key_quantities", ArrayList(cartFragmentFoodQuantities))
                    intent.putExtra("key_flag" , true)
                    startActivity(intent)
                },0)
            }
        }
    }

}