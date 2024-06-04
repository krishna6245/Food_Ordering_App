package com.example.foodorderingapp.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodorderingapp.FoodDescriptionActivity
import com.example.foodorderingapp.dataModels.HistoryItemModel
import com.example.foodorderingapp.databinding.HistoryItemLayoutBinding

class HistoryItemAdapter(private val historyItems: MutableList<HistoryItemModel>,
                         private val context : Context) : RecyclerView.Adapter<HistoryItemAdapter.HistoryItemHolder>() {
    inner class HistoryItemHolder(private val binding : HistoryItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            binding.apply {
                val historyItem = historyItems[position]
                val menuItem = historyItem.menuItem!!

                historyItemFoodName.text = menuItem.foodName

                val imageUri = Uri.parse(menuItem.foodImage)
                Glide.with(context).load(imageUri).into(historyItemFoodImage)

                val viewPrice = "Rs.${menuItem.foodPrice}"
                historyItemFoodPrice.text = viewPrice
                historyItemFoodRestaurant.text =  menuItem.restaurantName
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryItemAdapter.HistoryItemHolder {
        val view = HistoryItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HistoryItemHolder(view)
    }
    override fun getItemCount(): Int {
        return historyItems.size
    }
    override fun onBindViewHolder(holder: HistoryItemAdapter.HistoryItemHolder, position: Int) {
        holder.bind(position)
        holder.itemView.setOnClickListener{
            Handler().postDelayed({
                val intent = Intent(context,FoodDescriptionActivity::class.java)

                val historyItem = historyItems[position]
                val menuItem = historyItem.menuItem

                val name = menuItem?.foodName
                val image = menuItem?.foodImage
                val description = menuItem?.foodDescription
                val ingredients = menuItem?.foodIngredients
                val restaurant = menuItem?.restaurantName
                val price = menuItem?.foodPrice

                intent.putExtra("key_name",name)
                intent.putExtra("key_image",image)
                intent.putExtra("key_description",description)
                intent.putExtra("key_restaurant",restaurant)
                intent.putExtra("key_price",price)
                if(ingredients != null){
                    intent.putStringArrayListExtra("key_ingredients",ArrayList(ingredients))
                }

                context.startActivity(intent)

                context.startActivity(intent)
            },0)
        }
    }
}