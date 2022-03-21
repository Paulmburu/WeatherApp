package github.paulmburu.network.api


import github.paulmburu.network.util.Constants
import okhttp3.Interceptor
import okhttp3.Response

class CreateAuthInterceptor() :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .build()

        return chain.proceed(request)
    }
}