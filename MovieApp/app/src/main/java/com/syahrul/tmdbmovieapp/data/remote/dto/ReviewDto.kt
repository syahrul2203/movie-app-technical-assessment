package com.syahrul.tmdbmovieapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ReviewDto(
    @SerializedName("id") val id: String,
    @SerializedName("author") val author: String,
    @SerializedName("content") val content: String,
    @SerializedName("author_details") val authorDetails: AuthorDetailsDto?,
    @SerializedName("created_at") val createdAt: String?,
)

data class AuthorDetailsDto(
    @SerializedName("rating") val rating: Double?,
)

