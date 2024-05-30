package com.example.foodorderingapp.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.util.LogPrinter
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapp.databinding.MenuItemLayoutBinding
import com.bumptech.glide.Glide
import com.example.foodorderingapp.FoodDescriptionActivity
import com.example.foodorderingapp.dataModels.CartItemModel
import com.example.foodorderingapp.dataModels.MenuItemModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MenuItemAdapter(private val context : Context,
                      private val menuList : MutableList<MenuItemModel>) : RecyclerView.Adapter<MenuItemAdapter.MenuItemHolder>() {

    init {
        val auth = FirebaseAuth.getInstance()
        val database = FirebaseDatabase.getInstance()

        val userId = auth.currentUser?.uid?:""

        cartReference = database.reference.child("food ordering app").child("users").child(userId).child("cart items")
    }
    companion object{
        lateinit var cartReference: DatabaseReference
    }
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

            binding.menuItemCartButton.setOnClickListener{
                val cartItem = CartItemModel(menuList[position],1)
                cartReference.push().setValue(cartItem)
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
            val restaurant = menuItem.restaurantName
            val price = menuItem.foodPrice

            intent.putExtra("key_name",name)
            intent.putExtra("key_image",image)
            intent.putExtra("key_description",description)
            intent.putExtra("key_restaurant",restaurant)
            intent.putExtra("key_price",price)
            if(ingredients != null){
                intent.putStringArrayListExtra("key_ingredients",ArrayList(ingredients))
            }

            context.startActivity(intent)
        }
    }
}