package com.example.foodorderingapp.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderingapp.PlaceOrderActivity
import com.example.foodorderingapp.R
import com.example.foodorderingapp.adapters.CartItemAdapter
import com.example.foodorderingapp.dataModels.CartItemModel
import com.example.foodorderingapp.databinding.FragmentCartBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CartFragment : Fragment() {
    private lateinit var binding:FragmentCartBinding
    private lateinit var cartFragmentAdapter: CartItemAdapter

    private lateinit var cartItemList: MutableList<CartItemModel>
    private lateinit var cartItemReferences: MutableList<String>

    private lateinit var cartFragmentFoodNames : MutableList<String>
    private lateinit var cartFragmentFoodImages : MutableList<Int>
    private lateinit var cartFragmentFoodPrices : MutableList<Int>
    private lateinit var cartFragmentFoodQuantities : MutableList<Int>

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater,container,false)
        init()
        return binding.root
    }
    private fun init(){
        initializeUiElements()
        setListeners()
        setAdapters()
    }
    private fun initializeUiElements(){
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        cartItemList = mutableListOf()
        cartItemReferences = mutableListOf()

        cartFragmentFoodNames = mutableListOf()
        cartFragmentFoodImages = mutableListOf()
        cartFragmentFoodPrices = mutableListOf()
        cartFragmentFoodQuantities = mutableListOf()
    }
    private fun setAdapters(){

        val userId = auth.currentUser?.uid?:""

        val cartReference = database.reference.child("food ordering app").child("users").child(userId).child("cart items")

        cartReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children){
                    val cartItem = dataSnapshot.getValue(CartItemModel::class.java)
                    if (cartItem != null) {
                        cartItemList.add(cartItem)
                        dataSnapshot.key?.let { cartItemReferences.add(it) }
                    }
                }
                cartFragmentAdapter = CartItemAdapter(cartItemList,cartItemReferences,requireActivity())
                binding.cartFragmentCartItemList.layoutManager = LinearLayoutManager(requireContext())
                binding.cartFragmentCartItemList.adapter = cartFragmentAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(),"Database Access Failed",Toast.LENGTH_SHORT).show()
            }

        })


    }
    private fun setListeners(){
        binding.apply {
            cartFragmentProccedButton.setOnClickListener {
                Handler().postDelayed({
                    val intent = Intent(requireContext() , PlaceOrderActivity::class.java)

                    intent.putParcelableArrayListExtra("key_cart_list", ArrayList(cartItemList))
                    startActivity(intent)
                },0)
            }
        }
    }

}