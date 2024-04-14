package com.example.foodorderingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.foodorderingapp.databinding.ActivityMainBinding
import com.example.foodorderingapp.fragment.bottomSheets.NotificationBottomSheetFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init(){
        setLayout()
        setListeners()
    }

    private fun setLayout(){
        binding.mainActivityBottomNavigation.setupWithNavController(findNavController(R.id.mainActivityFragmentContainer))
    }
    private fun setListeners(){
        binding.apply {
            mainActivityNotificationButton.setOnClickListener{
                val notificationBottomSheet = NotificationBottomSheetFragment()
                notificationBottomSheet.show(supportFragmentManager,"Tag")
            }
        }
    }
}