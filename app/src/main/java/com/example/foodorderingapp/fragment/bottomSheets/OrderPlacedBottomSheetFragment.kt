package com.example.foodorderingapp.fragment.bottomSheets

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foodorderingapp.MainActivity
import com.example.foodorderingapp.databinding.FragmentOrderPlacedBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class OrderPlacedBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentOrderPlacedBottomSheetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderPlacedBottomSheetBinding.inflate(inflater,container,false)
        init()
        return binding.root
    }
    private fun init(){
        setListeners()
    }
    private fun setListeners(){
        binding.apply {
            orderPlacedBottomSheetGoHomeButton.setOnClickListener {
                Handler().postDelayed({
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                },0)
            }
        }
    }
}