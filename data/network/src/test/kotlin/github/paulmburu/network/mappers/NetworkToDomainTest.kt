package github.paulmburu.network.mappers

import com.google.common.truth.Truth
import github.paulmburu.domain.models.*
import github.paulmburu.network.models.*
import org.junit.Test

class NetworkToDomainTest {

    @Test
    fun `When CoordinatesDto toDomain is called, the Domain representation should be returned`() {
        val coordinatesDto = CoordinatesDto(lat = 37.8, lon = 24.6)
        val coordinates = Coordinates(lat = 37.8, lon = 24.6)

        Truth.assertThat(coordinates).isEqualTo(coordinatesDto.toDomain())
    }

    @Test
    fun `When MainInfoDto toDomain is called, the Domain representation should be returned`() {
        val mainInfoDto = MainInfoDto(temp = 234.0, temp_min = 232.2, temp_max = 235.8)
        val mainInfo = MainInfo(temp = 234.0, temp_min = 232.2, temp_max = 235.8)

        Truth.assertThat(mainInfo).isEqualTo(mainInfoDto.toDomain())
    }

    @Test
    fun `When WeatherInfoDto toDomain is called, the Domain representation should be returned`() {
        val weatherInfoDto = WeatherInfoDto(main = "Clouds", description = "Flat Clouds")
        val weatherInfo = WeatherInfo(main = "Clouds", description = "Flat Clouds")

        Truth.assertThat(weatherInfo).isEqualTo(weatherInfoDto.toDomain())
    }

    @Test
    fun `When CurrentLocationWeatherDto toDomain is called, the Domain representation should be returned`() {
        val currentLocationWeatherDto = CurrentLocationWeatherDto(
            id = "12424",
            name = "Accra",
            timeStamp = "12424",
            coord = CoordinatesDto(lat = 37.8, lon = 24.6),
            weather = arrayListOf(WeatherInfoDto(main = "Clouds", description = "Flat Clouds")),
            main = MainInfoDto(temp = 234.0, temp_min = 232.2, temp_max = 235.8),
        )

        val currentLocationWeather = CurrentLocationWeather(
            id = "12424",
            name = "Accra",
            timeStamp = "12424",
            coord = Coordinates(lat = 37.8, lon = 24.6),
            weatherInfo = arrayListOf(WeatherInfo(main = "Clouds", description = "Flat Clouds")),
            mainInfo = MainInfo(temp = 234.0, temp_min = 232.2, temp_max = 235.8),
        )

        Truth.assertThat(currentLocationWeather).isEqualTo(currentLocationWeatherDto.toDomain())
    }

    @Test
    fun `When WeatherForecastDto toDomain is called, the Domain representation should be returned`() {
        val weatherForecastDto = WeatherForecastDto(
            timeStamp = "2022-03-21 18:00:00",
            weather = arrayListOf(WeatherInfoDto(main = "Clouds", description = "Flat Clouds")),
            main = MainInfoDto(temp = 234.0, temp_min = 232.2, temp_max = 235.8),
        )

        val weatherForecast = WeatherForecast(
            isoTimeStamp = "2022-03-21 18:00:00",
            weatherInfo = arrayListOf(WeatherInfo(main = "Clouds", description = "Flat Clouds")),
            mainInfo = MainInfo(temp = 234.0, temp_min = 232.2, temp_max = 235.8),
        )

        Truth.assertThat(weatherForecast).isEqualTo(weatherForecastDto.toDomain())
    }
}