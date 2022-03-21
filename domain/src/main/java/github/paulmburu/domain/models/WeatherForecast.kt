package github.paulmburu.domain.models

data class WeatherForecast(
    var isoTimeStamp: String,
    val weatherInfo: List<WeatherInfo>,
    val mainInfo: MainInfo,
)