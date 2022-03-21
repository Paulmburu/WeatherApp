package github.paulmburu.local.mappers

import github.paulmburu.domain.models.CurrentLocationWeather
import github.paulmburu.domain.models.WeatherForecast
import github.paulmburu.local.models.LocationWeatherEntity
import github.paulmburu.local.models.WeatherForecastEntity

fun CurrentLocationWeather.toLocal(): LocationWeatherEntity {
    return LocationWeatherEntity(
        id = id,
        name = name,
        timeStamp = timeStamp,
        lat = coord.lat,
        lon = coord.lon,
        weatherType = weatherInfo[0].main,
        weatherTypeDescription = weatherInfo[0].description,
        temp = mainInfo.temp,
        tempMin = mainInfo.temp_min,
        tempMax = mainInfo.temp_max,
        isFavourite = false,
    )
}

fun WeatherForecast.toLocal(): WeatherForecastEntity {
    return WeatherForecastEntity(
        isoTimeStamp = isoTimeStamp,
        weatherType = weatherInfo[0].main,
        weatherTypeDescription = weatherInfo[0].description,
        temp = mainInfo.temp,
        tempMin = mainInfo.temp_min,
        tempMax = mainInfo.temp_max,
    )
}