package com.example.foodorderingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.util.Patterns
import android.widget.EditText
import com.example.foodorderingapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

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
    }
    private fun setListeners(){
        binding.loginActivitySignupText.setOnClickListener{
            val intent= Intent(this,SignupActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.loginActivityLoginButton.setOnClickListener{
            getUserData()

            if(validate()){
                createUser()
                val intent=Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
    private fun getUserData(){
        email = emailEditText.text.toString()
        password = passwordEditText.text.toString()
    }
    private fun validate() : Boolean{

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
    private fun createUser(){

    }
}