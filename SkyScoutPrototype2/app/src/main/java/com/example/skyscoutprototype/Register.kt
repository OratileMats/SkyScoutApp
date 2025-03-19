package com.example.skyscoutprototype

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.*
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


class Register : AppCompatActivity() {


    lateinit var etEmail: EditText
    lateinit var etConfPass: EditText
    private lateinit var etPass: EditText
    private lateinit var regBtn: Button
    lateinit var logPage: TextView

    // create Firebase authentication object
    private lateinit var auth: FirebaseAuth

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        etEmail = findViewById(R.id.cEmail)
        etConfPass = findViewById(R.id.cnfPass)
        etPass = findViewById(R.id.entPass)
        regBtn = findViewById(R.id.regBtn)
        logPage = findViewById(R.id.logPage)

        // Initialising auth object
        auth = Firebase.auth


            regBtn.setOnClickListener {
                val intent = Intent(this@Register, MainActivity::class.java)
                startActivity(intent)
                MainActivity()

                // switching from signUp Activity to Login Activity
                logPage.setOnClickListener {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
            }
        }
    }
    private fun MainActivity() {
        val email = etEmail.text.toString()
        val pass = etPass.text.toString()
        val confirmPassword = etConfPass.text.toString()

        // check pass
        if (email.isBlank() || pass.isBlank() || confirmPassword.isBlank()) {
            Toast.makeText(this, "Email and Password can't be blank", Toast.LENGTH_SHORT).show()
            return
        }

        if (pass != confirmPassword) {
            Toast.makeText(this, "Password and Confirm Password do not match", Toast.LENGTH_SHORT).show()
            return
        }

        // If all credentials are correct, create the user
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                val userId = auth.currentUser?.uid

                if (userId != null) {
                    // Create a User object
                    val user = User(userId, email)

                    // Get reference to the Firebase Realtime Database
                    val database = FirebaseDatabase.getInstance()
                    val userRef = database.getReference("Users")

                    // Save the user object to the database
                    userRef.child(userId).setValue(user).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "User information saved successfully", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this, "Failed to save user information", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Sign Up Failed!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}



