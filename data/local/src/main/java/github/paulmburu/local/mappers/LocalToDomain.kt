package github.paulmburu.local.mappers

import github.paulmburu.domain.models.*
import github.paulmburu.local.models.LocationWeatherEntity
import github.paulmburu.local.models.WeatherForecastEntity

fun LocationWeatherEntity.toDomain(): CurrentLocationWeather {
    return CurrentLocationWeather(
        id = id,
        name = name,
        coord = Coordinates(lat = lat, lon =lon),
        weatherInfo = listOf(WeatherInfo(main = weatherType, description = weatherTypeDescription)),
        mainInfo = MainInfo(temp = temp, temp_min = tempMin, temp_max = tempMax),
        timeStamp = timeStamp
    )
}

fun WeatherForecastEntity.toDomain(): WeatherForecast{
    return WeatherForecast(
        isoTimeStamp = isoTimeStamp,
        weatherInfo = arrayListOf(WeatherInfo(weatherType, weatherTypeDescription)),
        mainInfo = MainInfo(temp, tempMin, tempMax)
    )
}