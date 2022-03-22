package github.paulmburu.weatherapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import github.paulmburu.weatherapp.databinding.WeatherForecastItemBinding
import github.paulmburu.weatherapp.models.WeatherForecastPresentation

class WeatherForecastRecyclerAdapter(
) :
    ListAdapter<WeatherForecastPresentation, WeatherForecastRecyclerAdapter.WeatherForecastViewHolder>(
        WeatherForecastComparator()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherForecastViewHolder {
        return WeatherForecastViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: WeatherForecastViewHolder, position: Int) {
        val weatherForecast = getItem(position)
        holder.bind(weatherForecast)
    }


    class WeatherForecastViewHolder(private val binding: WeatherForecastItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            weatherForecastPresentation: WeatherForecastPresentation,
        ) {
            with(binding) {
                weatherForecast = weatherForecastPresentation
                executePendingBindings()
            }
        }

        companion object {
            fun create(parent: ViewGroup): WeatherForecastViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = WeatherForecastItemBinding.inflate(layoutInflater, parent, false)

                return WeatherForecastViewHolder(binding)
            }
        }

    }

    class WeatherForecastComparator : DiffUtil.ItemCallback<WeatherForecastPresentation>() {
        override fun areItemsTheSame(
            oldItem: WeatherForecastPresentation,
            newItem: WeatherForecastPresentation
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: WeatherForecastPresentation,
            newItem: WeatherForecastPresentation
        ): Boolean {
            return oldItem.isoTimeStamp == newItem.isoTimeStamp

        }

    }
}