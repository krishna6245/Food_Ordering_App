package com.example.foodorderingapp.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderingapp.PlaceOrderActivity
import com.example.foodorderingapp.R
import com.example.foodorderingapp.adapters.CartFragmentMenuItemAdapter
import com.example.foodorderingapp.databinding.FragmentCartBinding

class CartFragment : Fragment() {
    private lateinit var binding:FragmentCartBinding
    private lateinit var cartFragmentAdapter: CartFragmentMenuItemAdapter
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
        cartFragmentAdapter = CartFragmentMenuItemAdapter(cartFragmentFoodNames,cartFragmentFoodImages,cartFragmentFoodPrices,cartFragmentFoodQuantities)
        binding.cartFragmentCartItemList.layoutManager = LinearLayoutManager(requireContext())
        binding.cartFragmentCartItemList.adapter = cartFragmentAdapter
    }
    private fun setListeners(){
        binding.apply {
            cartFragmentProccedButton.setOnClickListener {
                Handler().postDelayed({
                    val intent = Intent(requireContext() , PlaceOrderActivity::class.java)
                    startActivity(intent)
                },0)
            }
        }
    }

}