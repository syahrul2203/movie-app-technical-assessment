package com.syahrul.tmdbmovieapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MovieDetailDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("runtime") val runtime: Int?,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("genres") val genres: List<GenreDto>,
)

