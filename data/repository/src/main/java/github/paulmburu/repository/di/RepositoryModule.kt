package github.paulmburu.repository.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import github.paulmburu.domain.repository.WeatherRepository
import github.paulmburu.local.dao.WeatherDao
import github.paulmburu.network.api.WeatherApi
import github.paulmburu.repository.repository.WeatherRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun providesWeatherRepository(
        weatherApi: WeatherApi,
        weatherDao: WeatherDao,
    ) : WeatherRepository {
        return WeatherRepositoryImpl(weatherApi, weatherDao)
    }
}