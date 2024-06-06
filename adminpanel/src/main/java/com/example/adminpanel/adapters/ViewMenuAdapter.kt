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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ViewMenuAdapter(private val context: Context,
                      private val menuList: MutableList<MenuItemModel>,
                      private val menuListReference: MutableList<String>,
                      private val userMenuListReference: MutableList<String>) : RecyclerView.Adapter<ViewMenuAdapter.ViewMenuHolder>(){

    init {
        val auth = FirebaseAuth.getInstance()
        val database = FirebaseDatabase.getInstance()

        val userId = auth.currentUser!!.uid

        userReference = database.reference.child("admin panel").child("users").child(userId)
        menuReference = database.reference.child("menu")
        userMenuReference = userReference.child("menu")
    }
    companion object{
        lateinit var userReference: DatabaseReference
        lateinit var menuReference: DatabaseReference
        lateinit var userMenuReference: DatabaseReference
    }
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
            }
            binding.viewMenuDeleteButton.setOnClickListener{
                val menuItemReference = menuListReference[position]
                val userMenuItemReference = userMenuListReference[position]

                menuReference.child(menuItemReference).removeValue()
                userMenuReference.child(userMenuItemReference).removeValue()

                menuList.removeAt(position)
                menuListReference.removeAt(position)
                userMenuListReference.removeAt(position)
                notifyDataSetChanged()
            }
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