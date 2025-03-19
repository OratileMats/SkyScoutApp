package com.example.skyscoutprototype


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase



class AddObservation : AppCompatActivity() {

    private lateinit var etSpecies: EditText
    private lateinit var etBirdsObs: EditText
    private lateinit var etLocation: EditText
    private lateinit var etTime: EditText
    private lateinit var saveButton: Button
    private lateinit var homeButton: Button


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_observation)

        // Initializing views
        etSpecies = findViewById(R.id.etSpecies)
        etBirdsObs = findViewById(R.id.etBirdObs)
        etLocation = findViewById(R.id.etLocation)
        etTime = findViewById(R.id.etTime)
        saveButton = findViewById(R.id.ObsSaveBtn)
        homeButton = findViewById(R.id.homeButton)

        homeButton.setOnClickListener {
            val intent = Intent(this@AddObservation, HomePage::class.java)
            startActivity(intent)
            HomePage()
        }
        // Set onClickListener for the save button
        saveButton.setOnClickListener {
            saveObservation()
        }
    }


    private fun saveObservation() {
        val species = etSpecies.text.toString().trim()
        val birdsObs = etBirdsObs.text.toString().toIntOrNull()
        val location = etLocation.text.toString().trim()
        val time = etTime.text.toString().trim()

        // Input validation
        if (species.isEmpty() || birdsObs == null || location.isEmpty() || time.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields with valid data", Toast.LENGTH_SHORT).show()
            return
        }

        // Create Observation object
        val observation = Observation(species, birdsObs, location, time)

        // Reference to the Firebase Realtime Database
        val database = FirebaseDatabase.getInstance()
        val observationsRef = database.getReference("Observations")

        // Generate a unique key for each observation
        val observationId = observationsRef.push().key

        if (observationId != null) {
            // Save the observation to the database
            observationsRef.child(observationId).setValue(observation)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Observation saved successfully", Toast.LENGTH_SHORT).show()
                        clearFields()
                    } else {
                        Toast.makeText(this, "Failed to save observation", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Error generating observation ID", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to clear input fields after saving
    private fun clearFields() {
        etSpecies.text.clear()
        etBirdsObs.text.clear()
        etLocation.text.clear()
        etTime.text.clear()
    }
}


