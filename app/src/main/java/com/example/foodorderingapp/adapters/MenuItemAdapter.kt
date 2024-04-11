package com.example.foodorderingapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapp.databinding.MenuItemLayoutBinding

class MenuItemAdapter(private val names:List<String>, private val images:List<Int>, private val prices:List<Int>) : RecyclerView.Adapter<MenuItemAdapter.MenuItemHolder>() {
    inner class MenuItemHolder(private val binding: MenuItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(name: String, image: Int, price: Int) {
            binding.menuItemName.text=name
            binding.menuItemImage.setImageResource(image)

            val viewPrice = "Rs.$price"
            binding.menuItemPrice.text = viewPrice
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuItemHolder {
        val view = MenuItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MenuItemHolder(view)
    }

    override fun getItemCount(): Int {
        return names.size
    }

    override fun onBindViewHolder(holder: MenuItemHolder, position: Int) {
        val name=names[position]
        val image=images[position]
        val price=prices[position]
        holder.bind(name,image,price)
    }
}