package com.example.foodorderingapp.adapters

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapp.FoodDescriptionActivity
import com.example.foodorderingapp.databinding.CartItemLayoutBinding

class CartItemAdapter(private val names:MutableList<String>,
                      private val images:MutableList<Int>,
                      private val prices:MutableList<Int>,
                      private val quantities:MutableList<Int>,
                      private val context : Context) : RecyclerView.Adapter<CartItemAdapter.CartFragmentMenuItemHolder>() {
    inner class CartFragmentMenuItemHolder(private val binding : CartItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {

            binding.cartItemName.text=names[position]
            val viewPrice = "Rs.${prices[position]}"
            binding.cartItemPrice.text=viewPrice
            binding.cartItemQuantity.text=quantities[position].toString()
            binding.cartItemImage.setImageResource(images[position])

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
        val view = CartItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        view.cartItemIncrease.setOnClickListener{

        }

        return CartFragmentMenuItemHolder(view)
    }

    override fun getItemCount(): Int {
        return names.size
    }

    override fun onBindViewHolder(holder: CartFragmentMenuItemHolder, position: Int) {
        holder.bind(position)

        holder.itemView.setOnClickListener{
            Handler().postDelayed({
                val intent = Intent(context , FoodDescriptionActivity::class.java)
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