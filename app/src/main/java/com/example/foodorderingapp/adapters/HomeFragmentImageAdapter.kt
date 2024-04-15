package com.example.foodorderingapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.foodorderingapp.R

class HomeFragmentImageAdapter (private val imageList : ArrayList<Int>, private val viewPager : ViewPager2)
    : RecyclerView.Adapter<HomeFragmentImageAdapter.HomeFragmentImageViewHolder>(){

    class HomeFragmentImageViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val imageView = itemView.findViewById<ImageView>(R.id.imageViewContainerHomeFragment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeFragmentImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_layout_home_fragment , parent , false)
        return HomeFragmentImageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: HomeFragmentImageViewHolder, position: Int) {
        holder.imageView.setImageResource(imageList[position])
        if(position == imageList.size - 1){
            viewPager.post(runnable)
        }
    }

    private val runnable = Runnable {
        imageList.addAll(imageList)
        notifyDataSetChanged()
    }

}