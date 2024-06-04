package com.example.foodorderingapp.fragment

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodorderingapp.adapters.HistoryItemAdapter
import com.example.foodorderingapp.dataModels.HistoryItemModel
import com.example.foodorderingapp.dataModels.OrderItemModel
import com.example.foodorderingapp.databinding.FragmentHistoryBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class HistoryFragment : Fragment() {

    private lateinit var binding : FragmentHistoryBinding
    private lateinit var historyFragmentAdapter : HistoryItemAdapter

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    private lateinit var userId: String
    private lateinit var orderHistoryReference: DatabaseReference

    private var recentBuy: HistoryItemModel? = null
    private lateinit var previousBuyList: MutableList<HistoryItemModel>

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
        initializeUiElements()
        setAdapters()
        retrieveOrderHistory()
    }
    private fun retrieveOrderHistory(){
        orderHistoryReference.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val orderItem = snapshot.getValue(OrderItemModel::class.java)
                if(orderItem!=null){
                    val cartItemList = orderItem.userOrderList
                    for( cartItem in cartItemList){
                        val menuItem = cartItem.menuItem

                        val historyItem = HistoryItemModel(menuItem)

                        updateUi(historyItem)
                    }
                }
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })
    }
    private fun updateUi(historyItem: HistoryItemModel){
        if(recentBuy != null){
            previousBuyList.add(0, recentBuy!!)
            historyFragmentAdapter.notifyItemInserted(previousBuyList.size-1)
        }
        recentBuy = historyItem
        updateRecentBuy()
    }
    private fun updateRecentBuy(){
        binding.apply {
            val menuItem = recentBuy!!.menuItem!!
            val foodPrice = "Rs.${menuItem.foodPrice}"
            val imageUri = Uri.parse(menuItem.foodImage)

            historyFragmentRecentBuyFoodName.text = menuItem.foodName
            historyFragmentRecentBuyFoodPrice.text = foodPrice
            historyFragmentRecentBuyRestaurant.text = menuItem.restaurantName
            context?.let { Glide.with(it).load(imageUri).into(historyFragmentRecentBuyFoodImage) }
        }
    }
    private fun initializeUiElements(){
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        userId = auth.currentUser!!.uid
        orderHistoryReference = database.reference.child("food ordering app").child("users").child(userId).child("history items")

        previousBuyList = mutableListOf()
    }
    private fun setAdapters(){
        historyFragmentAdapter = HistoryItemAdapter(previousBuyList,requireActivity())
        binding.historyFragmentPreviousBuyList.layoutManager = LinearLayoutManager(requireContext())
        binding.historyFragmentPreviousBuyList.adapter = historyFragmentAdapter
    }
}