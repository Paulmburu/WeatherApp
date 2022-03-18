package github.paulmburu.local.mappers

import github.paulmburu.domain.models.Coordinates
import github.paulmburu.domain.models.CurrentLocationWeather
import github.paulmburu.domain.models.MainInfo
import github.paulmburu.domain.models.WeatherInfo
import github.paulmburu.local.models.LocationWeatherEntity

fun LocationWeatherEntity.toDomain(): CurrentLocationWeather {
    return CurrentLocationWeather(
        id = id,
        coord = Coordinates(lat = lat, lon =lon),
        weatherInfo = listOf(WeatherInfo(main = weatherType, description = weatherTypeDescription)),
        mainInfo = MainInfo(temp = temp, temp_min = tempMin, temp_max = tempMax),
        timeForecast = timeForecast
    )
}