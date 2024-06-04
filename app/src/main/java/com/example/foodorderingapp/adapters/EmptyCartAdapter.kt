package com.example.foodorderingapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapp.databinding.EmptyCartLayoutBinding

class EmptyCartAdapter : RecyclerView.Adapter<EmptyCartAdapter.EmptyCartHolder>() {
    class EmptyCartHolder (private val binding : EmptyCartLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmptyCartHolder {
        val view = EmptyCartLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return EmptyCartHolder(view)
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: EmptyCartHolder, position: Int) {

    }
}