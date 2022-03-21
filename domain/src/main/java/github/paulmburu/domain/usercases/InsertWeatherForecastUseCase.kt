package github.paulmburu.domain.usercases

import github.paulmburu.domain.models.CurrentLocationWeather
import github.paulmburu.domain.models.WeatherForecast
import github.paulmburu.domain.repository.WeatherRepository
import github.paulmburu.domain.usercases.base.BaseUseCase



typealias InsertWeatherForecastBaseUseCase = BaseUseCase<List<WeatherForecast>, Unit>

class InsertWeatherForecastUseCase(private val weatherRepository: WeatherRepository) :
    InsertWeatherForecastBaseUseCase {
    override suspend fun invoke(params: List<WeatherForecast>) {
        weatherRepository.insertWeatherForecast(params)
    }
}