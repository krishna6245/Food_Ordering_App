package com.example.foodorderingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import com.example.foodorderingapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val source=binding.loginActivtyContinueText.text.toString()
        val spannableStringBuilder = SpannableStringBuilder(source)
        spannableStringBuilder.setSpan(
            AbsoluteSizeSpan(40),
            0,
            3,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.loginActivtyContinueText.text=spannableStringBuilder
        binding.loginActivitySignupText.setOnClickListener{
            Handler().postDelayed({
                val intent= Intent(this,SignupActivity::class.java)
                startActivity(intent)
                finish()
            },0)
        }
        binding.loginActivityLoginButton.setOnClickListener{
            val email=binding.loginActivityEmail.text.toString()
            val password=binding.loginActivityPassword.text.toString()
            if(true){//TODO
                Handler().postDelayed({
                    val intent=Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                },0)
            }
        }
    }
}