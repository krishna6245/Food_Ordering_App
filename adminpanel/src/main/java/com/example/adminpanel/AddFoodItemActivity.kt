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
import com.example.adminpanel.databinding.ActivityAddFoodItemBinding
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
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

    private lateinit var foodIngredientsList : MutableList<String>
    private var foodImage : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFoodItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){
        buildUiElements()
        setListeners()
    }
    private fun buildUiElements(){
        foodNameEditText = binding.addFoodItemActivityFoodName
        foodPriceEditText = binding.addFoodItemActivityFoodPrice
        foodImageButton = binding.addFoodItemActivityAddFoodImageButton
        foodDescriptionEditText = binding.addFoodItemActivityFoodDescription
        foodIngredientsEditText = binding.addFoodItemActivityEnterIngredients

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        foodIngredientsList = mutableListOf()
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
    private val pickImage =
        registerForActivityResult(ActivityResultContracts.GetContent()){uri ->
            if(uri != null){
                binding.addFoodItemActivityFoodImage.setImageURI(uri)
//                binding.addFoodItemActivityFoodImageName.text = uri.toString()
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

    private fun toast(s : String?){
        Log.d("tag","${s}")
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show()
    }
}