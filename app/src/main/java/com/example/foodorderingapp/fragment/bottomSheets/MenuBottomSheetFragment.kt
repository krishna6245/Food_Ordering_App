package com.example.foodorderingapp.fragment.bottomSheets

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderingapp.R
import com.example.foodorderingapp.adapters.MenuItemAdapter
import com.example.foodorderingapp.databinding.FragmentMenuBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MenuBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentMenuBottomSheetBinding
    private lateinit var menuBottomSheetAdapter : MenuItemAdapter
    private lateinit var menuBottomSheetItemFoodNames : MutableList<String>
    private lateinit var menuBottomSheetItemFoodImages : MutableList<Int>
    private lateinit var menuBottomSheetItemFoodPrices : MutableList<Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBottomSheetBinding.inflate(inflater,container,false)
        init()
        return binding.root
    }

    private fun init(){
        setLists()
        setAdapters()
        setListeners()
    }
    private fun setLists(){
        menuBottomSheetItemFoodNames = mutableListOf("Biryani","Burger","Pizza","Momos","Rolls","Fries","Sandwich","Muffins","Burger","Pizza","Momos","Rolls","Fries","Sandwich","Muffins","Burger","Pizza","Momos","Rolls","Fries","Sandwich","Muffins")
        menuBottomSheetItemFoodPrices = mutableListOf(100,70,150,60,75,80,40,60,70,150,60,75,80,40,60,70,150,60,75,80,40,60)
        val a:Int = R.drawable.dummy_image
        val b:Int = R.drawable.dummy_image_1
        menuBottomSheetItemFoodImages = mutableListOf(a,b,a,b,a,b,a,b,b,a,b,a,b,a,b,b,a,b,a,b,a,b)
    }
    private fun setAdapters(){
        menuBottomSheetAdapter = MenuItemAdapter(menuBottomSheetItemFoodNames,menuBottomSheetItemFoodImages,menuBottomSheetItemFoodPrices,requireActivity())
        binding.menuBottomSheetItemList.layoutManager = LinearLayoutManager(requireContext())
        binding.menuBottomSheetItemList.adapter = menuBottomSheetAdapter
    }
    private fun setListeners(){
        binding.apply {
            menuBottomSheetBackButton.setOnClickListener{
                dismiss()
            }
        }
    }
}