package com.example.foodorderingapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapp.databinding.HistoryItemLayoutBinding

class HistoryItemAdapter(private val names : MutableList<String>, private val images : MutableList<Int>,private val restaurants : MutableList<String>, private val prices : MutableList<Int>) : RecyclerView.Adapter<HistoryItemAdapter.HistoryItemHolder>() {
    inner class HistoryItemHolder(private val binding : HistoryItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            val name = names[position]
            val restaurant = restaurants[position]
            val image = images[position]
            val viewPrice = "Rs.${prices[position]}"

            binding.apply {
                historyItemFoodName.text = name
                historyItemFoodImage.setImageResource(image)
                historyItemFoodPrice.text = viewPrice
                historyItemFoodRestaurant.text = restaurant
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryItemAdapter.HistoryItemHolder {
        val view = HistoryItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HistoryItemHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryItemAdapter.HistoryItemHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return names.size
    }
}