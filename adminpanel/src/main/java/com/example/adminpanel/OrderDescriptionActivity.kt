package com.example.adminpanel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminpanel.adapters.OrderCartListAdapter
import com.example.adminpanel.dataModels.CartItemModel
import com.example.adminpanel.dataModels.OrderItemModel
import com.example.adminpanel.databinding.ActivityOrderDescriptionBinding

class OrderDescriptionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderDescriptionBinding

    private lateinit var orderItem: OrderItemModel
    private lateinit var orderItemReference: String
    private lateinit var orderCartList: MutableList<CartItemModel>

    private lateinit var orderListAdapter: OrderCartListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDescriptionBinding.inflate(layoutInflater)
        init()
        setContentView(binding.root)
    }
    private fun init() {
        initializeUiElements()
        setAdapters()
        setListeners()
    }
    private fun initializeUiElements(){
        orderItem = intent.getParcelableExtra<OrderItemModel>("key_order_item") ?: OrderItemModel()
        orderItemReference = intent.getStringExtra("key_order_item_reference") ?: ""

        orderCartList = orderItem.userOrderList

        binding.apply {
            orderActivityUserName.text = orderItem.userName
            orderActivityUserAddress.text = orderItem.userAddress
            orderActivityUserPhoneNumber.text = orderItem.userPhoneNumber
        }
    }
    private fun setAdapters(){
        orderListAdapter = OrderCartListAdapter(this , orderCartList)
        binding.orderActivityOrderList.layoutManager = LinearLayoutManager(this)
        binding.orderActivityOrderList.adapter = orderListAdapter
    }
    private fun setListeners(){
        binding.orderActivityBackButton.setOnClickListener{
            finish()
        }
    }
}