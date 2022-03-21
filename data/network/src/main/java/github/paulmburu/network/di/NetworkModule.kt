package github.paulmburu.network.di


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import github.paulmburu.network.BuildConfig
import github.paulmburu.network.api.CreateAuthInterceptor
import github.paulmburu.network.api.WeatherApi
import github.paulmburu.network.util.Constants.BASE_URL
import github.paulmburu.network.util.Constants.TokenTypes.BEARER
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun providesOkhttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = when (BuildConfig.BUILD_TYPE) {
                "release" -> HttpLoggingInterceptor.Level.NONE
                else -> {
                    HttpLoggingInterceptor.Level.BODY
                }
            }
        }

        val authInterceptor = CreateAuthInterceptor()

        return OkHttpClient.Builder()
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()

    }

    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    @Provides
    fun providesWeatherApi(retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }

}

