package github.paulmburu.weatherapp.util

import java.text.SimpleDateFormat
import java.util.*


fun Double.convertKelvinToCelsius(): String = "${this.minus(272.15).toInt()}°"


fun String.convertTimestampToDate(): String {
    return try {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        val date = format.parse(this)
        SimpleDateFormat("EEEE", Locale.ENGLISH).format(date?.time)
    } catch (e: Exception) {
        "undefined"
    }
}