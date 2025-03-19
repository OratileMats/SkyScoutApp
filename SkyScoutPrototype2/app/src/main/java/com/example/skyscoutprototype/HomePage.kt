package com.example.skyscoutprototype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

private lateinit var obsBtn: Button
private lateinit var youBtn: Button
private lateinit var hotBtn: Button
private lateinit var routeBtn: Button
private lateinit var locBtn: Button
private lateinit var setBtn: Button


class HomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        obsBtn = findViewById(R.id.obsBtn)
        youBtn = findViewById(R.id.youBtn)
        hotBtn = findViewById(R.id.hotBtn)
        routeBtn = findViewById(R.id.routeBtn)
        locBtn = findViewById(R.id.locBtn)
        setBtn = findViewById(R.id.setBtn)

        obsBtn.setOnClickListener {
            val intent = Intent(this@HomePage, AddObservation::class.java)
            startActivity(intent)
            HomePage()
        }
        youBtn.setOnClickListener {
            val intent = Intent(this@HomePage, YourObservation::class.java)
            startActivity(intent)
            HomePage()
        }
        hotBtn.setOnClickListener {
            val intent = Intent(this@HomePage, Hotspot::class.java)
            startActivity(intent)
            HomePage()
        }
        routeBtn.setOnClickListener {
            val intent = Intent(this@HomePage, RouteCalculatorActivity::class.java)
            startActivity(intent)
            HomePage()
        }
        locBtn.setOnClickListener {
            val intent = Intent(this@HomePage, MapsActivity::class.java)
            startActivity(intent)
            HomePage()
        }
        setBtn.setOnClickListener {
            val intent = Intent(this@HomePage, Settings::class.java)
            startActivity(intent)
            HomePage()
        }
    }
}