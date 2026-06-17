package com.syahrul.tmdbmovieapp.core.common

import retrofit2.HttpException
import java.io.IOException

object ErrorHandler {
    fun handle(throwable: Throwable): String {
        return when (throwable) {
            is IOException -> "No internet connection"
            is HttpException -> {
                when (throwable.code()) {
                    401 -> "Unauthorized request"
                    in 500..599 -> "Server error"
                    else -> "Something went wrong"
                }
            }
            else -> "Something went wrong"
        }
    }
}
