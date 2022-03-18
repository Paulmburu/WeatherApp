package github.paulmburu.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_table")
class LocationWeatherEntity(
    @PrimaryKey
    var id: String,

    @ColumnInfo(name = "time_forecast")
    val timeForecast: String?,

    @ColumnInfo(name = "lat")
    val lat: Double,

    @ColumnInfo(name = "lon")
    val lon: Double,

    @ColumnInfo(name = "weather_type")
    val weatherType: String,

    @ColumnInfo(name = "weather_type_description")
    val weatherTypeDescription: String,

    @ColumnInfo(name = "temp")
    val temp: Double,

    @ColumnInfo(name = "temp_min")
    val tempMin: Double,

    @ColumnInfo(name = "temp_max")
    val tempMax: Double,

    @ColumnInfo(name = "is_favourite")
    var isFavourite: Boolean = false,


)