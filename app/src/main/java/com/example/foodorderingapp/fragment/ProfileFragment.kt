package com.example.foodorderingapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.foodorderingapp.R
import com.example.foodorderingapp.dataModels.UserModel
import com.example.foodorderingapp.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.ref.Reference

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    private lateinit var userReference: DatabaseReference
    private lateinit var userId: String
    private var userData: UserModel? = null

    private var dataEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater,container,false)

        init()

        return binding.root
    }
    private fun toast(data: Any?){
        Toast.makeText(context,"$data",Toast.LENGTH_SHORT).show()
    }
    private fun init(){
        initializeUiElements()
        switchData()
        setListeners()
    }
    private fun initializeUiElements(){
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        userId = auth.currentUser!!.uid.toString()

        userReference = database.reference.child("food ordering app").child("users").child(userId)
        userReference.child("user data").addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    userData = dataSnapshot.getValue(UserModel::class.java)
                    if (userData != null) {
                        fillUserData()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                toast("Can't fetch user data!!")
            }

        })
    }
    private fun fillUserData(){
        binding.apply {
            profileFragmentNameEditText.setText(userData!!.userName)
            profileFragmentAddressEditText.setText(userData!!.address)
            profileFragmentEmailEditText.setText(userData!!.email)
            profileFragmentPhoneNumberEditText.setText(userData!!.phoneNumber)

            profileFragmentEmailEditText.isEnabled = false
        }
    }
    private fun setListeners(){
        binding.profileFragmentSaveButton.setOnClickListener {
            userReference.child("user data").setValue(userData)
        }
        binding.profileFragmentEditDetailsButton.setOnClickListener{
            switchData()
        }
    }
    private fun switchData(){
        binding.apply {
            profileFragmentNameEditText.isEnabled = dataEnabled
            profileFragmentAddressEditText.isEnabled = dataEnabled
            profileFragmentPhoneNumberEditText.isEnabled = dataEnabled
        }
        dataEnabled = !dataEnabled
    }
}