package com.example.foodorderingapp.adapters

import android.content.Context
import android.content.Intent
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapp.databinding.MenuItemLayoutBinding
import android.os.Handler
import com.example.foodorderingapp.FoodDescriptionActivity

class MenuItemAdapter(private val names : List<String>,
                      private val images : List<Int>,
                      private val prices : List<Int>,
                      private val context : Context) : RecyclerView.Adapter<MenuItemAdapter.MenuItemHolder>() {
    inner class MenuItemHolder(private val binding: MenuItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int) {
            binding.apply {
                menuItemName.text=names[position]
                menuItemImage.setImageResource(images[position])
                val viewPrice = "Rs.${prices[position]}"
                menuItemPrice.text = viewPrice
            }
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
        holder.bind(position)
        holder.itemView.setOnClickListener{
            Handler().postDelayed({
                val intent = Intent(context , FoodDescriptionActivity::class.java )

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