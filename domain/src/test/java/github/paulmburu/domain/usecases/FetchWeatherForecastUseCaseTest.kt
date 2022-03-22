package github.paulmburu.domain.usecases

import com.google.common.truth.Truth
import github.paulmburu.common.Resource
import github.paulmburu.domain.fakes.WeatherRepositoryFake
import github.paulmburu.domain.models.Coordinates
import github.paulmburu.domain.usercases.FetchWeatherForecastBaseUseCase
import github.paulmburu.domain.usercases.FetchWeatherForecastUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FetchWeatherForecastUseCaseTest {
    private lateinit var fetchWeatherForecastUseCase: FetchWeatherForecastBaseUseCase

    @Before
    fun setUp() {
        val weatherRepository = WeatherRepositoryFake()
        fetchWeatherForecastUseCase = FetchWeatherForecastUseCase(
            weatherRepository
        )
    }

    @Test
    fun `When FetchCurrentWeather is called with Coordinates, LocationCurrentWeather should be returned`() =
        runBlocking {
            fetchWeatherForecastUseCase(
                Coordinates(0.0,0.0)
            ).collect { resource ->
                when(resource){
                    is Resource.Success -> {
                        Truth.assertThat(resource.data!!.size).isEqualTo(4)
                    }
                }
            }
        }
}