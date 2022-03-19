package github.paulmburu.domain.usercases

import github.paulmburu.common.Resource
import github.paulmburu.domain.models.Coordinates
import github.paulmburu.domain.models.CurrentLocationWeather
import github.paulmburu.domain.repository.WeatherRepository
import github.paulmburu.domain.usercases.base.FlowBaseUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

typealias FetchWeatherForecastBaseUseCase = FlowBaseUseCase<Coordinates, Flow<Resource<List<CurrentLocationWeather>>>>

class FetchWeatherForecastUseCase constructor(private val weatherRepository: WeatherRepository) :
    FetchWeatherForecastBaseUseCase {
    override fun invoke(params: Coordinates): Flow<Resource<List<CurrentLocationWeather>>> = flow {
        val result = weatherRepository.fetchWeatherForecast(params.lat, params.lon)
        result.collect { resource ->
            when (resource) {
                is Resource.Success -> {
                    val data = resource.data!!
                    emit(
                        Resource.Success(data)
                    )
                }
                is Resource.Error -> {
                    emit(Resource.Error(message = resource.message))
                }
                else -> {

                }
            }
        }
    }
}