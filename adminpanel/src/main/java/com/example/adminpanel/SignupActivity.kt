package com.example.adminpanel

import android.app.Activity
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
import com.google.firebase.auth.GoogleAuthProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignupActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySignupBinding

    private lateinit var auth : FirebaseAuth
    private lateinit var database : DatabaseReference
    private lateinit var gso: GoogleSignInOptions
    private lateinit var googleSignInClient: GoogleSignInClient

    private val googleSignUpRequestCode = 100

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText

    private lateinit var email : String
    private lateinit var password : String
    private lateinit var confirmPassword : String

    fun toast(data : Any?){
        Toast.makeText(this,"$data",Toast.LENGTH_SHORT).show()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){
        bindUiElements()
        setListeners()
    }
    private fun bindUiElements(){
        emailEditText = binding.signupActivityEmailEditText
        passwordEditText = binding.signupActivityPasswordEditText
        confirmPasswordEditText = binding.signupActivityConfirmPasswordEditText

        auth = FirebaseAuth.getInstance()
        database = Firebase.database.getReference("admin panel")

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

    }
    private fun setListeners(){
        binding.signupActivityAlreadyHaveAccountButton.setOnClickListener {
            finish()
        }
        binding.signupActivityCreateAccountButton.setOnClickListener {
            getUserData()

            if(validateUserData()){
                email.trim()

                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {task ->

                    if(task.isSuccessful){
                        val intent = Intent(this@SignupActivity , GetUserDataActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        return@addOnCompleteListener
                    }

                    val exception = task.exception
                    Log.d("Signup Activity","",exception)
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
                    }
                    Toast.makeText(applicationContext , "Failed to create an Account. Try Again!!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        //Show/Hide Password
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

        binding.signupActivityGoogleButton.setOnClickListener{
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, googleSignUpRequestCode)
        }
    }
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == googleSignUpRequestCode) {
            if (resultCode == Activity.RESULT_OK){
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    firebaseAuthWithGoogle(account)
                } catch (e: ApiException) {
                    Toast.makeText(this,"Can't create Account. Try Again",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun firebaseAuthWithGoogle(account : GoogleSignInAccount) {
        val idToken = account.idToken
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val currentUser = auth.currentUser
                    if (currentUser != null) {

                        val intent = Intent(this@SignupActivity, GetUserDataActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        return@addOnCompleteListener
                    }
                    Toast.makeText(
                        applicationContext,
                        "Failed to Login to your Account. Try Again!!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
    private fun getUserData(){
        email = emailEditText.text.toString()
        password = passwordEditText.text.toString()
        confirmPassword = confirmPasswordEditText.text.toString()
    }
    private fun validateUserData() : Boolean{

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