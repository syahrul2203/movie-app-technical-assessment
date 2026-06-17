package com.syahrul.tmdbmovieapp.domain.repository

import androidx.paging.PagingData
import com.syahrul.tmdbmovieapp.core.common.AppResult
import com.syahrul.tmdbmovieapp.domain.model.Genre
import com.syahrul.tmdbmovieapp.domain.model.Movie
import com.syahrul.tmdbmovieapp.domain.model.MovieDetail
import com.syahrul.tmdbmovieapp.domain.model.Review
import com.syahrul.tmdbmovieapp.domain.model.Trailer
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getGenres(): AppResult<List<Genre>>
    suspend fun getMovieDetail(movieId: Int): AppResult<MovieDetail>
    suspend fun getMovieVideos(movieId: Int): AppResult<List<Trailer>>
    suspend fun getMovieTrailer(movieId: Int): AppResult<Trailer?>
    fun getMoviesByGenre(genreId: Int): Flow<PagingData<Movie>>
    fun getMovieReviews(movieId: Int): Flow<PagingData<Review>>
}
