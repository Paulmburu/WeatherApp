package github.paulmburu.domain.fakes

import github.paulmburu.common.Resource
import github.paulmburu.domain.models.CurrentLocationWeather
import github.paulmburu.domain.models.WeatherForecast
import github.paulmburu.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import java.util.LinkedHashMap

class WeatherRepositoryFake : WeatherRepository {
    private val currentLocationWeatherDatabase  = LinkedHashMap<String, CurrentLocationWeather>()
    private val weatherForecastDatabase  = LinkedHashMap<String, WeatherForecast>()

    override fun fetchCurrentWeather(
        lat: Double,
        lon: Double
    ): Flow<Resource<CurrentLocationWeather>> = flow {
        emit(Resource.Success(Data.currentLocationWeather))
    }

    override fun fetchWeatherForecast(
        lat: Double,
        lon: Double
    ): Flow<Resource<List<WeatherForecast>>> = flow {
        emit(Resource.Success(Data.WeatherForecastData.response))
    }

    override suspend fun insertCurrentWeather(currentWeather: List<CurrentLocationWeather>) {
        currentWeather.forEach {
            currentLocationWeatherDatabase[it.id] = it
        }
    }

    override suspend fun insertLocationToFavourites(weatherForecast: List<CurrentLocationWeather>) {
        weatherForecast.forEach {
            currentLocationWeatherDatabase[it.id] = it
        }
    }

    override suspend fun insertWeatherForecast(weatherForecast: List<WeatherForecast>) {
        weatherForecast.forEach {
            weatherForecastDatabase[it.isoTimeStamp] = it
        }
    }

    override fun getCurrentWeather(
        lat: Double,
        lon: Double
    ): Flow<Resource<CurrentLocationWeather>> {
        return flowOf(Resource.Success(Data.currentLocationWeather))
    }

    override fun getWeatherForecast(
        lat: Double,
        lon: Double
    ): Flow<Resource<List<WeatherForecast>>> {
        return flowOf(Resource.Success(listOf()))
    }
}