package github.paulmburu.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.google.common.truth.Truth
import github.paulmburu.local.database.WeatherDatabase
import github.paulmburu.local.mappers.toDomain
import github.paulmburu.local.models.LocationWeatherEntity
import github.paulmburu.local.models.WeatherForecastEntity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class WeatherDaoTest {

    private lateinit var database: WeatherDatabase
    private lateinit var weatherDao: WeatherDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WeatherDatabase::class.java
        ).build()

        weatherDao = database.weatherDao
    }

    @Test
    fun insertingWeatherForecast_shouldSave_the_correctData() = runBlocking {
        val weatherForecast = listOf(
            WeatherForecastEntity(
                isoTimeStamp = "2022-03-21 03:00:00",
                weatherType = "Clouds",
                weatherTypeDescription = "Flat Clouds",
                temp = 234.09,
                tempMin = 220.43,
                tempMax = 240.34
            )
        )

        weatherDao.insertWeatherForecast(weatherForecast)

        val response = weatherDao.weatherForecast().first()
        Truth.assertThat(response.size).isGreaterThan(0)
        Truth.assertThat(response.first().isoTimeStamp).isEqualTo(weatherForecast.first().isoTimeStamp)

    }

    @Test
    fun fetch_LocationWeather_Returns_the_correct_data() = runBlocking {
        val currentLocationWeather = listOf(
            LocationWeatherEntity(id = "54678456",name = "Nairobi", timeStamp = "54678456", lat = -1.28333, lon = 36.81667, weatherType = "Sunny","Hot sun", tempMax = 234.4, tempMin = 224.5, temp = 230.5),
            LocationWeatherEntity(id = "54678456",name = "Durban", timeStamp = "54678456", lat = -29.8579, lon = 31.0292, weatherType = "Sunny","Hot sun", tempMax = 234.4, tempMin = 224.5, temp = 230.5),
        )

        weatherDao.insertCurrentWeather(currentLocationWeather)

        weatherDao.findCurrentWeather().collect {
            val data = it.map { locationWeatherEntity -> locationWeatherEntity.toDomain() }
            Truth.assertThat(data.size).isEqualTo(1)
            Truth.assertThat(data.last().id).isEqualTo(currentLocationWeather.last().id)
        }
    }

    @After
    fun tearDown() {
        database.close()
    }
}