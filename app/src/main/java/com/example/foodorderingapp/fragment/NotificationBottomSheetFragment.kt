package com.example.foodorderingapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderingapp.R
import com.example.foodorderingapp.adapters.NotificationItemAdapter
import com.example.foodorderingapp.databinding.FragmentNotificationBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class NotificationBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding :  FragmentNotificationBottomSheetBinding
    private lateinit var notificationBottomSheetAdapter: NotificationItemAdapter
    private lateinit var orderTypes : MutableList<Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationBottomSheetBinding.inflate(inflater,container,false)
        init()
        return binding.root
    }
    private fun init(){
        setLists()
        setAdapters()
    }
    private fun setLists(){
        orderTypes = mutableListOf(1,0,2,1,0,1,0,1,0,2,2)
    }
    private fun setAdapters(){
        notificationBottomSheetAdapter = NotificationItemAdapter(orderTypes)
        binding.notificationBottomSheetItemList.layoutManager = LinearLayoutManager(requireContext())
        binding.notificationBottomSheetItemList.adapter = notificationBottomSheetAdapter

    }
}