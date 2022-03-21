package github.paulmburu.weatherapp.models


data class WeatherForecastPresentation(
    val isoTimeStamp: String,
    var dayOfTheWeek: String,
    val weatherType: String,
    val temperature: String,

)
