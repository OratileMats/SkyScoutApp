package com.example.skyscoutprototype

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
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
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import org.json.JSONObject
import java.net.URL
import kotlin.concurrent.thread

class RouteCalculatorActivity : AppCompatActivity(), OnMapReadyCallback {

    private var mGoogleMap: GoogleMap? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val hotspotLocations = listOf(
        LatLng(-26.2041, 28.0473), // Add more hotspot locations here
        // Example: LatLng(-26.2025, 28.0455), LatLng(-26.2053, 28.0507)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route_calculator)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mGoogleMap?.isMyLocationEnabled = true
            getCurrentLocationAndCalculateNearestRoute()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocationAndCalculateNearestRoute() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val userLocation = LatLng(location.latitude, location.longitude)
                mGoogleMap?.addMarker(
                    MarkerOptions().position(userLocation).title("Your Location")
                )
                mGoogleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 13f))

                val nearestHotspot = findNearestHotspot(userLocation)
                if (nearestHotspot != null) {
                    mGoogleMap?.addMarker(
                        MarkerOptions().position(nearestHotspot).title("Nearest Hotspot")
                    )
                    drawRoute(userLocation, nearestHotspot)
                } else {
                    Toast.makeText(this, "No hotspots available", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Unable to get current location", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun findNearestHotspot(userLocation: LatLng): LatLng? {
        return hotspotLocations.minByOrNull { hotspot ->
            val distance = FloatArray(1)
            android.location.Location.distanceBetween(
                userLocation.latitude, userLocation.longitude,
                hotspot.latitude, hotspot.longitude,
                distance
            )
            distance[0]
        }
    }

    private fun drawRoute(origin: LatLng, destination: LatLng) {
        val url = getDirectionsUrl(origin, destination)
        thread {
            try {
                val response = URL(url).readText()
                val jsonObject = JSONObject(response)
                val routes = jsonObject.getJSONArray("routes")
                if (routes.length() > 0) {
                    val overviewPolyline = routes.getJSONObject(0)
                        .getJSONObject("overview_polyline")
                        .getString("points")
                    val decodedPath = PolyUtil.decode(overviewPolyline)

                    runOnUiThread {
                        mGoogleMap?.addPolyline(
                            PolylineOptions().addAll(decodedPath).width(10f).color(ContextCompat.getColor(this, com.google.android.libraries.places.R.color.quantum_teal))
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this, "Error calculating route", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getDirectionsUrl(origin: LatLng, destination: LatLng): String {
        val apiKey = "AIzaSyCpG-ErQhfq5oJFIawJbDB2Q7RQgQrCz8Q"
        return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin.latitude},${origin.longitude}&destination=${destination.latitude},${destination.longitude}&key=$apiKey"
    }
}
