package com.example.adminpanel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import com.example.adminpanel.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding

    private lateinit var auth : FirebaseAuth
    private lateinit var database : DatabaseReference

    private lateinit var emailEditText : EditText
    private lateinit var passwordEditText : EditText

    private lateinit var email : String
    private lateinit var password : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){
        bindUiElements()
        setListeners()
    }
    private fun bindUiElements(){
        emailEditText = binding.loginActivityEmailEditText
        passwordEditText = binding.loginActivityPasswordEditText

        auth = FirebaseAuth.getInstance()
        database = Firebase.database.getReference("admin panel")
    }
    private fun setListeners(){
        binding.loginActivityNotHaveAccountButton.setOnClickListener{
            val intent = Intent(this@LoginActivity , SignupActivity::class.java)
            startActivity(intent)
        }
        binding.loginActivityLoginButton.setOnClickListener {
            getUserDetails()

            if(validateUserDetails()){
                email.trim()
                password.trim()

                auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener {task ->
                        if(task.isSuccessful){
                            val userId = auth.currentUser
                            val intent = Intent(this@LoginActivity , MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                    }
                    .addOnFailureListener {exception ->
                        if (exception is FirebaseAuthInvalidCredentialsException) {
                            val error = exception.errorCode

                            if (error == "ERROR_USER_NOT_FOUND") {
                                emailEditText.error = "Email Not Found!!"
                                emailEditText.requestFocus()
                            }
                            if (error == "ERROR_WRONG_PASSWORD") {
                                passwordEditText.error = "Incorrect Password"
                                passwordEditText.requestFocus()
                            }
                            Toast.makeText(
                                applicationContext,
                                "Failed to Sign In. Try Again!!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
        binding.loginActivityShowPasswordButton.setOnClickListener{
            if(binding.loginActivityPasswordEditText.inputType != InputType.TYPE_CLASS_TEXT){
                binding.loginActivityPasswordEditText.inputType = InputType.TYPE_CLASS_TEXT
                binding.loginActivityShowPasswordButton.setImageResource(R.drawable.password_shown_icon)
            }
            else{
                binding.loginActivityPasswordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.loginActivityShowPasswordButton.setImageResource(R.drawable.password_hidden_icon)
            }
        }
    }
    private fun getUserDetails(){
        email = emailEditText.text.toString()
        password = passwordEditText.text.toString()
    }
    private fun validateUserDetails() : Boolean{

        // Checks on Email
        if (email.isEmpty()){
            emailEditText.error = "Email is empty!!"
            emailEditText.requestFocus()
            return false
        }
        if (email.isBlank()){
            emailEditText.error = "Email is blank!!"
            emailEditText.setText("")
            emailEditText.requestFocus()
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.error = "Email is not valid!!"
            emailEditText.requestFocus()
            return false
        }

        // Checks on Password
        if (password.isEmpty()){
            passwordEditText.error = "Password is empty!!"
            passwordEditText.requestFocus()
            return false
        }
        return true
    }
}