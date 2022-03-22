package github.paulmburu.weatherapp.ui.mainactivity

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import github.paulmburu.domain.models.Coordinates
import github.paulmburu.domain.usercases.FetchCurrentWeatherUseCase
import github.paulmburu.domain.usercases.FetchWeatherForecastUseCase
import github.paulmburu.domain.usercases.GetCurrentWeatherUseCase
import github.paulmburu.domain.usercases.GetWeatherForecastUseCase
import github.paulmburu.weatherapp.ui.mainActivity.MainViewModel
import github.paulmburu.weatherapp.util.ConnectivityProvider
import github.paulmburu.weatherapp.util.MainCoroutineRule
import github.paulmburu.weatherapp.util.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @RelaxedMockK
    private lateinit var fetchCurrentWeatherUseCase: FetchCurrentWeatherUseCase

    @RelaxedMockK
    private lateinit var fetchWeatherForecastUseCase: FetchWeatherForecastUseCase

    @RelaxedMockK
    private lateinit var getCurrentWeatherUseCase: GetCurrentWeatherUseCase

    @RelaxedMockK
    private lateinit var getWeatherForecastUseCase: GetWeatherForecastUseCase

    @RelaxedMockK
    private lateinit var connectivityProvider: ConnectivityProvider

    private lateinit var viewModel : MainViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = MainViewModel(
            fetchCurrentWeatherUseCase,
            fetchWeatherForecastUseCase,
            getCurrentWeatherUseCase,
            getWeatherForecastUseCase,
            connectivityProvider
        )
    }

    @Test
    fun `when setCoordinates is called, the current coordinates should be set`() {
        viewModel.setCoordinates(28.4, 34.6)
        val value = viewModel.currentUserCoordinates.getOrAwaitValue()
        Truth.assertThat(value).isEqualTo(Coordinates(28.4, 34.6))
    }
}