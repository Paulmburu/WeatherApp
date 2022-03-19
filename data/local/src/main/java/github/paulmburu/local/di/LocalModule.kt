package github.paulmburu.local.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import github.paulmburu.local.dao.WeatherDao
import github.paulmburu.local.database.WeatherDatabase

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    fun providesWeatherDatabase(@ApplicationContext context: Context): WeatherDatabase {
        return Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()

    }

    @Provides
    fun providesWeatherDao(database: WeatherDatabase): WeatherDao {
        return database.weatherDao
    }

    const val DATABASE_NAME = "weather_database"

}