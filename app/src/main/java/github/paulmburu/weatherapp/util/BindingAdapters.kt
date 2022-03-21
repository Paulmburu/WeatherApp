package github.paulmburu.weatherapp.util


import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import github.paulmburu.weatherapp.R

@BindingAdapter("imageDrawable")
fun bindImage(imgView: ImageView, weatherType: String?) {
    when {
        weatherType.equals("Clouds") -> imgView.setImageDrawable(
            ResourcesCompat.getDrawable(imgView.resources, R.drawable.partlysunny, null)
        )
        weatherType.equals("Clear") -> imgView.setImageDrawable(
            ResourcesCompat.getDrawable(imgView.resources, R.drawable.clear, null)
        )
        weatherType.equals("Rain") -> imgView.setImageDrawable(
            ResourcesCompat.getDrawable(imgView.resources, R.drawable.rain, null)
        )
    }
}