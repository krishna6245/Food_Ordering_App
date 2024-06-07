package com.example.adminpanel.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminpanel.OrderDescriptionActivity
import com.example.adminpanel.dataModels.OrderItemModel
import com.example.adminpanel.databinding.PendingOrderItemLayoutBinding

class PendingOrderAdapter(private val context : Context ,
                          private val orderList : MutableList<OrderItemModel>,
                          private val orderListReference : MutableList<String>) : RecyclerView.Adapter<PendingOrderAdapter.PendingOrderItemHolder>() {

    inner class PendingOrderItemHolder ( private val binding : PendingOrderItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            val orderItem = orderList[position]
            val userOrderList = orderItem.userOrderList

            binding.apply {
                val menuItem = userOrderList[0].menuItem

                val imageUri = Uri.parse(menuItem!!.foodImage)
//                Glide.with(context).load(imageUri).into(pendingOrderItemImage)

                pendingOrderItemCustomerName.text = orderItem.userName

                val totalPrice ="Rs.${orderItem.totalPrice}"
                pendingOrderItemTotalPrice.text = totalPrice
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingOrderItemHolder {
        val view = PendingOrderItemLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return PendingOrderItemHolder(view)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    override fun onBindViewHolder(holder: PendingOrderItemHolder, position: Int) {
        holder.bind(position)

        holder.itemView.setOnClickListener{
            val orderItem = orderList[position]
            val orderItemReference = orderListReference[position]

            val intent = Intent(context, OrderDescriptionActivity::class.java)

            intent.putExtra("key_order_item",orderItem)
            intent.putExtra("key_order_item_reference",orderItemReference)

            context.startActivity(intent)
        }
    }
}