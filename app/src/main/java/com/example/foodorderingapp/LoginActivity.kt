package com.example.foodorderingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import com.example.foodorderingapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private lateinit var auth : FirebaseAuth
    private lateinit var database : DatabaseReference

    private lateinit var email : String
    private lateinit var password : String

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){
        setLayout()
        initializeUiElements()
        setListeners()
    }
    private fun setLayout(){
        val source=binding.loginActivtyContinueText.text.toString()
        val spannableStringBuilder = SpannableStringBuilder(source)
        spannableStringBuilder.setSpan(
            AbsoluteSizeSpan(40),
            0,
            3,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.loginActivtyContinueText.text=spannableStringBuilder
    }
    private fun initializeUiElements(){
        emailEditText = binding.loginActivityEmail
        passwordEditText = binding.loginActivityPassword

        auth = FirebaseAuth.getInstance()
        database = Firebase.database.getReference("food ordering app")
    }
    private fun setListeners(){
        binding.loginActivitySignupText.setOnClickListener{
            val intent= Intent(this,SignupActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.loginActivityLoginButton.setOnClickListener{
            getUserData()

            if(validateUserData()){
                email.trim()
                password.trim()

                auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener {task ->
                        if(task.isSuccessful){
                            val userId = auth.currentUser
                            val intent=Intent(this,MainActivity::class.java)
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
    }
    private fun getUserData(){
        email = emailEditText.text.toString()
        password = passwordEditText.text.toString()
    }
    private fun validateUserData() : Boolean{

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