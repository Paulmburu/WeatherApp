package github.paulmburu.weatherapp.ui.mainActivity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import github.paulmburu.domain.models.CurrentLocationWeather
import github.paulmburu.domain.models.WeatherForecast
import github.paulmburu.weatherapp.R
import github.paulmburu.weatherapp.databinding.ActivityMainBinding
import github.paulmburu.weatherapp.models.WeatherForecastPresentation
import github.paulmburu.weatherapp.ui.adapters.WeatherForecastRecyclerAdapter
import github.paulmburu.weatherapp.util.convertKelvinToCelsius

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val LOCATION_PERMISSION_REQUEST_CODE = 111
    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: WeatherForecastRecyclerAdapter
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var currentLocation: Location? = null
    lateinit var locationManager: LocationManager
    private lateinit var afterPermissionFunc: (Map<String, Int>) -> Unit


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        setObservers()
        setDisplayRecyclerView()
    }


    @SuppressLint("MissingPermission")
    private fun initLocationManager() {
        var locationByGps: Location? = null
        var locationByNetwork: Location? = null

        val hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        val gpsLocationListener: LocationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                locationByGps = location
            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }

        val networkLocationListener: LocationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                locationByNetwork = location
            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }

        if (hasGps) {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000,
                0F,
                gpsLocationListener
            )
        }

        if (hasNetwork) {
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                5000,
                0F,
                networkLocationListener
            )
        }


        val lastKnownLocationByGps =
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        lastKnownLocationByGps?.let {
            locationByGps = lastKnownLocationByGps
        }
        val lastKnownLocationByNetwork =
            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        lastKnownLocationByNetwork?.let {
            locationByNetwork = lastKnownLocationByNetwork
        }
        if (locationByGps != null && locationByNetwork != null) {
            if (locationByGps!!.accuracy > locationByNetwork!!.accuracy) {
                currentLocation = locationByGps
                viewModel.setCoordinates(currentLocation!!.latitude, currentLocation!!.longitude)
            } else {
                currentLocation = locationByNetwork
                viewModel.setCoordinates(currentLocation!!.latitude, currentLocation!!.longitude)
            }
        } else {
            Toast.makeText(
                this,
                "Kindly check and turn on your GPS Location then try again ",
                Toast.LENGTH_LONG
            ).show()

            displayEmptyState()
        }
    }

    private fun setDisplayRecyclerView() {
        adapter = WeatherForecastRecyclerAdapter()
        binding.forecastRecyclerview.adapter = adapter
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setObservers() {
        viewModel.connectivityStatus.observe(this){
            when(it){
                true -> {
                    checkAndRequestPermission(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    ) { permissionResults ->
                        if (permissionResults.none { it.value != PackageManager.PERMISSION_GRANTED }) {
                            initLocationManager()
                        } else {
                            displayEmptyState()
                            Toast.makeText(
                                this,
                                "Permission Denied",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                }
                false -> {
                    viewModel.loadLocalData()
                }
            }
        }

        viewModel.currentUserCoordinates.observe(this) {
            viewModel.loadNetworkData(it)
        }
        viewModel.fetchWeatherResult.observe(this) { uiState ->
            when (uiState) {
                is MainViewModel.FetchCurrentWeatherUiState.Loading -> {
                    displayLoadingState()
                }

                is MainViewModel.FetchCurrentWeatherUiState.Failure -> {
                    displayEmptyState()
                }

                is MainViewModel.FetchCurrentWeatherUiState.Success -> {
                    displaySuccessState(uiState.currentLocationWeather)
                }

                is MainViewModel.FetchCurrentWeatherUiState.Empty -> {
                    displayEmptyState()
                }

                is MainViewModel.FetchCurrentWeatherUiState.Cleared -> {
                    displayEmptyState()
                }
            }
        }

        viewModel.weatherForecastResult.observe(this) { uiState ->
            when (uiState) {
                is MainViewModel.FetchWeatherForecastUiState.Loading -> {
                    displayLoadingState()
                }

                is MainViewModel.FetchWeatherForecastUiState.Failure -> {
                    displayEmptyState()
                }

                is MainViewModel.FetchWeatherForecastUiState.Success -> {
                    val data = uiState.weatherForecast.distinctBy { it.dayOfTheWeek }
                    displayWeatherForecastState(data)
                }

                is MainViewModel.FetchWeatherForecastUiState.Empty -> {
                    displayEmptyState()
                }

                is MainViewModel.FetchWeatherForecastUiState.Cleared -> {
                    displayEmptyState()
                }
            }
        }

    }

    private fun displayLoadingState() {
        with(binding) {
            progressBar.isVisible = true
            forecastViews.isVisible = false
            noDataImageView.isVisible = false
        }
    }

    private fun displaySuccessState(currentLocationWeather: CurrentLocationWeather) {
        updateWeatherBackground(currentLocationWeather.weatherInfo[0].main)
        with(binding) {
            progressBar.isVisible = false
            forecastViews.isVisible = true
            noDataImageView.isVisible = false
            tempTextView.text = currentLocationWeather.mainInfo.temp.convertKelvinToCelsius()
            tempMinTextView.text = currentLocationWeather.mainInfo.temp_min.convertKelvinToCelsius()
            currentTempTextView.text = currentLocationWeather.mainInfo.temp.convertKelvinToCelsius()
            tempMaxTextView.text = currentLocationWeather.mainInfo.temp_max.convertKelvinToCelsius()
            weatherTypeTextView.text = currentLocationWeather.weatherInfo[0].main

        }
    }

    private fun displayWeatherForecastState(weatherForecast: List<WeatherForecastPresentation>) {
        adapter.submitList(weatherForecast)
        with(binding) {
            progressBar.isVisible = false
            forecastViews.isVisible = true
            noDataImageView.isVisible = false
        }
    }

    private fun updateWeatherBackground(weatherType: String) {
        with(binding) {
            when {
                weatherType.equals("Clouds") -> {
                    val cloudyColor = ContextCompat.getColor(applicationContext, R.color.cloudy)
                    window.statusBarColor = cloudyColor
                    forecastView.setBackgroundColor(cloudyColor)
                    weatherBackground.setImageDrawable(
                        ContextCompat.getDrawable(applicationContext, R.drawable.forest_cloudy)
                    )
                }
                weatherType.equals("Clear") -> {
                    val sunnyColor = ContextCompat.getColor(applicationContext, R.color.sunny)
                    window.statusBarColor = sunnyColor
                    forecastView.setBackgroundColor(sunnyColor)
                    weatherBackground.setImageDrawable(
                        ContextCompat.getDrawable(applicationContext, R.drawable.forest_sunny)
                    )
                }
                weatherType.equals("Rain") -> {
                    val rainlyColor = ContextCompat.getColor(applicationContext, R.color.rainy)
                    window.statusBarColor = rainlyColor
                    forecastView.setBackgroundColor(rainlyColor)
                    weatherBackground.setImageDrawable(
                        ContextCompat.getDrawable(applicationContext, R.drawable.forest_rainy)
                    )
                }
            }


        }
    }

    private fun displayEmptyState() {
        adapter.submitList(null)

        with(binding) {
            noDataImageView.isVisible = true
            progressBar.isVisible = false
            forecastViews.isVisible = false
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun checkAndRequestPermission(permissions: Array<String>, param: (Map<String, Int>) -> Unit) {
        requestPermissions(permissions, LOCATION_PERMISSION_REQUEST_CODE)
        afterPermissionFunc = param
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val permissionResults = mutableMapOf<String, Int>()
        permissions.forEachIndexed { i, permission ->
            permissionResults[permission] = grantResults[i]
        }
        afterPermissionFunc(permissionResults)
    }
}
