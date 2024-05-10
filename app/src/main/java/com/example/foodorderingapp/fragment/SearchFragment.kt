package com.example.foodorderingapp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderingapp.R
import com.example.foodorderingapp.adapters.MenuItemAdapter
import com.example.foodorderingapp.dataModels.MenuItemModel
import com.example.foodorderingapp.databinding.FragmentSearchBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SearchFragment : Fragment() {

    private lateinit var binding : FragmentSearchBinding
    private lateinit var searchItemAdapter: MenuItemAdapter

    private lateinit var auth : FirebaseAuth
    private lateinit var database: FirebaseDatabase

    private lateinit var searchMenuList : MutableList<MenuItemModel>
    private lateinit var filteredMenuList : MutableList<MenuItemModel>

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
        setListeners()
        initializeUiElements()
    }
    private fun initializeUiElements(){
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        searchMenuList = mutableListOf()
        filteredMenuList = mutableListOf()

        retrieveMenu()
    }
    private fun retrieveMenu(){
        val menuReference = database.reference.child("menu")

        menuReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                searchMenuList.clear()

                for (menuItemSnapshot in snapshot.children){
                    val menuItem = menuItemSnapshot.getValue(MenuItemModel::class.java)
                    menuItem?.let {
                        searchMenuList.add(menuItem)
                    }
                }
                filteredMenuList.addAll(searchMenuList)
                setAdapters()
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("Error","Database Error in ViewMenuActivity:- ${error.toString()}")
            }
        })
    }
    private fun setAdapters(){
        searchItemAdapter = MenuItemAdapter(requireContext(), filteredMenuList)
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
        filteredMenuList.clear()
        searchMenuList.forEachIndexed { index, menuItem ->
            if(menuItem.foodName?.contains(query,ignoreCase = true) == true){
                filteredMenuList.add(searchMenuList[index])
            }
        }
        searchItemAdapter.notifyDataSetChanged()
    }

}