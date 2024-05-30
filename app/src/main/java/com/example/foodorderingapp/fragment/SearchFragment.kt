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
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SearchFragment : Fragment() {

    private lateinit var binding : FragmentSearchBinding
    private lateinit var searchItemAdapter: MenuItemAdapter

    private lateinit var auth : FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var menuReference: DatabaseReference

    private var currentSearch: String = ""

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
        initializeUiElements()
        setAdapters()
        setListeners()
        retrieveMenu()
    }
    private fun initializeUiElements(){
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        menuReference = database.reference.child("menu")

        searchMenuList = mutableListOf()
        filteredMenuList = mutableListOf()
    }
    private fun retrieveMenu(){
        menuReference.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val menuItem = snapshot.getValue(MenuItemModel::class.java)
                if(menuItem!=null){
                    searchMenuList.add(menuItem)
                    if(menuItem.foodName!!.contains(currentSearch,ignoreCase = true)){
                        filteredMenuList.add(menuItem)
                        searchItemAdapter.notifyItemInserted(filteredMenuList.size-1)
                    }
                }
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })
    }
    private fun setAdapters(){
        searchItemAdapter = MenuItemAdapter(requireContext(), filteredMenuList)
        binding.menuFragmentMenuItemList.layoutManager = LinearLayoutManager(requireContext())
        binding.menuFragmentMenuItemList.adapter = searchItemAdapter
    }
    private fun filterMenuItems(){
        filteredMenuList.clear()
        searchMenuList.forEachIndexed { index, menuItem ->
            if(menuItem.foodName!!.contains(currentSearch,ignoreCase = true)){
                filteredMenuList.add(searchMenuList[index])
            }
        }
        searchItemAdapter.notifyDataSetChanged()
    }
    private fun setListeners(){

        binding.menuFragmentSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                currentSearch = query.orEmpty()
                filterMenuItems()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                currentSearch = newText.orEmpty()
                filterMenuItems()
                return true
            }
        })
    }
}
