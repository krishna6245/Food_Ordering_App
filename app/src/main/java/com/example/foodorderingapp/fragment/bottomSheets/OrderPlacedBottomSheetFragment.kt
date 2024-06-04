package com.example.foodorderingapp.fragment.bottomSheets

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.example.foodorderingapp.MainActivity
import com.example.foodorderingapp.databinding.FragmentOrderPlacedBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class OrderPlacedBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomSheet = view.parent as View
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
    }
    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        (activity as? FragmentActivity)?.finish()
    }
    private fun init(){
        setListeners()
    }
    private fun setListeners(){
        binding.apply {
            orderPlacedBottomSheetGoHomeButton.setOnClickListener {
                closeBottomSheet()
            }
        }
    }
    private fun closeBottomSheet(){
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }
}