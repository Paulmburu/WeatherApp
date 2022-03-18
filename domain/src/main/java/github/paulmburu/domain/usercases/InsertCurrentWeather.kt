package github.paulmburu.domain.usercases

import github.paulmburu.domain.models.CurrentLocationWeather
import github.paulmburu.domain.repository.WeatherRepository
import github.paulmburu.domain.usercases.base.BaseUseCase



typealias InsertCurrentWeatherBaseUseCase = BaseUseCase<List<CurrentLocationWeather>, Unit>

class InsertTasksWithProjectUseCase(private val weatherRepository: WeatherRepository) :
    InsertCurrentWeatherBaseUseCase {
    override suspend fun invoke(params: List<CurrentLocationWeather>) {
        weatherRepository.insertCurrentWeather(params)
    }
}