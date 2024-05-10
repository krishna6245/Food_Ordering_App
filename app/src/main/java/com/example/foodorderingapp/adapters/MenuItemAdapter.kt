package com.example.foodorderingapp.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapp.databinding.MenuItemLayoutBinding
import com.bumptech.glide.Glide
import com.example.foodorderingapp.FoodDescriptionActivity
import com.example.foodorderingapp.dataModels.MenuItemModel

class MenuItemAdapter(private val context : Context,
                      private val menuList : MutableList<MenuItemModel>) : RecyclerView.Adapter<MenuItemAdapter.MenuItemHolder>() {
    inner class MenuItemHolder(private val binding: MenuItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int) {
            binding.apply {
                val menuItem = menuList[position]

                menuItemName.text = menuItem.foodName

                val imageUri = Uri.parse(menuItem.foodImage)
                Glide.with(context).load(imageUri).into(menuItemImage)

                val viewPrice = "Rs.${menuItem.foodPrice}"
                menuItemPrice.text = viewPrice
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuItemHolder {
        val view = MenuItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MenuItemHolder(view)
    }
    override fun getItemCount(): Int {
        return menuList.size
    }
    override fun onBindViewHolder(holder: MenuItemHolder, position: Int) {
        holder.bind(position)
        holder.itemView.setOnClickListener{
            val intent = Intent(context , FoodDescriptionActivity::class.java )

            val menuItem = menuList[position]

            val name = menuItem.foodName
            val image = menuItem.foodImage
            val description = menuItem.foodDescription
            val ingredients = menuItem.foodIngredients

            intent.putExtra("key_name",name)
            intent.putExtra("key_image",image)
            intent.putExtra("key_description",description)
            if(ingredients != null){
                intent.putStringArrayListExtra("key_ingredients",ArrayList(ingredients))
            }

            context.startActivity(intent)
        }
    }
}