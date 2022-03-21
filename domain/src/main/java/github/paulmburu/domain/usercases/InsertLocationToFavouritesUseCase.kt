package github.paulmburu.domain.usercases

import github.paulmburu.domain.models.CurrentLocationWeather
import github.paulmburu.domain.repository.WeatherRepository
import github.paulmburu.domain.usercases.base.BaseUseCase

class InsertLocationToFavourites {
}

typealias InsertLocationToFavouritesBaseUseCase = BaseUseCase<List<CurrentLocationWeather>, Unit>

class InsertLocationToFavouritesUseCase(private val weatherRepository: WeatherRepository) :
    InsertLocationToFavouritesBaseUseCase {
    override suspend fun invoke(params: List<CurrentLocationWeather>) {
        weatherRepository.insertLocationToFavourites(params)
    }
}