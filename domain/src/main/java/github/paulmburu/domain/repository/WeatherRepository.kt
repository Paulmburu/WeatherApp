package github.paulmburu.domain.repository

import github.paulmburu.common.Resource
import github.paulmburu.domain.models.CurrentLocationWeather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun fetchCurrentWeather(lat: Double, long: Double): Flow<Resource<CurrentLocationWeather>>
    fun fetchWeatherForecast(lat: Double, long: Double): Flow<Resource<List<CurrentLocationWeather>>>

    suspend fun insertCurrentWeather(currentWeather: List<CurrentLocationWeather>)
    suspend fun insertWeatherForecast(weatherForecast: List<CurrentLocationWeather>)
    suspend fun insertLocationToFavourites(weatherForecast: List<CurrentLocationWeather>)

    fun getCurrentWeather(lat: Double, long: Double): Flow<Resource<CurrentLocationWeather>>
    fun getWeatherForecast(lat: Double, long: Double): Flow<Resource<List<CurrentLocationWeather>>>

}
