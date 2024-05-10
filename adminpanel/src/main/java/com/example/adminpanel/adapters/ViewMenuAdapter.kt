package com.example.adminpanel.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminpanel.dataModels.MenuItemModel
import com.example.adminpanel.databinding.ActivityViewMenuBinding
import com.example.adminpanel.databinding.ViewMenuItemLayoutBinding

class ViewMenuAdapter(private val context: Context,
                      private val menuList : MutableList<MenuItemModel>,
                      private val quantities : MutableList<Int>) : RecyclerView.Adapter<ViewMenuAdapter.ViewMenuHolder>(){
    inner class ViewMenuHolder(private val binding: ViewMenuItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            binding.apply {
                val menuItem = menuList[position]

                viewMenuFoodName.text = menuItem.foodName
                viewMenuFoodRestaurantName.text = menuItem.restaurantName

                val viewPrice = "Rs.${menuItem.foodPrice}"
                viewMenuFoodPrice.text = viewPrice

                val imageUri = Uri.parse(menuItem.foodImage)
                Glide.with(context).load(imageUri).into(viewMenuFoodImage)

                viewMenuFoodQuantity.text = quantities[position].toString()

                viewMenuIncreaseButton.setOnClickListener {
                    increaseItem(position)
                }
                viewMenuDecreaseButton.setOnClickListener {
                    decreaseItem(position)
                }
                viewMenuDeleteButton.setOnClickListener {
                    removeItem(position)
                }
            }
        }

        private fun increaseItem(position: Int){
            quantities[position]++;
            binding.viewMenuFoodQuantity.text = quantities[position].toString()
        }
        private fun decreaseItem(position: Int){
            quantities[position]--
            if(quantities[position]==0){
                removeItem(position)
                return
            }
            binding.viewMenuFoodQuantity.text = quantities[position].toString()
        }
        private fun removeItem(position: Int){
            menuList.removeAt(position)
            quantities.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,menuList.size)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewMenuHolder {
        val view = ViewMenuItemLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewMenuHolder(view)
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    override fun onBindViewHolder(holder: ViewMenuHolder, position: Int) {
        holder.bind(position)
    }
}