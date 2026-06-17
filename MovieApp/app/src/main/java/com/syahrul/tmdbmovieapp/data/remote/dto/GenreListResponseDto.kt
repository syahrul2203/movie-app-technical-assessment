package com.syahrul.tmdbmovieapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class GenreListResponseDto(
    @SerializedName("genres") val genres: List<GenreDto>,
)

