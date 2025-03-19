package com.example.skyscoutprototype


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class YourObservation : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var tvObservations: TextView
    private lateinit var homeButton: Button


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_your_observation)

        homeButton = findViewById(R.id.HomeBtn)

        homeButton.setOnClickListener {
            val intent = Intent(this@YourObservation, HomePage::class.java)
            startActivity(intent)
            MainActivity()
        }

// Initialize TextView to display observations
        tvObservations = findViewById(R.id.tvObservations)

        // Initialize Firebase database reference
        database = FirebaseDatabase.getInstance().getReference("Observations")

        // Fetch observations from the database
        fetchObservations()
    }

    private fun fetchObservations() {
        // Attach a listener to read the data from the "Observations" node
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val observationsList = StringBuilder()

                for (observationSnapshot in snapshot.children) {
                    val observation = observationSnapshot.getValue(Observation::class.java)
                    if (observation != null) {
                        observationsList.append("Species: ${observation.species}\n")
                        observationsList.append("Birds Observed: ${observation.birdsObs}\n")
                        observationsList.append("Location: ${observation.location}\n")
                        observationsList.append("Time: ${observation.time}\n\n")
                    }
                }

                // Display the data in the TextView
                tvObservations.text = observationsList.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle potential errors
                Toast.makeText(this@YourObservation, "Failed to load observations: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

