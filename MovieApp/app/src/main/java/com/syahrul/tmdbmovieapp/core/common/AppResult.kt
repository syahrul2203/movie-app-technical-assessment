package com.syahrul.tmdbmovieapp.core.common

sealed interface AppResult<out T> {
    data class Success<out T>(val data: T) : AppResult<T>
    data class Error(
        val message: String,
        val throwable: Throwable? = null,
    ) : AppResult<Nothing>
}
