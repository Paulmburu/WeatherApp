package github.paulmburu.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import github.paulmburu.local.models.LocationWeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeather(currentWeatherLocation: List<LocationWeatherEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocationToFavourites(favourites: List<LocationWeatherEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherForecast(forecast: List<LocationWeatherEntity>)


    @Query("SELECT * FROM weather_table WHERE id = 'current_weather'")
    fun findCurrentWeather(): Flow<List<LocationWeatherEntity>>

    @Query("SELECT * FROM weather_table")
    fun weatherForecast(): Flow<List<LocationWeatherEntity>>

    @Query("SELECT * FROM weather_table WHERE is_favourite = 't'")
    fun findFavourites(): Flow<List<LocationWeatherEntity>>

}