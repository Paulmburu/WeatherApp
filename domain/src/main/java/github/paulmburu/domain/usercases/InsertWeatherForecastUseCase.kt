package github.paulmburu.domain.usercases

import github.paulmburu.domain.models.CurrentLocationWeather
import github.paulmburu.domain.repository.WeatherRepository
import github.paulmburu.domain.usercases.base.BaseUseCase



typealias InsertWeatherForecastBaseUseCase = BaseUseCase<List<CurrentLocationWeather>, Unit>

class InsertWeatherForecastUseCase(private val weatherRepository: WeatherRepository) :
    InsertWeatherForecastBaseUseCase {
    override suspend fun invoke(params: List<CurrentLocationWeather>) {
        weatherRepository.insertWeatherForecast(params)
    }
}