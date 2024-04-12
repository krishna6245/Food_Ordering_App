package com.example.foodorderingapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderingapp.R
import com.example.foodorderingapp.adapters.MenuItemAdapter
import com.example.foodorderingapp.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private lateinit var binding : FragmentSearchBinding
    private lateinit var searchItemAdapter: MenuItemAdapter
    private lateinit var searchItemFoodNames : MutableList<String>
    private lateinit var searchItemFoodImages : MutableList<Int>
    private lateinit var searchItemFoodPrices : MutableList<Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchBinding.inflate(inflater,container,false)

        init()

        return binding.root
    }

    private fun init(){

        searchItemFoodNames = mutableListOf("Biryani","Burger","Pizza","Momos","Rolls","Fries","Sandwich","Muffins")
        searchItemFoodPrices = mutableListOf(100,70,150,60,75,80,40,60)
        val a:Int = R.drawable.dummy_image
        val b:Int = R.drawable.dummy_image_1
        searchItemFoodImages = mutableListOf(a,b,a,b,a,b,a,b)

        searchItemAdapter = MenuItemAdapter(searchItemFoodNames,searchItemFoodImages,searchItemFoodPrices)
        binding.menuFragmentMenuItemList.layoutManager = LinearLayoutManager(requireContext())
        binding.menuFragmentMenuItemList.adapter = searchItemAdapter

    }

}