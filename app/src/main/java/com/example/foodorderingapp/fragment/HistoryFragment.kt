package com.example.foodorderingapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderingapp.R
import com.example.foodorderingapp.adapters.HistoryItemAdapter
import com.example.foodorderingapp.databinding.FragmentHistoryBinding


class HistoryFragment : Fragment() {

    private lateinit var binding : FragmentHistoryBinding
    private lateinit var historyFragmentAdapter : HistoryItemAdapter
    private lateinit var historyFragmentFoodNames : MutableList<String>
    private lateinit var historyFragmentFoodRestaurants : MutableList<String>
    private lateinit var historyFragmentFoodPrices : MutableList<Int>
    private lateinit var historyFragmentFoodImages : MutableList<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater,container,false)
        init()
        return binding.root
    }

    private fun init(){
        setLists()
        setLayout()
        setAdapters()
    }
    private fun setLists(){
        historyFragmentFoodNames = mutableListOf("Biryani","Burger","Pizza","Momos","Rolls","Fries","Sandwich","Muffins")
        historyFragmentFoodPrices = mutableListOf(100,70,150,60,75,80,40,60)
        historyFragmentFoodRestaurants = mutableListOf("Surya Restaurant","Monarch Hotel","Shivam Restaurant","Mukesh Momos","Monarch Hotel","Lal Bagh Ka Raja","Durga Canteen","Dreams Bakery")
        val a:Int = R.drawable.dummy_image
        val b:Int = R.drawable.dummy_image_1
        historyFragmentFoodImages = mutableListOf(a,b,a,b,a,b,a,b)
    }
    private fun setLayout(){
        binding.apply {
            historyFragmentRecentBuyFoodName.text = historyFragmentFoodNames[0]
            historyFragmentRecentBuyFoodImage.setImageResource(historyFragmentFoodImages[0])
            historyFragmentRecentBuyRestaurant.text = historyFragmentFoodRestaurants[0]
            val viewPrice = "Rs.${historyFragmentFoodPrices[0]}"
            historyFragmentRecentBuyFoodPrice.text = viewPrice
        }
        historyFragmentFoodNames.removeAt(0)
        historyFragmentFoodImages.removeAt(0)
        historyFragmentFoodPrices.removeAt(0)
        historyFragmentFoodRestaurants.removeAt(0)
    }
    private fun setAdapters(){
        historyFragmentAdapter = HistoryItemAdapter(historyFragmentFoodNames,historyFragmentFoodImages,historyFragmentFoodRestaurants,historyFragmentFoodPrices)
        binding.historyFragmentPreviousBuyList.layoutManager = LinearLayoutManager(requireContext())
        binding.historyFragmentPreviousBuyList.adapter = historyFragmentAdapter
    }
}