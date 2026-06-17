package com.syahrul.tmdbmovieapp.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Generic pagination wrapper returned by TMDB endpoints.
 *
 * Example fields:
 * - page
 * - results
 * - total_pages
 */
data class PagedResponseDto<T>(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<T>,
    @SerializedName("total_pages") val totalPages: Int,
)

