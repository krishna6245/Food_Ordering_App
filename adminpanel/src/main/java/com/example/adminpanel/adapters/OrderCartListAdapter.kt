package com.example.adminpanel.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.gifdecoder.GifHeader
import com.example.adminpanel.dataModels.CartItemModel
import com.example.adminpanel.databinding.OrderCartItemLayoutBinding

class OrderCartListAdapter (private val context: Context,
                            private val orderCartList: MutableList<CartItemModel>) : RecyclerView.Adapter<OrderCartListAdapter.OrderCartViewHolder>() {

    inner class OrderCartViewHolder(private val binding: OrderCartItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val cartItem = orderCartList[position]
            val menuItem = cartItem.menuItem
            binding.apply {
                orderCartItemFoodName.text = menuItem!!.foodName

                val foodPrice = "Rs.${menuItem.foodPrice}"
                orderCartItemFoodPrice.text = foodPrice
                orderCartItemQuantity.text = cartItem.quantity.toString()

                val imageUri = Uri.parse(menuItem.foodImage)
                Glide.with(context).load(imageUri).into(orderCartItemFoodImage)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderCartViewHolder {
        val view = OrderCartItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return OrderCartViewHolder(view)
    }

    override fun getItemCount(): Int {
        return orderCartList.size
    }

    override fun onBindViewHolder(holder: OrderCartViewHolder, position: Int) {
        holder.bind(position)
    }
}