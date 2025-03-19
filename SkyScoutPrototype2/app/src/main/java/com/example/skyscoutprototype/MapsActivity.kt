package com.example.skyscoutprototype

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private var mGoogleMap: GoogleMap? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val LOCATION_PERMISSION_REQUEST_CODE = 1000
    private lateinit var homeBtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        homeBtn = findViewById(R.id.Homebutton)


        homeBtn.setOnClickListener {
            val intent = Intent(this@MapsActivity, HomePage::class.java)
            startActivity(intent)
            MainActivity()
        }

        // Initialize FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap

        // Check for location permission and request if not granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            // Permissions already granted, show current location
            showCurrentLocation()
        }
    }

    private fun showCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val userLatLng = LatLng(location.latitude, location.longitude)
                    mGoogleMap?.addMarker(MarkerOptions().position(userLatLng).title("Your Current Location"))
                    mGoogleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 15f))
                } else {
                    // Default to a known location (Johannesburg Center) if the location is null
                    val johannesburgCenter = LatLng(-26.2041, 28.0473)
                    mGoogleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(johannesburgCenter, 13f))
                    mGoogleMap?.addMarker(MarkerOptions().position(johannesburgCenter).title("Johannesburg Center"))
                }
            }
        }
    }

    // Handle permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // Permission granted, show current location
                showCurrentLocation()
            } else {
                // Permission denied, show default location
                val johannesburgCenter = LatLng(-26.2041, 28.0473)
                mGoogleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(johannesburgCenter, 13f))
                mGoogleMap?.addMarker(MarkerOptions().position(johannesburgCenter).title("Johannesburg Center"))
            }
        }
    }
}
