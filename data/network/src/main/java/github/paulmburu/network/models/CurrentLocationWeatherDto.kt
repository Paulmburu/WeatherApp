package github.paulmburu.network.models

data class CurrentLocationWeatherDto(
    var id: String,
    val coord: CoordinatesDto,
    val weatherInfo: List<WeatherInfoDto>,
    val mainInfo: MainInfoDto,
    val timeForecast: String?

)
