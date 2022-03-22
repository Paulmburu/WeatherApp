package github.paulmburu.weatherapp.ui.mainActivity

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import github.paulmburu.domain.models.CurrentLocationWeather
import github.paulmburu.weatherapp.R
import github.paulmburu.weatherapp.databinding.ActivityMainBinding
import github.paulmburu.weatherapp.models.WeatherForecastPresentation
import github.paulmburu.weatherapp.ui.adapters.WeatherForecastRecyclerAdapter
import github.paulmburu.weatherapp.util.convertKelvinToCelsius

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: WeatherForecastRecyclerAdapter
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getValue(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                getLastKnownPosition()
            }
            permissions.getValue(Manifest.permission.ACCESS_COARSE_LOCATION) -> {
                getLastKnownPosition()
            }
            else -> {
                Snackbar.make(
                    binding.root,
                    getString(R.string.permission_location_rationale),
                    Snackbar.LENGTH_SHORT
                ).show()
                displayEmptyState()
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        checkLocationPermission()

        setObservers()
        setDisplayRecyclerView()
    }


    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this, arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                ).toString()
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            locationPermissionRequest.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        } else {
            getLastKnownPosition()
        }
    }

    @SuppressLint("MissingPermission")
    fun getLastKnownPosition() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    viewModel.setCoordinates(
                        location.latitude,
                        location.longitude
                    )
                } else {
                    Toast.makeText(
                        this,
                        "No Location Found, kindly check your GPS ",
                        Toast.LENGTH_LONG
                    ).show()

                    displayEmptyState()
                }
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
            when (weatherType) {
                "Clouds" -> {
                    val cloudyColor = ContextCompat.getColor(applicationContext, R.color.cloudy)
                    window.statusBarColor = cloudyColor
                    forecastView.setBackgroundColor(cloudyColor)
                    weatherBackground.setImageDrawable(
                        ContextCompat.getDrawable(applicationContext, R.drawable.forest_cloudy)
                    )
                }
                "Clear" -> {
                    val sunnyColor = ContextCompat.getColor(applicationContext, R.color.sunny)
                    window.statusBarColor = sunnyColor
                    forecastView.setBackgroundColor(sunnyColor)
                    weatherBackground.setImageDrawable(
                        ContextCompat.getDrawable(applicationContext, R.drawable.forest_sunny)
                    )
                }
                "Rain" -> {
                    val rainyColor = ContextCompat.getColor(applicationContext, R.color.rainy)
                    window.statusBarColor = rainyColor
                    forecastView.setBackgroundColor(rainyColor)
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

}
