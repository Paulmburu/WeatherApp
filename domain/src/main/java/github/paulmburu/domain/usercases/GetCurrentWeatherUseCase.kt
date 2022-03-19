package github.paulmburu.domain.usercases

import github.paulmburu.common.Resource
import github.paulmburu.domain.models.Coordinates
import github.paulmburu.domain.models.CurrentLocationWeather
import github.paulmburu.domain.repository.WeatherRepository
import github.paulmburu.domain.usercases.base.BaseUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

typealias GetCurrentWeatherBaseUseCase = BaseUseCase<Coordinates, Flow<Resource<CurrentLocationWeather>>>

class GetCurrentWeatherUseCase constructor(private val weatherRepository: WeatherRepository) :
    GetCurrentWeatherBaseUseCase {
    override suspend fun invoke(params: Coordinates): Flow<Resource<CurrentLocationWeather>> = flow {
        val result = weatherRepository.getCurrentWeather(params.lat, params.lon)
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