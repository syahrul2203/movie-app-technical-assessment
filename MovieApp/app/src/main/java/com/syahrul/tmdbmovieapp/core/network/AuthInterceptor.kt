package com.syahrul.tmdbmovieapp.core.network

import com.syahrul.tmdbmovieapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${BuildConfig.TMDB_ACCESS_TOKEN}")
            .addHeader("Accept", "application/json")
            .build()
        return chain.proceed(request)
    }
}
