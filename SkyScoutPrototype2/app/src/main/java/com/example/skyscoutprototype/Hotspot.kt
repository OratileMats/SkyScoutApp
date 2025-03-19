package com.example.skyscoutprototype

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.skyscoutprototype.R.id.homeButton1
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlin.random.Random

class Hotspot : AppCompatActivity(), OnMapReadyCallback {

    private var mGoogleMap: GoogleMap? = null
    private lateinit var homeButton: Button



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotspot)

        homeButton = findViewById(R.id.homeButton1)


        homeButton.setOnClickListener {
            val intent = Intent(this@Hotspot, HomePage::class.java)
            startActivity(intent)
            MainActivity()
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap

        // Center point for Johannesburg
        val johannesburgCenter = LatLng(-26.2041, 28.0473)

        // Pinpoint locations within a 5km radius
        val locations = generateLocationsWithinRadius(johannesburgCenter, 5000, 10)
        addMarkersToMap(locations)

        // Move camera to the center of Johannesburg
        mGoogleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(johannesburgCenter, 13f))
    }

    private fun generateLocationsWithinRadius(
        center: LatLng,
        radius: Int,
        numPoints: Int
    ): List<LatLng> {
        val locations = mutableListOf<LatLng>()

        for (i in 1..numPoints) {
            val randomPoint = generateRandomLocation(center, radius)
            locations.add(randomPoint)
        }

        return locations
    }

    private fun generateRandomLocation(center: LatLng, radius: Int): LatLng {
        val radiusInDegrees = radius / 111000.0 // Convert meters to latitude/longitude degrees

        val u = Random.nextDouble()
        val v = Random.nextDouble()
        val w = radiusInDegrees * Math.sqrt(u)
        val t = 2 * Math.PI * v
        val latOffset = w * Math.cos(t)
        val lngOffset = w * Math.sin(t) / Math.cos(Math.toRadians(center.latitude))

        val lat = center.latitude + latOffset
        val lng = center.longitude + lngOffset

        return LatLng(lat, lng)
    }

    private fun addMarkersToMap(locations: List<LatLng>) {
        for (location in locations) {
            mGoogleMap?.addMarker(
                MarkerOptions()
                    .position(location)
                    .title("Observation Point")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
            )
        }
    }
}