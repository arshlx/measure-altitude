package com.example.altitudemeasure

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
import com.example.altitudemeasure.databinding.ActivityMainBinding
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
        .setIntervalMillis(7000L)
        .setMinUpdateIntervalMillis(7000L)
//        .setMaxUpdates(1)
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[GeoViewModel::class.java]
        viewModel.altitudeStatus.observe(this, tempObserver)
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
                binding.pressureTxt.text = String.format("%.3f mbar", values[0])
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
                    binding.altitudeTxt.text = location.altitude.toString()
                    viewModel.getTemperature()
                }
            }

        }
    }

    private val tempObserver = Observer<String> {
        when (it) {
            TaskStatus.LOADING -> {
//                binding.progressBar.visibility = View.VISIBLE
            }
            TaskStatus.SUCCESS -> {
                binding.temperatureTxt.text = getString(
                    R.string.temperatureTemplate,
                    viewModel.tempInfo!!.temperature.toString()
                )
            }
            TaskStatus.FAILURE -> {
                binding.temperatureTxt.text = TaskStatus.FAILURE
            }
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