package com.example.adminpanel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.adminpanel.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){
        setListeners()
    }
    private fun setListeners(){
        binding.apply {
            loginActivityNotHaveAccountButton.setOnClickListener{
                val intent = Intent(this@LoginActivity , SignupActivity::class.java)
                startActivity(intent)
            }
            loginActivityLoginButton.setOnClickListener {
                val intent = Intent(this@LoginActivity , MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
    }
}