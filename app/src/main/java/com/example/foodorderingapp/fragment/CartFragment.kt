package com.example.foodorderingapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderingapp.R
import com.example.foodorderingapp.adapters.CartFragmentMenuItemAdapter
import com.example.foodorderingapp.databinding.FragmentCartBinding

class CartFragment : Fragment() {
    private lateinit var binding:FragmentCartBinding
    private lateinit var cartItemAdapter: CartFragmentMenuItemAdapter
    private lateinit var cartItemFoodNames : MutableList<String>
    private lateinit var cartItemFoodImages : MutableList<Int>
    private lateinit var cartItemFoodPrices : MutableList<Int>
    private lateinit var cartItemFoodQuantities : MutableList<Int>

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

        cartItemFoodNames = mutableListOf("Biryani","Burger","Pizza","Momos","Rolls","Fries","Sandwich","Muffins")
        cartItemFoodPrices = mutableListOf(100,70,150,60,75,80,40,60)
        val a:Int = R.drawable.dummy_image
        val b:Int = R.drawable.dummy_image_1
        cartItemFoodImages = mutableListOf(a,b,a,b,a,b,a,b)
        cartItemFoodQuantities = mutableListOf(1,2,3,10,5,2,8,5)

        cartItemAdapter = CartFragmentMenuItemAdapter(cartItemFoodNames,cartItemFoodImages,cartItemFoodPrices,cartItemFoodQuantities)
        binding.cartFragmentCartItemList.layoutManager = LinearLayoutManager(requireContext())
        binding.cartFragmentCartItemList.adapter = cartItemAdapter
    }


}