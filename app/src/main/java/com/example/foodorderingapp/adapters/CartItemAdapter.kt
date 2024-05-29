package com.example.foodorderingapp.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodorderingapp.FoodDescriptionActivity
import com.example.foodorderingapp.dataModels.CartItemModel
import com.example.foodorderingapp.databinding.CartItemLayoutBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CartItemAdapter(private val cartItems: MutableList<CartItemModel>,
                      private val cartItemId: MutableList<String>,
                      private val context : Context) : RecyclerView.Adapter<CartItemAdapter.CartFragmentMenuItemHolder>() {

    init {
        val auth = FirebaseAuth.getInstance()
        val database = FirebaseDatabase.getInstance()

        val userId = auth.currentUser?.uid?:""
        val cartSize = cartItems.size

        itemQuantities = IntArray(cartSize){1}
        cartItemReference = database.reference.child("food ordering app").child("users").child(userId).child("cart items")

    }

    companion object{
        private var itemQuantities = intArrayOf()
        private lateinit var cartItemReference : DatabaseReference
    }

    inner class CartFragmentMenuItemHolder(private val binding : CartItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {

            val cartItem = cartItems[position]
            val menuItem = cartItem.menuItem

            if (menuItem != null) {
                binding.cartItemName.text = menuItem.foodName
                val viewPrice = "Rs.${menuItem.foodPrice}"
                binding.cartItemPrice.text=viewPrice

                Glide.with(context).load(Uri.parse(menuItem.foodImage)).into(binding.cartItemImage)

                binding.cartItemQuantity.text=cartItem.quantity.toString()
            }

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
            val cartItem = cartItems[position]

            cartItem.quantity = cartItem.quantity?.plus(1);
            binding.cartItemQuantity.text = cartItem.quantity.toString()
            cartItems[position] = cartItem

            updateDatabaseChange(position)
        }
        private fun decreaseItem(position: Int){
            val cartItem = cartItems[position]

            cartItem.quantity = cartItem.quantity?.minus(1);
            if(cartItem.quantity==0){
                removeItem(position)
                return
            }
            binding.cartItemQuantity.text = cartItem.quantity.toString()
            cartItems[position] = cartItem
            updateDatabaseChange(position)
        }
        private fun removeItem(position: Int){
            updateDatabaseRemoval(position)
            cartItems.removeAt(position)
            cartItemId.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,cartItems.size)
        }
        private fun updateDatabaseChange(position: Int){
            cartItemReference.child(cartItemId[position]).setValue(cartItems[position])
        }
        private fun updateDatabaseRemoval(position: Int){
            cartItemReference.child(cartItemId[position]).removeValue().addOnSuccessListener {
                Toast.makeText(context,"Item removal successful",Toast.LENGTH_SHORT).show()
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartFragmentMenuItemHolder {
        val view = CartItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CartFragmentMenuItemHolder(view)
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    override fun onBindViewHolder(holder: CartFragmentMenuItemHolder, position: Int) {
        holder.bind(position)

        holder.itemView.setOnClickListener{
            Handler().postDelayed({
                val intent = Intent(context , FoodDescriptionActivity::class.java)

                val cartItem = cartItems[position]
                val menuItem = cartItem.menuItem

                val name = menuItem?.foodName
                val image = menuItem?.foodImage
                val description = menuItem?.foodDescription
                val ingredients = menuItem?.foodIngredients?.let { it1 -> ArrayList(it1) }
                val restaurant = menuItem?.restaurantName
                val price = menuItem?.foodPrice

                intent.putExtra("key_name",name)
                intent.putExtra("key_image",image)
                intent.putExtra("key_description",description)
                intent.putStringArrayListExtra("key_ingredients",ingredients)
                intent.putExtra("key_restaurant",restaurant)
                intent.putExtra("key_price",price)

                context.startActivity(intent)
            },0)
        }
    }
}