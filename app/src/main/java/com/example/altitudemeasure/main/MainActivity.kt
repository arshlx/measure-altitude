package com.example.altitudemeasure.main

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.altitudemeasure.R
import com.example.altitudemeasure.databinding.ActivityMainBinding
import com.example.altitudemeasure.objects.TaskStatus
import com.google.android.gms.location.*


class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var sensorManager: SensorManager
    private lateinit var pressureSensor: Sensor
    private lateinit var sensorEventListener: SensorEventListener
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var viewModel: GeoViewModel
    private val locationRequest: LocationRequest = LocationRequest.Builder(7000)
        .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
        .setIntervalMillis(10000L)
        .setMinUpdateIntervalMillis(5000L)
//        .setMaxUpdates(1)
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[GeoViewModel::class.java]
        viewModel.altitudeStatus.observe(this, tempObserver)
        binding.apply {
            altitudeTxt.text =
                getString(R.string.altitude_str, TaskStatus.LOADING)
            latitudeTxt.text =
                getString(R.string.latitude_str, TaskStatus.LOADING)
            longitudeTxt.text =
                getString(R.string.longitude_str, TaskStatus.LOADING)
        }
        initPressureSensor()
        initLocationServices()
        checkLocationPermission()
    }

    private fun initPressureSensor() {
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)

        sensorEventListener = object : SensorEventListener {
            override fun onSensorChanged(sensorEvent: SensorEvent) {
                val values = sensorEvent.values
                binding.pressureTxt.text =
                    String.format("Atmospheric Pressure: %.3f mbar", values[0])
            }

            override fun onAccuracyChanged(sensor: Sensor?, i: Int) {}
        }
    }

    private fun initLocationServices() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                for (location in locationResult.locations) {
                    binding.apply {
                        altitudeTxt.text =
                            getString(R.string.altitude_str, location.altitude.toString())
                        latitudeTxt.text =
                            getString(R.string.latitude_str, location.latitude.toString())
                        longitudeTxt.text =
                            getString(R.string.longitude_str, location.longitude.toString())
                    }
                    viewModel.apply {
                        coordinates = if (tempInfo != null && location != null) getString(
                            R.string.coordinates,
                            location.latitude.toString(),
                            location.longitude.toString()
                        )
                        else "sydney"
                    }

                    viewModel.getTemperature()
                }
            }

        }
    }

    private val tempObserver = Observer<String> {
        binding.temperatureTxt.text = when (it) {
            TaskStatus.LOADING ->
                TaskStatus.LOADING

            TaskStatus.SUCCESS ->
                getString(
                    R.string.temperatureTemplate,
                    viewModel.tempInfo!!.temperature.toString()
                )

            TaskStatus.FAILURE ->
                TaskStatus.FAILURE
            else -> TaskStatus.EMPTY
        }
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(
            sensorEventListener, pressureSensor, SensorManager.SENSOR_DELAY_UI
        )
//        if (requestingLocationUpdates) startLocationUpdates()
    }

    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(ACCESS_FINE_LOCATION), 1)
            Toast.makeText(this, R.string.location_permission_denied, Toast.LENGTH_LONG).show()
            return
        }
        getLocationUpdates()
    }

    @SuppressLint("MissingPermission")
    private fun getLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(sensorEventListener)
    }
}