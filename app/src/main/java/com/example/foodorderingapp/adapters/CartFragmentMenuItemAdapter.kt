package com.example.foodorderingapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapp.databinding.CartItemCartFragmentBinding

class CartFragmentMenuItemAdapter(private val names:MutableList<String>,private val images:MutableList<Int>,private val prices:MutableList<Int>,private val quantities:MutableList<Int>) : RecyclerView.Adapter<CartFragmentMenuItemAdapter.CartFragmentMenuItemHolder>() {
    inner class CartFragmentMenuItemHolder(private val binding : CartItemCartFragmentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(name: String, image: Int, price: Int, quantity: Int, position: Int) {

            binding.cartItemName.text=name
            val viewPrice = "Rs.$price"
            binding.cartItemPrice.text=viewPrice
            binding.cartItemQuantity.text=quantity.toString()
            binding.cartItemImage.setImageResource(image)

            binding.cartItemIncrease.setOnClickListener {
                increaseItem(position)
            }

            binding.cartItemDecrease.setOnClickListener {
                decreaseItem(position)
            }

            binding.cartItemDelete.setOnClickListener {
                removeItem(position)
            }

        }
        private fun increaseItem(position: Int){
            quantities[position]++;
            binding.cartItemQuantity.text = quantities[position].toString()
        }
        private fun decreaseItem(position: Int){
            quantities[position]--;
            if(quantities[position]==0){
                removeItem(position)
                return
            }
            binding.cartItemQuantity.text = quantities[position].toString()
        }
        private fun removeItem(position: Int){
            quantities.removeAt(position)
            names.removeAt(position)
            prices.removeAt(position)
            images.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,images.size)
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartFragmentMenuItemHolder {
        val view = CartItemCartFragmentBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        view.cartItemIncrease.setOnClickListener{

        }

        return CartFragmentMenuItemHolder(view)
    }

    override fun getItemCount(): Int {
        return names.size
    }

    override fun onBindViewHolder(holder: CartFragmentMenuItemHolder, position: Int) {
        val name = names[position]
        val price = prices[position]
        val image = images[position]
        val quantity = quantities[position]

        holder.bind(name,image,price,quantity,position)
    }
}