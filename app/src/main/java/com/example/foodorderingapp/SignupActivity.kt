package com.example.foodorderingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import com.example.foodorderingapp.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val source=binding.signupActivtyContinueText.text.toString()
        val spannableStringBuilder = SpannableStringBuilder(source)
        spannableStringBuilder.setSpan(
            AbsoluteSizeSpan(40),
            0,
            3,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.signupActivtyContinueText.text=spannableStringBuilder
        binding.signupActivitySignupText.setOnClickListener{
            Handler().postDelayed({
                val intent= Intent(this,LoginActivity::class.java)
                startActivity(intent)
                finish()
            },0)
        }
        binding.signupActivityLoginButton.setOnClickListener{
            val email=binding.signupActivityEmail.text.toString()
            val password=binding.signupActivityPassword.text.toString()
            if(true){//TODO
                Handler().postDelayed({
                    val intent= Intent(this,SelectLocationActivity::class.java)
                    startActivity(intent)
                    finish()
                },0)
            }
        }
    }
}