package github.paulmburu.weatherapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import github.paulmburu.domain.repository.WeatherRepository
import github.paulmburu.domain.usercases.*

@Module
@InstallIn(ActivityRetainedComponent::class)
object UseCaseModule {

    @Provides
    fun providesFetchCurrentWeatherUseCase(weatherRepository: WeatherRepository): FetchCurrentWeatherUseCase {
        return FetchCurrentWeatherUseCase(weatherRepository)
    }

    @Provides
    fun providesFetchWeatherForecastUseCase(weatherRepository: WeatherRepository): FetchWeatherForecastUseCase {
        return FetchWeatherForecastUseCase(weatherRepository)
    }


    @Provides
    fun providesGetWeatherForecastUseCase(weatherRepository: WeatherRepository): GetWeatherForecastUseCase {
        return GetWeatherForecastUseCase(weatherRepository)
    }

    @Provides
    fun providesGetCurrentWeatherUseCase(weatherRepository: WeatherRepository): GetCurrentWeatherUseCase {
        return GetCurrentWeatherUseCase(weatherRepository)
    }

    @Provides
    fun providesInsertCurrentWeatherUseCase(weatherRepository: WeatherRepository): InsertCurrentWeatherUseCase {
        return InsertCurrentWeatherUseCase(weatherRepository)
    }

    @Provides
    fun providesInsertLocationToFavouritesUseCase(weatherRepository: WeatherRepository): InsertLocationToFavouritesUseCase {
        return InsertLocationToFavouritesUseCase(weatherRepository)
    }

    @Provides
    fun providesIInsertWeatherForecastUseCase(weatherRepository: WeatherRepository): InsertWeatherForecastUseCase {
        return InsertWeatherForecastUseCase(weatherRepository)
    }

}