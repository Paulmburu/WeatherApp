package github.paulmburu.repository.repository

import github.paulmburu.common.Resource
import github.paulmburu.domain.models.CurrentLocationWeather
import github.paulmburu.domain.repository.WeatherRepository
import github.paulmburu.local.dao.WeatherDao
import github.paulmburu.local.mappers.toDomain
import github.paulmburu.local.mappers.toLocal
import github.paulmburu.network.api.WeatherApi
import github.paulmburu.network.mappers.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val weatherDao: WeatherDao
) : WeatherRepository {

    override fun fetchCurrentWeather(
        lat: Double,
        lon: Double
    ): Flow<Resource<CurrentLocationWeather>> = flow {
        try {

            val result = weatherApi.fetchCurrentWeather(lat.toString(),lon.toString())
            when {
                result.isSuccessful -> emit(
                    Resource.Success(result.body()!!.toDomain())
                )
                else -> emit(Resource.Error(message = result.message()))
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = e.localizedMessage))
            Timber.e(e)
        } catch (e: Exception) {
            // emit(Resource.Error(message = e.localizedMessage))
            Timber.e(e)
        }
    }

    override fun fetchWeatherForecast(
        lat: Double,
        lon: Double
    ): Flow<Resource<List<CurrentLocationWeather>>> = flow {
        try {

            val result = weatherApi.fetchWeatherForecast(lat.toString(),lon.toString())
            when {
                result.isSuccessful -> emit(
                    Resource.Success(result.body()!!.currentLocationWeatherForecast.map { it.toDomain() })
                )
                else -> emit(Resource.Error(message = result.message()))
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = e.localizedMessage))
            Timber.e(e)
        } catch (e: Exception) {
            // emit(Resource.Error(message = e.localizedMessage))
            Timber.e(e)
        }
    }

    override suspend fun insertCurrentWeather(currentWeather: List<CurrentLocationWeather>) {
        weatherDao.insertCurrentWeather(currentWeather.map { it ->
            it.toLocal().also { it.id = "current_weather" }
        })
    }

    override suspend fun insertWeatherForecast(weatherForecast: List<CurrentLocationWeather>) {
        weatherDao.insertWeatherForecast(weatherForecast.map { it ->
            it.toLocal()
        })
    }

    override suspend fun insertLocationToFavourites(weatherForecast: List<CurrentLocationWeather>) {
        weatherDao.insertLocationToFavourites(weatherForecast.map { it ->
            it.toLocal().also { it.isFavourite = true }
        })
    }

    override fun getCurrentWeather(
        lat: Double,
        lon: Double
    ): Flow<Resource<CurrentLocationWeather>> = flow {
        try {
            val result = weatherDao.findCurrentWeather()
            val data = result.map { it[0].toDomain() }.toList()
            emit(Resource.Success(
                data[0]
            ))

        } catch (e: IOException) {
            emit(Resource.Error(message = e.localizedMessage))
            Timber.e(e)
        } catch (e: Exception) {
            //emit(Resource.Error(message = e.localizedMessage))
            Timber.e(e)
        }
    }

    override fun getWeatherForecast(
        lat: Double,
        lon: Double
    ): Flow<Resource<List<CurrentLocationWeather>>> = flow {
        try {
            val result = weatherDao.weatherForecast()
            val data = result.map { it[0].toDomain() }.toList()
            emit(Resource.Success(
                data
            ))

        } catch (e: IOException) {
            emit(Resource.Error(message = e.localizedMessage))
            Timber.e(e)
        } catch (e: Exception) {
            //emit(Resource.Error(message = e.localizedMessage))
            Timber.e(e)
        }
    }

}