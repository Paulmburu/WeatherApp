package github.paulmburu.network.models

import com.google.gson.annotations.SerializedName

data class WeatherForecastDto(
    @SerializedName("dt_txt")
    var timeStamp: String,
    val weather: List<WeatherInfoDto>,
    val main: MainInfoDto,
)