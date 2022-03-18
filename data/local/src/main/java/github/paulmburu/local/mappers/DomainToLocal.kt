package github.paulmburu.local.mappers

import github.paulmburu.domain.models.CurrentLocationWeather
import github.paulmburu.local.models.LocationWeatherEntity

fun CurrentLocationWeather.toLocal(): LocationWeatherEntity {
    return LocationWeatherEntity(
        id = id,
        timeForecast = timeForecast,
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