package com.example.foodorderingapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
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

    private lateinit var filteredFoodItemNames : MutableList<String>
    private lateinit var filteredFoodItemImages : MutableList<Int>
    private lateinit var filteredFoodItemPrices : MutableList<Int>
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
        setLists()
        setAdapters()
        setListeners()
    }
    private fun setLists(){
        searchItemFoodNames = mutableListOf("Biryani","Burger","Pizza","Momos","Rolls","Fries","Sandwich","Muffins")
        searchItemFoodPrices = mutableListOf(100,70,150,60,75,80,40,60)
        val a:Int = R.drawable.dummy_image
        val b:Int = R.drawable.dummy_image_1
        searchItemFoodImages = mutableListOf(a,b,a,b,a,b,a,b)

        filteredFoodItemNames = mutableListOf()
        filteredFoodItemImages = mutableListOf()
        filteredFoodItemPrices = mutableListOf()

        filteredFoodItemNames.addAll(searchItemFoodNames)
        filteredFoodItemImages.addAll(searchItemFoodImages)
        filteredFoodItemPrices.addAll(searchItemFoodPrices)

    }
    private fun setAdapters(){
        searchItemAdapter = MenuItemAdapter(filteredFoodItemNames,filteredFoodItemImages,filteredFoodItemPrices)
        binding.menuFragmentMenuItemList.layoutManager = LinearLayoutManager(requireContext())
        binding.menuFragmentMenuItemList.adapter = searchItemAdapter
    }
    private fun setListeners(){

        binding.menuFragmentSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                filterMenuItems(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterMenuItems(newText)
                return true
            }
        })

    }
    private fun filterMenuItems(query : String){
        filteredFoodItemNames.clear()
        filteredFoodItemImages.clear()
        filteredFoodItemPrices.clear()

        searchItemFoodNames.forEachIndexed { index, foodName ->
            if(foodName.contains(query,ignoreCase = true)){
                filteredFoodItemNames.add(searchItemFoodNames[index])
                filteredFoodItemImages.add(searchItemFoodImages[index])
                filteredFoodItemPrices.add(searchItemFoodPrices[index])
            }
        }
        searchItemAdapter.notifyDataSetChanged()
    }

}