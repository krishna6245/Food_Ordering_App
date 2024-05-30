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
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MenuBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentMenuBottomSheetBinding
    private lateinit var menuBottomSheetAdapter : MenuItemAdapter

    private lateinit var auth : FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var menuReference: DatabaseReference

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
        setAdapters()
        retrieveMenu()
    }
    private fun initializeUiElements(){
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        menuReference = database.reference.child("menu")

        menuList = mutableListOf()
    }
    private fun retrieveMenu(){
        menuReference.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val menuItem = snapshot.getValue(MenuItemModel::class.java)
                if(menuItem!=null){
                    menuList.add(menuItem)
                    menuBottomSheetAdapter.notifyItemInserted(menuList.size-1)
                }
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })
    }
    private fun setAdapters(){
        menuBottomSheetAdapter = MenuItemAdapter(requireActivity(),menuList)
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