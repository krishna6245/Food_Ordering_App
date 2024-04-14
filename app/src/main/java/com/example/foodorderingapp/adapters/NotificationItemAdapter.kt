package com.example.foodorderingapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapp.R
import com.example.foodorderingapp.databinding.NotificationItemBinding

class NotificationItemAdapter(private val orderTypes : MutableList<Int>) : RecyclerView.Adapter<NotificationItemAdapter.NotificationItemHolder>(){
    inner class NotificationItemHolder (private val binding : NotificationItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int){
            var image = 0
            var text = ""
            when(orderTypes[position]){
                0 -> {
                    image = R.drawable.order_placed_icon
                    text = "Congrats, Your order has been placed"
                }
                1 -> {
                    image = R.drawable.order_delivered_icon
                    text = "Your order has been delivered successfully"
                }
                2 -> {
                    image = R.drawable.order_cancel_icon
                    text = "Your order has been cancelled"
                }
            }
            binding.apply {
                notificationItemImage.setImageResource(image)
                notificationItemText.text = text
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationItemHolder {
        val view = NotificationItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NotificationItemHolder(view)
    }

    override fun getItemCount(): Int {
        return orderTypes.size
    }

    override fun onBindViewHolder(holder: NotificationItemHolder, position: Int) {
        holder.bind(position)
    }
}