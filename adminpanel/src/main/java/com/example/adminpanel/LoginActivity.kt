package com.example.adminpanel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import com.example.adminpanel.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding

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
    }
    private fun setListeners(){
        binding.apply {
            loginActivityNotHaveAccountButton.setOnClickListener{
                val intent = Intent(this@LoginActivity , SignupActivity::class.java)
                startActivity(intent)
            }
            loginActivityLoginButton.setOnClickListener {
                getUserDetails()

                if(validateUserDetails()){
                    loginUserWithEmailAndPassword()

                    val intent = Intent(this@LoginActivity , MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
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
    private fun loginUserWithEmailAndPassword(){

    }
}