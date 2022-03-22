package github.paulmburu.domain.usecases

import com.google.common.truth.Truth
import github.paulmburu.common.Resource
import github.paulmburu.domain.fakes.Data
import github.paulmburu.domain.fakes.WeatherRepositoryFake
import github.paulmburu.domain.models.Coordinates
import github.paulmburu.domain.usercases.FetchCurrentWeatherBaseUseCase
import github.paulmburu.domain.usercases.FetchCurrentWeatherUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FetchCurrentWeatherUseCaseTest {
    private lateinit var fetchCurrentWeatherBaseUseCase: FetchCurrentWeatherBaseUseCase

    @Before
    fun setUp() {
        val weatherRepository = WeatherRepositoryFake()
        fetchCurrentWeatherBaseUseCase = FetchCurrentWeatherUseCase(
            weatherRepository
        )
    }

    @Test
    fun `When FetchCurrentWeather is called with Coordinates, LocationCurrentWeather should be returned`() =
        runBlocking {
            fetchCurrentWeatherBaseUseCase(
                Coordinates(0.0,0.0)
            ).collect { resource ->
                when(resource){
                    is Resource.Success -> {
                        Truth.assertThat(resource.data).isEqualTo(Data.currentLocationWeather)
                    }
                }
            }
        }
}