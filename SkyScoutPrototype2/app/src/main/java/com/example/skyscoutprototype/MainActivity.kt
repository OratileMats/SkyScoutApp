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

class MainActivity : AppCompatActivity() {

    private lateinit var backToReg: TextView
    lateinit var userEmail: EditText
    private lateinit var userPass: EditText
    lateinit var logBtn: Button

    // Creating firebaseAuth object
    lateinit var auth: FirebaseAuth
//Change 1
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        backToReg = findViewById(R.id.backToReg)
        logBtn = findViewById(R.id.logBtn)
        userEmail = findViewById(R.id.etEmail)
        userPass = findViewById(R.id.etPass)

        auth = FirebaseAuth.getInstance()


        logBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, HomePage::class.java)
            startActivity(intent)
            HomePage()
        }
        backToReg.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
            finish()
        }
    }
    internal fun HomePage() {
        val email = userEmail.text.toString()
        val pass = userPass.text.toString()

        // calling signInWithEmailAndPassword(email, pass)
        // function using Firebase auth object
        // On successful response Display a Toast
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Toast.makeText(this, "Successfully LoggedIn", Toast.LENGTH_SHORT).show()
            } else
                Toast.makeText(this, "Log In failed ", Toast.LENGTH_SHORT).show()
        }
    }
}