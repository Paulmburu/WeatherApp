package github.paulmburu.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import github.paulmburu.local.dao.WeatherDao
import github.paulmburu.local.models.LocationWeatherEntity

@Database(entities = [LocationWeatherEntity::class], version = 1, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {

    abstract val weatherDao: WeatherDao
}