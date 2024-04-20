package com.example.adminpanel.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminpanel.databinding.OrderDispatchItemLayoutBinding

class OrderDispatchAdapter(private val context: Context,
                           private val customerNames : MutableList<String>,
                           private val paymentStatus : MutableList<Boolean>,
                           private val deliveryStatus : MutableList<Boolean>) : RecyclerView.Adapter<OrderDispatchAdapter.OrderDispatchItemHolder>(){
    inner class OrderDispatchItemHolder(private val binding : OrderDispatchItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            binding.apply {
                orderDispatchItemCustomerName.text = customerNames[position]

                var deliveryStatusText = "Not\nDelivered"
                if(deliveryStatus[position]){
                    deliveryStatusText = "Delivered"
                }
                orderDispatchItemDeliveryStatus.text = deliveryStatusText

                var paymentStatusText = "Not Received"
                if(paymentStatus[position]){
                    paymentStatusText = "Received"
                }
                orderDispatchItemPaymentStatus.text = paymentStatusText
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDispatchItemHolder {
        val view = OrderDispatchItemLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return OrderDispatchItemHolder(view)
    }

    override fun getItemCount(): Int {
        return customerNames.size
    }

    override fun onBindViewHolder(holder: OrderDispatchItemHolder, position: Int) {
        holder.bind(position)
    }
}