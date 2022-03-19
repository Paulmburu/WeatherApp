package github.paulmburu.network.mappers

import github.paulmburu.domain.models.Coordinates
import github.paulmburu.domain.models.CurrentLocationWeather
import github.paulmburu.domain.models.MainInfo
import github.paulmburu.domain.models.WeatherInfo
import github.paulmburu.network.models.CoordinatesDto
import github.paulmburu.network.models.CurrentLocationWeatherDto
import github.paulmburu.network.models.MainInfoDto
import github.paulmburu.network.models.WeatherInfoDto


fun CoordinatesDto.toDomain(): Coordinates {
    return Coordinates(
        lat = lat,
        lon = long,
    )
}

fun MainInfoDto.toDomain(): MainInfo {
    return MainInfo(
        temp = temp,
        temp_min = temp_min,
        temp_max = temp_max,
    )
}

fun WeatherInfoDto.toDomain(): WeatherInfo {
    return WeatherInfo(
        main = main,
        description = description,
    )
}

fun CurrentLocationWeatherDto.toDomain(): CurrentLocationWeather {
    return CurrentLocationWeather(
        id = id,
        timeForecast = timeForecast,
        coord = coord.toDomain(),
        mainInfo = mainInfo.toDomain(),
        weatherInfo = weatherInfo.map { it.toDomain() }
    )
}