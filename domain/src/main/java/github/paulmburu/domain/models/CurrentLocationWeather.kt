package github.paulmburu.domain.models

data class CurrentLocationWeather(
    val id: String,
    val coord: Coordinates,
    val weatherInfo: List<WeatherInfo>,
    val mainInfo: MainInfo,
    val timeForecast: String?

)