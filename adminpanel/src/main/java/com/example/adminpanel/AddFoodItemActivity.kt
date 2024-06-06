package com.example.adminpanel

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.adminpanel.dataModels.MenuItemModel
import com.example.adminpanel.dataModels.UserModel
import com.example.adminpanel.databinding.ActivityAddFoodItemBinding
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.lang.Exception

class AddFoodItemActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddFoodItemBinding

    private lateinit var foodNameEditText : EditText
    private lateinit var foodPriceEditText : EditText
    private lateinit var foodImageButton : ImageView
    private lateinit var foodDescriptionEditText : EditText
    private lateinit var foodIngredientsEditText : EditText

    private lateinit var auth : FirebaseAuth
    private lateinit var database : FirebaseDatabase

    private lateinit var userId: String
    private lateinit var userReference: DatabaseReference
    private lateinit var restaurantName: String
    private var userData: UserModel = UserModel()

    private lateinit var foodIngredientsList : MutableList<String>
    private var foodImage : Uri? = null

    private fun toast(s : Any?){
        Toast.makeText(this,"$s",Toast.LENGTH_SHORT).show()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFoodItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){
        initializeUiElements()
        setListeners()
    }
    private fun initializeUiElements(){
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        userId = auth.currentUser!!.uid
        userReference = database.reference.child("admin panel").child("users").child(userId)

        userReference.child("user data").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userData = snapshot.getValue(UserModel::class.java)!!
                restaurantName = userData.restaurantName.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                toast("Can't connect to server!! Try Again Later")
            }
        })

        foodIngredientsList = mutableListOf()

        foodNameEditText = binding.addFoodItemActivityFoodName
        foodPriceEditText = binding.addFoodItemActivityFoodPrice
        foodImageButton = binding.addFoodItemActivityAddFoodImageButton
        foodDescriptionEditText = binding.addFoodItemActivityFoodDescription
        foodIngredientsEditText = binding.addFoodItemActivityEnterIngredients
    }
    private fun setListeners(){
        binding.addFoodItemActivityBackButton.setOnClickListener {
            finish()
        }
        binding.addFoodItemActivityAddIngredientsButton.setOnClickListener{
            val ingredient = foodIngredientsEditText.text.toString().trim()

            foodIngredientsEditText.setText("")

            if(ingredient.isBlank()){
                foodIngredientsEditText.error = "Enter ingredient name"
                foodIngredientsEditText.requestFocus()
                return@setOnClickListener
            }

            var ingredients = binding.addFoodItemActivityIngredientsList.text.toString().trim()
            ingredients = ingredients + "\n" + ingredient
            binding.addFoodItemActivityIngredientsList.setText(ingredients)

            foodIngredientsList.add(ingredient)
        }
        binding.addFoodItemActivityAddItemButton.setOnClickListener{
            binding.addFoodItemActivityAddItemButton.isEnabled = false
            createMenuItem()
        }
        foodImageButton.setOnClickListener{
            pickImage.launch("image/*")
        }
    }
    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()){uri ->
            if(uri != null){
                binding.addFoodItemActivityFoodImage.setImageURI(uri)
                binding.addFoodItemActivityFoodImageName.setText(uri.toString())
                foodImage = uri
            }
        }
    private fun createMenuItem(){
        val foodName = foodNameEditText.text.toString().trim()
        val foodPrice = foodPriceEditText.text.toString().trim()
        val foodDescription = foodDescriptionEditText.text.toString()
        val foodIngredientList = binding.addFoodItemActivityIngredientsList.text.toString().trim()

        if(foodName.isBlank()){
            foodNameEditText.error = "Enter Food Name"
            foodNameEditText.requestFocus()
            return
        }
        if(foodPrice.isBlank()){
            foodPriceEditText.error = "Enter Food Price"
            foodPriceEditText.requestFocus()
            return
        }
        if(foodPrice.toIntOrNull() == null){
            foodPriceEditText.error = "Invalid Input Format"
            foodPriceEditText.requestFocus()
            return
        }
        if(foodDescription.isBlank()){
            foodDescriptionEditText.error = "Enter Description"
            foodDescriptionEditText.requestFocus()
            return
        }
        if(foodImage == null){
            foodImageButton.requestFocus()
            return
        }
        if(foodIngredientList.isBlank()){
            foodIngredientsEditText.error = "Enter Some Ingredients"
            foodIngredientsEditText.requestFocus()
            return
        }

        val menuReference = database.getReference("menu")
        val menuItemKey = menuReference.push().key

        val storageReference = FirebaseStorage.getInstance().reference
        val imageReference = storageReference.child("menu_images/${menuItemKey}.jpg")

        val uploadImage = imageReference.putFile(foodImage!!)

        uploadImage.addOnSuccessListener {
            imageReference.downloadUrl
                .addOnSuccessListener { downloadUrl ->
                    val newMenuItem = MenuItemModel(
                        foodName = foodName,
                        restaurantName = restaurantName,
                        foodPrice = foodPrice.toInt(),
                        foodImage = downloadUrl.toString(),
                        foodDescription = foodDescription,
                        foodIngredients = foodIngredientsList
                    )
                    menuItemKey?.let { key ->
                        menuReference.child(key).setValue(newMenuItem)
                        toast("Item Created Successfully")
                        finish()
                    }

                }
                .addOnFailureListener {
                    toast("Image Upload Failed")
                }
            }
            .addOnFailureListener {
                toast("Please Select an Image")
            }
    }
}