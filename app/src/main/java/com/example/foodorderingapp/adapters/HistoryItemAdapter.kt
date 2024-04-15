package com.example.foodorderingapp.adapters

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapp.FoodDescriptionActivity
import com.example.foodorderingapp.databinding.HistoryItemLayoutBinding

class HistoryItemAdapter(private val names : MutableList<String>,
                         private val images : MutableList<Int>,
                         private val restaurants : MutableList<String>,
                         private val prices : MutableList<Int>,
                         private val context : Context) : RecyclerView.Adapter<HistoryItemAdapter.HistoryItemHolder>() {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryItemAdapter.HistoryItemHolder {
        val view = HistoryItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HistoryItemHolder(view)
    }
    override fun getItemCount(): Int {
        return names.size
    }
    override fun onBindViewHolder(holder: HistoryItemAdapter.HistoryItemHolder, position: Int) {
        holder.bind(position)
        holder.itemView.setOnClickListener{
            Handler().postDelayed({
                val intent = Intent(context,FoodDescriptionActivity::class.java)

                val name = names[position]
                val image = images[position]
                val description = "This is very tasty Food" //TODO
                val ingredients = arrayListOf<String>("Wheat","Rice","Grains","Protein")
                intent.putExtra("key_name",name)
                intent.putExtra("key_image",image)
                intent.putExtra("key_description",description)
                intent.putStringArrayListExtra("key_ingredients",ingredients)

                context.startActivity(intent)
            },0)
        }
    }
}