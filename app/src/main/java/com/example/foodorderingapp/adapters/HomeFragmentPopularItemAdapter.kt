package com.example.foodorderingapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapp.databinding.PopularItemHomeFragmentBinding

class HomeFragmentPopularItemAdapter(private val names:List<String>,private val images:List<Int>,private val prices:List<Int>) : RecyclerView.Adapter<HomeFragmentPopularItemAdapter.HomeFragmentPopularItemHolder>() {
    class HomeFragmentPopularItemHolder(private val binding: PopularItemHomeFragmentBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(name: String, image: Int, price: Int) {
            binding.popularItemName.text=name
            binding.popularItemImage.setImageResource(image)
            binding.popularItemPrice.text = "Rs.$price"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeFragmentPopularItemHolder {
        val view = PopularItemHomeFragmentBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HomeFragmentPopularItemHolder(view)
    }

    override fun getItemCount(): Int {
        return names.size
    }

    override fun onBindViewHolder(holder: HomeFragmentPopularItemHolder, position: Int) {
        val name=names[position]
        val image=images[position]
        val price=prices[position]
        holder.bind(name,image,price)
    }
}