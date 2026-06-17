package com.syahrul.tmdbmovieapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class VideoDto(
    @SerializedName("id") val id: String,
    @SerializedName("key") val key: String,
    @SerializedName("name") val name: String,
    @SerializedName("site") val site: String,
    @SerializedName("type") val type: String,
)

