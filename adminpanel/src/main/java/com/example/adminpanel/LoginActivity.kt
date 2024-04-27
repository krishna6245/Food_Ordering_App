package com.example.adminpanel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import com.example.adminpanel.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding

    private lateinit var auth : FirebaseAuth
    private lateinit var database : DatabaseReference

    private lateinit var gso: GoogleSignInOptions
    private lateinit var googleSignInClient: GoogleSignInClient

    private val googleSignInRequestCode = 101

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

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
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
        binding.loginActivityGoogleButton.setOnClickListener{
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, googleSignInRequestCode)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == googleSignInRequestCode) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Toast.makeText(this,"Can't create Account. Try Again",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    //TODO
                    //Create user record in database

                    val intent = Intent(this@LoginActivity , MainActivity::class.java)
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
                    Toast.makeText(applicationContext , "Failed to Login to your Account. Try Again!!", Toast.LENGTH_SHORT).show()
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