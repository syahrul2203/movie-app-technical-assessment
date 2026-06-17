package com.syahrul.tmdbmovieapp.data.remote.api

import com.syahrul.tmdbmovieapp.data.remote.dto.GenreListResponseDto
import com.syahrul.tmdbmovieapp.data.remote.dto.MovieDetailDto
import com.syahrul.tmdbmovieapp.data.remote.dto.MovieDto
import com.syahrul.tmdbmovieapp.data.remote.dto.PagedResponseDto
import com.syahrul.tmdbmovieapp.data.remote.dto.ReviewDto
import com.syahrul.tmdbmovieapp.data.remote.dto.VideoListResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApiService {

    @GET("genre/movie/list")
    suspend fun getGenreList(): GenreListResponseDto

    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("with_genres") genreId: Int,
        @Query("page") page: Int,
    ): PagedResponseDto<MovieDto>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
    ): MovieDetailDto

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int,
    ): PagedResponseDto<ReviewDto>

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: Int,
    ): VideoListResponseDto
}
