package com.example.adminpanel.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminpanel.databinding.PendingOrderItemLayoutBinding
import kotlinx.coroutines.flow.combineTransform

class PendingOrderAdapter(private val context : Context ,
                          private val customerNames : MutableList<String> ,
                          private val images : MutableList<Int> ,
                          private val quantities : MutableList<Int>) : RecyclerView.Adapter<PendingOrderAdapter.PendingOrderItemHolder>() {

    inner class PendingOrderItemHolder ( private val binding : PendingOrderItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            binding.apply {
                pendingOrderItemCustomerName.text = customerNames[position]
                pendingOrderItemQuantity.text = quantities[position].toString()
                pendingOrderItemImage.setImageResource(images[position])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingOrderItemHolder {
        val view = PendingOrderItemLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return PendingOrderItemHolder(view)
    }

    override fun getItemCount(): Int {
        return customerNames.size
    }

    override fun onBindViewHolder(holder: PendingOrderItemHolder, position: Int) {
        holder.bind(position)
    }
}