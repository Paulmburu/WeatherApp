package github.paulmburu.repository.repository

import github.paulmburu.common.Resource
import github.paulmburu.domain.models.CurrentLocationWeather
import github.paulmburu.domain.models.WeatherForecast
import github.paulmburu.domain.repository.WeatherRepository
import github.paulmburu.local.dao.WeatherDao
import github.paulmburu.local.mappers.toDomain
import github.paulmburu.local.mappers.toLocal
import github.paulmburu.local.models.WeatherForecastEntity
import github.paulmburu.network.api.WeatherApi
import github.paulmburu.network.mappers.toDomain
import github.paulmburu.repository.BuildConfig
import kotlinx.coroutines.flow.*
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

            val result = weatherApi.fetchCurrentWeather(
                lat.toString(),
                lon.toString(),
                BuildConfig.BEARER_TOKEN
            )
            when {
                result.isSuccessful -> {
                    weatherDao.insertCurrentWeather(
                        arrayListOf(
                            result.body()!!.toDomain().toLocal()
                        )
                    )
                    emit(
                        Resource.Success(result.body()!!.toDomain())
                    )
                }
                else -> emit(Resource.Error(message = result.message()))
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = e.localizedMessage))
            Timber.e(e)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    override fun fetchWeatherForecast(
        lat: Double,
        lon: Double
    ): Flow<Resource<List<WeatherForecast>>> = flow {
        try {

            val result = weatherApi.fetchWeatherForecast(
                lat.toString(),
                lon.toString(),
                BuildConfig.BEARER_TOKEN
            )
            when {
                result.isSuccessful -> {
                    weatherDao.insertWeatherForecast(result.body()!!.list.map {
                        it.toDomain().toLocal()
                    })
                    emit(
                        Resource.Success(result.body()!!.list.map { it.toDomain() })
                    )
                }
                else -> emit(Resource.Error(message = result.message()))
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = e.localizedMessage))
            Timber.e(e)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    override suspend fun insertCurrentWeather(currentWeather: List<CurrentLocationWeather>) {
        weatherDao.insertCurrentWeather(currentWeather.map { it ->
            it.apply { id =  "current_weather"}.toLocal()
        })
    }

    override suspend fun insertWeatherForecast(weatherForecast: List<WeatherForecast>) {
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
            weatherDao.findCurrentWeather().collect {
                val data = it.map { locationWeatherEntity -> locationWeatherEntity.toDomain() }
                emit(
                    Resource.Success(
                        data.last()
                    )
                )
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = e.localizedMessage))
            Timber.e(e)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    override fun getWeatherForecast(
        lat: Double,
        lon: Double
    ): Flow<Resource<List<WeatherForecast>>> = flow {
        try {
            weatherDao.weatherForecast().collect {
                val data = it.map { weatherForecastEntity -> weatherForecastEntity.toDomain() }
                emit(
                    Resource.Success(
                        data
                    )
                )
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = e.localizedMessage))
            Timber.e(e)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

}