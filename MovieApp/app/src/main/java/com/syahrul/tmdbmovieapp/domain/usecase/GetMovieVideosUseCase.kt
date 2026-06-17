package com.syahrul.tmdbmovieapp.domain.usecase

import com.syahrul.tmdbmovieapp.core.common.AppResult
import com.syahrul.tmdbmovieapp.domain.model.Trailer
import com.syahrul.tmdbmovieapp.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieVideosUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(movieId: Int): AppResult<List<Trailer>> {
        return movieRepository.getMovieVideos(movieId)
    }
}
