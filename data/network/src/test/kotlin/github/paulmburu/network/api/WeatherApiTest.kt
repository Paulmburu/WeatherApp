package github.paulmburu.network.api

import com.google.common.truth.Truth
import com.google.gson.Gson
import github.paulmburu.network.base.BaseTest
import github.paulmburu.network.models.CurrentLocationWeatherDto
import github.paulmburu.network.models.ForecastResponse
import kotlinx.coroutines.runBlocking
import org.junit.Test

class WeatherApiTest : BaseTest() {

    @Test
    fun `When fetchCurrentWeather is called with all status, the correct result should be parsed`() =
        runBlocking {
            val response = weatherApi.fetchCurrentWeather("37.8","122.08", "someAppKey")
            if (response.isSuccessful) {
                Truth.assertThat(response.body()!!).isInstanceOf(CurrentLocationWeatherDto::class.java)
            }
        }

    @Test
    fun `When fetchWeatherForecast is called with all status, the correct result should be parsed`() =
        runBlocking {
            val response = weatherApi.fetchWeatherForecast("37.8","122.08", "someAppKey")
            if (response.isSuccessful) {
                Truth.assertThat(response.body()!!).isInstanceOf(ForecastResponse::class.java)
            }
        }

}