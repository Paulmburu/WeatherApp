package github.paulmburu.domain.fakes

import github.paulmburu.domain.models.*

object Data {
    val currentLocationWeather = CurrentLocationWeather(
        id = "1234",
        coord = Coordinates(0.0, 0.0),
        name = "hawai",
        weatherInfo = arrayListOf(
        WeatherInfo("Clouds","Flat clouds")
    ), mainInfo = MainInfo(230.89, 232.76, 235.05),
        timeStamp = "1647822735,"
    )

    object WeatherForecastData {
        val response = arrayListOf(
            WeatherForecast(
                isoTimeStamp = "2022-03-21 03:00:00",
                weatherInfo = arrayListOf(WeatherInfo("Clouds","Flat clouds")),
                mainInfo = MainInfo(230.89, 232.76, 235.05)
            ),
            WeatherForecast(
                isoTimeStamp = "2022-03-21 03:00:00",
                weatherInfo = arrayListOf(WeatherInfo("Rain","Flat clouds")),
                mainInfo = MainInfo(230.89, 232.76, 235.05)
            ),
            WeatherForecast(
                isoTimeStamp = "2022-03-21 03:00:00",
                weatherInfo = arrayListOf(WeatherInfo("Clear","Flat clouds")),
                mainInfo = MainInfo(230.89, 232.76, 235.05)
            ),
            WeatherForecast(
                isoTimeStamp = "2022-03-21 03:00:00",
                weatherInfo = arrayListOf(WeatherInfo("Clouds","Flat clouds")),
                mainInfo = MainInfo(230.89, 232.76, 235.05)
            ),
        )
    }
}