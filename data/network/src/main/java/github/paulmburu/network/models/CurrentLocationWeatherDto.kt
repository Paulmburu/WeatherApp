package github.paulmburu.network.models

import com.google.gson.annotations.SerializedName

data class CurrentLocationWeatherDto(
    var id: String,
    val coord: CoordinatesDto,
    val weather: List<WeatherInfoDto>,
    val main: MainInfoDto,
    val name: String,
    @SerializedName("dt")
    val timeStamp: String
)
