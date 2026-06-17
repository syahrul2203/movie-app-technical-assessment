package com.syahrul.tmdbmovieapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.syahrul.tmdbmovieapp.core.common.AppResult
import com.syahrul.tmdbmovieapp.core.common.ErrorHandler
import com.syahrul.tmdbmovieapp.data.mapper.toDomain
import com.syahrul.tmdbmovieapp.data.mapper.toDomainTrailer
import com.syahrul.tmdbmovieapp.data.remote.api.TmdbApiService
import com.syahrul.tmdbmovieapp.data.remote.paging.MoviePagingSource
import com.syahrul.tmdbmovieapp.data.remote.paging.ReviewPagingSource
import com.syahrul.tmdbmovieapp.domain.model.Genre
import com.syahrul.tmdbmovieapp.domain.model.Movie
import com.syahrul.tmdbmovieapp.domain.model.MovieDetail
import com.syahrul.tmdbmovieapp.domain.model.Review
import com.syahrul.tmdbmovieapp.domain.model.Trailer
import com.syahrul.tmdbmovieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val tmdbApiService: TmdbApiService,
) : MovieRepository {
    override suspend fun getGenres(): AppResult<List<Genre>> {
        return try {
            val response = tmdbApiService.getGenreList()
            val genres = response.genres.map { it.toDomain() }
            AppResult.Success(genres)
        } catch (e: Exception) {
            AppResult.Error(
                message = ErrorHandler.handle(e),
                throwable = e,
            )
        }
    }

    override suspend fun getMovieDetail(movieId: Int): AppResult<MovieDetail> {
        return try {
            val response = tmdbApiService.getMovieDetail(movieId)
            val movieDetail = response.toDomain()
            AppResult.Success(movieDetail)
        } catch (e: Exception) {
            AppResult.Error(
                message = ErrorHandler.handle(e),
                throwable = e,
            )
        }
    }

    override suspend fun getMovieVideos(movieId: Int): AppResult<List<Trailer>> {
        return try {
            val response = tmdbApiService.getMovieVideos(movieId)
            val trailers = response.results.map { it.toDomainTrailer() }
            AppResult.Success(trailers)
        } catch (e: Exception) {
            AppResult.Error(
                message = ErrorHandler.handle(e),
                throwable = e,
            )
        }
    }

    override suspend fun getMovieTrailer(movieId: Int): AppResult<Trailer?> {
        return when (val videosResult = getMovieVideos(movieId)) {
            is AppResult.Success -> {
                val trailer = videosResult.data.firstOrNull {
                    it.site == "YouTube" && it.type == "Trailer"
                }
                AppResult.Success(trailer)
            }
            is AppResult.Error -> videosResult
        }
    }

    override fun getMoviesByGenre(genreId: Int): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                MoviePagingSource(
                    apiService = tmdbApiService,
                    genreId = genreId,
                )
            },
        ).flow
    }

    override fun getMovieReviews(movieId: Int): Flow<PagingData<Review>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                ReviewPagingSource(
                    apiService = tmdbApiService,
                    movieId = movieId,
                )
            },
        ).flow
    }
}
