package com.example.adminpanel.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminpanel.databinding.ActivityViewMenuBinding
import com.example.adminpanel.databinding.ViewMenuItemLayoutBinding

class ViewMenuAdapter(private val context: Context,
                      private val names : MutableList<String>,
                      private val restaurants : MutableList<String>,
                      private val images : MutableList<Int>,
                      private val prices : MutableList<Int>,
                      private val quantities : MutableList<Int>) : RecyclerView.Adapter<ViewMenuAdapter.ViewMenuHolder>(){
    inner class ViewMenuHolder(private val binding: ViewMenuItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            binding.apply {
                viewMenuFoodName.text = names[position]
                viewMenuFoodImage.setImageResource(images[position])
                viewMenuFoodRestaurantName.text = restaurants[position]
                viewMenuFoodQuantity.text = quantities[position].toString()

                val viewPrice = "Rs.${prices[position]}"
                viewMenuFoodPrice.text = viewPrice

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
            quantities[position]--;
            if(quantities[position]==0){
                removeItem(position)
                return
            }
            binding.viewMenuFoodQuantity.text = quantities[position].toString()
        }
        private fun removeItem(position: Int){
            quantities.removeAt(position)
            names.removeAt(position)
            prices.removeAt(position)
            images.removeAt(position)
            restaurants.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,images.size)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewMenuHolder {
        val view = ViewMenuItemLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewMenuHolder(view)
    }

    override fun getItemCount(): Int {
        return names.size
    }

    override fun onBindViewHolder(holder: ViewMenuHolder, position: Int) {
        holder.bind(position)
    }
}