package github.paulmburu.network.api


import github.paulmburu.network.util.Constants
import okhttp3.Interceptor
import okhttp3.Response

class CreateAuthInterceptor(private val tokenType: String, private val token: String) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader(Constants.HttpHeaders.AUTHORIZATION, "$tokenType $token")
            .build()

        return chain.proceed(request)
    }
}