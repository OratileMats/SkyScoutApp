package com.example.skyscoutprototype

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView

class Settings : AppCompatActivity() {

    private lateinit var bckBtn: Button
    private lateinit var unitSwitch: Switch
    private lateinit var unitTextView: TextView
    private var isMiles = true // Start with miles as the default

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Initialize back button
        bckBtn = findViewById(R.id.bckBtn)
        bckBtn.setOnClickListener {
            val intent = Intent(this@Settings, HomePage::class.java)
            startActivity(intent)
            finish() // Use finish() to end the current activity properly
        }

        // Initialize the switch and text view for displaying the unit
        unitSwitch = findViewById(R.id.switch1)
        unitTextView = findViewById(R.id.textView14)

        // Set initial text for the unit
        unitTextView.text = if (isMiles) "Your current Metric System is: Miles" else "Your current Metric System is: Kilometers"

        // Set up the switch listener
        unitSwitch.setOnCheckedChangeListener { _, isChecked ->
            isMiles = isChecked
            unitTextView.text = if (isMiles) "Your current Metric System is: Kilometers" else "Your current Metric System is: Miles"
        }
    }
}



