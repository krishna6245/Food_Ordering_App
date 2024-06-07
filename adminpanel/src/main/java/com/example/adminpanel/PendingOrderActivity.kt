package com.example.adminpanel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminpanel.adapters.PendingOrderAdapter
import com.example.adminpanel.dataModels.OrderItemModel
import com.example.adminpanel.databinding.ActivityPendingOrderBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PendingOrderActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPendingOrderBinding

    private lateinit var auth : FirebaseAuth
    private lateinit var database : FirebaseDatabase
    private lateinit var userId: String

    private lateinit var orderList: MutableList<OrderItemModel>
    private lateinit var orderListReference: MutableList<String>
    private lateinit var orderReference: DatabaseReference

    private lateinit var orderListener: ChildEventListener
    private lateinit var adapter : PendingOrderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendingOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){
        initializeUiElements()
        setAdapters()
        getOrders()
        setListeners()
    }
    private fun initializeUiElements() {
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        userId = auth.currentUser!!.uid

        orderList = mutableListOf()
        orderListReference = mutableListOf()
        orderReference = database.getReference("Order details")
    }
    private fun getOrders(){
        orderListener = object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                if(snapshot.key != null){
                    val orderItem = snapshot.getValue(OrderItemModel::class.java)
                    if (orderItem != null) {
                        orderList.add(orderItem)
                    }
                    orderListReference.add(snapshot.key!!)
                    adapter.notifyItemInserted(orderList.size - 1)
                }
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        }
        orderReference.addChildEventListener(orderListener)
    }
    private fun setAdapters(){
        adapter = PendingOrderAdapter(this,orderList,orderListReference)
        binding.pendingOrderActivityItemList.layoutManager = LinearLayoutManager(this)
        binding.pendingOrderActivityItemList.adapter = adapter
    }
    private fun setListeners(){
        binding.pendingOrderActivityBackButton.setOnClickListener{
            finish()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        orderReference.removeEventListener(orderListener)
    }
}