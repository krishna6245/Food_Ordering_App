package com.example.foodorderingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.util.Log
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import com.example.foodorderingapp.dataModels.UserModel
import com.example.foodorderingapp.databinding.ActivitySignupBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignupActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySignupBinding

    private lateinit var name : String
    private lateinit var email : String
    private lateinit var password : String
    private lateinit var confirmPassword : String

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText

    private lateinit var auth : FirebaseAuth
    private lateinit var database : FirebaseDatabase

    private lateinit var gso : GoogleSignInOptions
    private lateinit var googleSignInClient : GoogleSignInClient

    private val googleSignUpRequestCode = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){
        setLayout()
        initializeUiElements()
        setListeners()
    }
    private fun setLayout(){
        val source=binding.signupActivtyContinueText.text.toString()
        val spannableStringBuilder = SpannableStringBuilder(source)
        spannableStringBuilder.setSpan(
            AbsoluteSizeSpan(40),
            0,
            3,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.signupActivtyContinueText.text=spannableStringBuilder
    }
    private fun initializeUiElements(){
        nameEditText = binding.signupActivityName
        emailEditText = binding.signupActivityEmail
        passwordEditText = binding.signupActivityPasswordEditText
        confirmPasswordEditText = binding.signupActivityConfirmPasswordEditText

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this,gso)
    }
    private fun setListeners(){
        binding.signupActivityAlreayHaveAccount.setOnClickListener{
            val intent= Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.signupActivitySignupButton.setOnClickListener{
            getUserData()
            if(validateUserData()){

                name.trim()
                email.trim()
                password.trim()

                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {task ->  // User SignUp
                    if(task.isSuccessful){
                        storeUserData()

                        val intent = Intent(this , MainActivity::class.java)
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
                        Log.e("Hello",error)
                        Toast.makeText(applicationContext , "Failed to create an Account. Try Again!!", Toast.LENGTH_LONG).show()
                    }
                }
            }
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
        binding.signupActivityGoogleButton.setOnClickListener{
            val signUpIntent = googleSignInClient.signInIntent
            startActivityForResult(signUpIntent,googleSignUpRequestCode)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == googleSignUpRequestCode){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
            }
            catch (e : ApiException){
                Toast.makeText(this,"Can't create Account. Try Again",Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun firebaseAuthWithGoogle(idToken : String){
        val credential = GoogleAuthProvider.getCredential(idToken,null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this){task ->   //User SignUp
                if(task.isSuccessful){
                    storeUserData()

                    val intent = Intent(this@SignupActivity , MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(applicationContext , "Failed to create an Account. Try Again!!", Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun storeUserData(){
        val user = auth.currentUser

        val userId = user?.uid
        val userName = user?.displayName
        val userEmail = user?.email
        val userPhoneNumber = user?.phoneNumber

        val userModel = UserModel(userName,userEmail,userPhoneNumber)

        val userReference = database.reference.child("food ordering app").child("users").child(userId.toString())

        userReference.child("user data").setValue(userModel)
    }
    private fun getUserData(){
        name = nameEditText.text.toString()
        email = emailEditText.text.toString()
        password = passwordEditText.text.toString()
        confirmPassword = confirmPasswordEditText.text.toString()
    }
    private fun validateUserData() : Boolean{

        // Constrains on Name
        if (name.isEmpty()){
            nameEditText.error = "Name can't be empty"
            nameEditText.requestFocus()
            return false
        }
        if (name.isBlank()){
            nameEditText.error = "Invalid Name"
            nameEditText.setText("")
            nameEditText.requestFocus()
            return false
        }

        // Constrains of Email
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
            passwordEditText.error = "Password can't be all blank"
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