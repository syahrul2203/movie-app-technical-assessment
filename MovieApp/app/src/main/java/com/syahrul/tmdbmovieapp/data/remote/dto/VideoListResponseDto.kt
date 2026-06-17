package com.syahrul.tmdbmovieapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class VideoListResponseDto(
    @SerializedName("results") val results: List<VideoDto>,
)

