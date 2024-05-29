package com.example.foodorderingapp.fragment.bottomSheets

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderingapp.adapters.MenuItemAdapter
import com.example.foodorderingapp.dataModels.MenuItemModel
import com.example.foodorderingapp.databinding.FragmentMenuBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MenuBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentMenuBottomSheetBinding
    private lateinit var menuBottomSheetAdapter : MenuItemAdapter

    private lateinit var auth : FirebaseAuth
    private lateinit var database: FirebaseDatabase

    private lateinit var menuItemReference : MutableList<String>
    private lateinit var menuList : MutableList<MenuItemModel>
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
        initializeUiElements()
        setListeners()
    }
    private fun initializeUiElements(){
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        menuList = mutableListOf()
        menuItemReference = mutableListOf()

        retrieveMenu()
    }
    private fun retrieveMenu(){
        val menuReference = database.reference.child("menu")

        menuReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                menuList.clear()

                for (menuItemSnapshot in snapshot.children){
                    val menuItem = menuItemSnapshot.getValue(MenuItemModel::class.java)
                    menuItem?.let {
                        menuList.add(menuItem)
                    }
                    menuItemSnapshot.key?.let { menuItemReference.add(it) }
                }
                setAdapters()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Error","Database Error in ViewMenuActivity:- ${error.toString()}")
            }

        })
    }
    private fun setAdapters(){
        menuBottomSheetAdapter = MenuItemAdapter(requireActivity(),1,menuList,menuItemReference)
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