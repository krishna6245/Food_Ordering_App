package com.example.adminpanel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import com.example.adminpanel.databinding.ActivitySignupBinding
import android.widget.ArrayAdapter
import android.util.Patterns
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.Toast
import com.example.adminpanel.dataModels.AdminUserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignupActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySignupBinding
    private lateinit var locationList : MutableList<String>
    private lateinit var locationItemAdapter : ArrayAdapter<String>

    private lateinit var auth : FirebaseAuth
    private lateinit var database : DatabaseReference

    private lateinit var ownerNameEditText: EditText
    private lateinit var businessNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var locationAutoCompleteTextView: AutoCompleteTextView

    private lateinit var ownerName : String
    private lateinit var businessName : String
    private lateinit var email : String
    private lateinit var password : String
    private lateinit var confirmPassword : String
    private lateinit var location : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){
        setLists()
        bindUiElements()
        setListeners()
    }
    private fun setLists(){
        locationList = mutableListOf("Agra","Firozabad","Mathura","Hathras","Tundla","Samshabad","Aligarh","Shikohabad","Mainpuri")
    }
    private fun bindUiElements(){
        locationItemAdapter = ArrayAdapter(this , android.R.layout.simple_list_item_1 , locationList)
        binding.signupActivityLocationList.setAdapter(locationItemAdapter)

        ownerNameEditText = binding.signupActivityOwnerName
        businessNameEditText = binding.signupActivityBusinessName
        emailEditText = binding.signupActivityEmail
        passwordEditText = binding.signupActivityPasswordEditText
        confirmPasswordEditText = binding.signupActivityConfirmPasswordEditText
        locationAutoCompleteTextView = binding.signupActivityLocationList

        auth = FirebaseAuth.getInstance()
        database = Firebase.database.getReference("admin panel")
    }
    private fun setListeners(){
        binding.signupActivityAlreadyHaveAcountButton.setOnClickListener {
            finish()
        }
        binding.signupActivityCreateAccountButton.setOnClickListener {
            getUserData()

            if(validateUserData()){

                location.trim()
                ownerName.trim()
                businessName.trim()
                email.trim()

                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {task ->

                    if(task.isSuccessful){

                        val userId = auth.currentUser!!.uid
                        val adminUser = AdminUserModel(location,ownerName,businessName,email,password)
                        database.child("users").child(userId).setValue(adminUser)

                        val intent = Intent(this@SignupActivity , MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        return@addOnCompleteListener
                    }

                    val exception = task.exception
                    if (exception is FirebaseAuthException) {
                        val error = exception.errorCode

                        if (error == "ERROR_EMAIL_ALREADY_IN_USE"){
                            emailEditText.error = "Email Already in use!!"
                            emailEditText.requestFocus()
                            return@addOnCompleteListener
                        }
                        if (error == "ERROR_INVALID_EMAIL"){
                            emailEditText.error = "Invalid Email!!"
                            emailEditText.requestFocus()
                            return@addOnCompleteListener
                        }
                        if (error == "ERROR_WEAK_PASSWORD"){
                            passwordEditText.error = "Weak Password!!"
                            passwordEditText.requestFocus()
                            return@addOnCompleteListener
                        }
                        Toast.makeText(applicationContext , "Failed to create an Account. Try Again!!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        binding.signupActivityLocationList.setOnItemClickListener { _, _, _, _ ->
            locationAutoCompleteTextView.error = null
        }
        binding.signupActivityShowPasswordButton.setOnClickListener{
            if(binding.signupActivityPasswordEditText.inputType != InputType.TYPE_CLASS_TEXT){
                binding.signupActivityPasswordEditText.inputType = InputType.TYPE_CLASS_TEXT
                binding.signupActivityShowPasswordButton.setImageResource(R.drawable.password_shown_icon)
            }
            else{
                binding.signupActivityPasswordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.signupActivityShowPasswordButton.setImageResource(R.drawable.password_hidden_icon)
            }
        }
        binding.signupActivityShowConfirmPasswordButton.setOnClickListener{
            if(binding.signupActivityConfirmPasswordEditText.inputType != InputType.TYPE_CLASS_TEXT){
                binding.signupActivityConfirmPasswordEditText.inputType = InputType.TYPE_CLASS_TEXT
                binding.signupActivityShowConfirmPasswordButton.setImageResource(R.drawable.password_shown_icon)
            }
            else{
                binding.signupActivityConfirmPasswordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.signupActivityShowConfirmPasswordButton.setImageResource(R.drawable.password_hidden_icon)
            }
        }

    }
    private fun getUserData(){
        ownerName = ownerNameEditText.text.toString()
        businessName = businessNameEditText.text.toString()
        email = emailEditText.text.toString()
        password = passwordEditText.text.toString()
        confirmPassword = confirmPasswordEditText.text.toString()
        location = locationAutoCompleteTextView.text.toString()

        Log.d("Hello",password)
        Log.d("Hello",confirmPassword)
    }
    private fun validateUserData() : Boolean{

        // Checks on Location
        if (location.isEmpty()){
            binding.signupActivityLocationList.error = "Select a Location"
            locationAutoCompleteTextView.requestFocus()
            return false
        }

        // Checks on Owner Name
        if (ownerName.isEmpty()){
            ownerNameEditText.error = "Owner Name can't be Empty"
            ownerNameEditText.requestFocus()
            return false
        }
        if (ownerName.isBlank()){
            ownerNameEditText.error = "Invalid Owner Name"
            ownerNameEditText.setText("")
            ownerNameEditText.requestFocus()
            return false
        }

        // Checks on business Name
        if (businessName.isEmpty()){
            businessNameEditText.error = "Business Name can't be empty"
            businessNameEditText.requestFocus()
            return false
        }
        if (businessName.isBlank()){
            businessNameEditText.error = "Invalid Business Name"
            businessNameEditText.setText("")
            businessNameEditText.requestFocus()
            return false
        }

        // Checks on Email
        if (email.isEmpty()){
            emailEditText.error = "Email can't be empty"
            emailEditText.requestFocus()
            return false
        }
        if (email.isBlank()){
            emailEditText.error = "Email can't be blank"
            emailEditText.setText("")
            emailEditText.requestFocus()
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.error = "Email is not valid"
            emailEditText.requestFocus()
            return false
        }

        // Checks on Password
        val minLength = 8
        val minUpperCase = 1
        val minLowerCase = 1
        val minDigits = 1

        if (password.isEmpty()){
            passwordEditText.error = "Password can't be empty"
            passwordEditText.requestFocus()
            return false
        }
        if (password.isBlank()){
            passwordEditText.error = "Password can't be blank"
            passwordEditText.setText("")
            passwordEditText.requestFocus()
            return false
        }
        if (password[0] == ' '){
            passwordEditText.error = "Password can't start with a blank"
            passwordEditText.requestFocus()
            return false
        }
        if (password.last() == ' '){
            passwordEditText.error = "Password can't end with a blank"
            passwordEditText.requestFocus()
            return false
        }
        if (password.length < minLength){
            passwordEditText.error = "Password should be atleast $minLength characters long"
            passwordEditText.requestFocus()
            return false
        }
        if (password.count { it.isUpperCase() } < minUpperCase) {
            passwordEditText.error = "Password should have atleast $minUpperCase uppercase characters"
            passwordEditText.requestFocus()
            return false
        }
        if (password.count { it.isLowerCase() } < minLowerCase) {
            passwordEditText.error = "Password should have atleast $minLowerCase lowercase characters"
            passwordEditText.requestFocus()
            return false
        }
        if (password.count { it.isDigit() } < minDigits) {
            passwordEditText.error = "Password should have atleast $minDigits numbers"
            passwordEditText.requestFocus()
            return false
        }

        // Checks on Confirm Password
        if (password != confirmPassword){
            confirmPasswordEditText.error = "Passwords doesn't match"
            confirmPasswordEditText.requestFocus()
            return false
        }

        return true
    }
}