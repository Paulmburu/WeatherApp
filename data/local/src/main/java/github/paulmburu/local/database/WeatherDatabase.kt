package github.paulmburu.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import github.paulmburu.local.dao.WeatherDao
import github.paulmburu.local.models.LocationWeatherEntity
import github.paulmburu.local.models.WeatherForecastEntity

@Database(entities = [LocationWeatherEntity::class, WeatherForecastEntity::class], version = 1, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {

    abstract val weatherDao: WeatherDao
}