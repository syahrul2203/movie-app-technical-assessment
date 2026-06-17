package com.syahrul.tmdbmovieapp.domain.usecase

import com.syahrul.tmdbmovieapp.core.common.AppResult
import com.syahrul.tmdbmovieapp.domain.model.MovieDetail
import com.syahrul.tmdbmovieapp.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(movieId: Int): AppResult<MovieDetail> {
        return movieRepository.getMovieDetail(movieId)
    }
}
