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
import com.example.foodorderingapp.adapters.EmptyCartAdapter
import com.example.foodorderingapp.dataModels.CartItemModel
import com.example.foodorderingapp.dataModels.MenuItemModel
import com.example.foodorderingapp.databinding.FragmentCartBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CartFragment : Fragment() {
    private lateinit var binding:FragmentCartBinding
    private lateinit var cartFragmentAdapter: CartItemAdapter
    private lateinit var emptyCartAdapter: EmptyCartAdapter

    private lateinit var cartItemList: MutableList<CartItemModel>
    private lateinit var cartItemReferences: MutableList<String>

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var userId: String
    private lateinit var cartReference: DatabaseReference

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
        retrieveMenu()
    }
    private fun initializeUiElements(){
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        userId = auth.currentUser?.uid?:""

        cartReference = database.reference.child("food ordering app").child("users").child(userId).child("cart items")

        cartItemList = mutableListOf()
        cartItemReferences = mutableListOf()
    }
    private fun retrieveMenu(){
        cartReference.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val cartItem = snapshot.getValue(CartItemModel::class.java)
                if(cartItem!=null){
                    cartItemList.add(cartItem)
                    snapshot.key?.let { cartItemReferences.add(it) }
                    cartFragmentAdapter.notifyItemInserted(cartItemList.size-1)

                    if( binding.cartFragmentCartItemList.adapter == emptyCartAdapter ){
                        binding.cartFragmentCartItemList.adapter = cartFragmentAdapter
                    }
                }
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}//TODO
            override fun onChildRemoved(snapshot: DataSnapshot) {
                if(cartItemList.size == 0){
                    binding.cartFragmentCartItemList.adapter = emptyCartAdapter
                }
            }
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })
    }
    private fun setAdapters(){
        cartFragmentAdapter = CartItemAdapter(cartItemList,cartItemReferences,requireActivity())
        binding.cartFragmentCartItemList.layoutManager = LinearLayoutManager(requireContext())

        emptyCartAdapter = EmptyCartAdapter()

        binding.cartFragmentCartItemList.adapter = emptyCartAdapter
    }
    private fun setListeners(){
        binding.apply {
            cartFragmentProccedButton.setOnClickListener {
                if(cartItemList.size == 0){
                    Toast.makeText(context,"Cart is empty",Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                Handler().postDelayed({
                    val intent = Intent(requireContext() , PlaceOrderActivity::class.java)

                    intent.putParcelableArrayListExtra("key_cart_list", ArrayList(cartItemList))
                    startActivity(intent)
                },0)
            }
        }
    }

}