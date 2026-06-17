package com.syahrul.tmdbmovieapp.domain.usecase

import com.syahrul.tmdbmovieapp.core.common.AppResult
import com.syahrul.tmdbmovieapp.domain.model.Genre
import com.syahrul.tmdbmovieapp.domain.repository.MovieRepository
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(): AppResult<List<Genre>> {
        return movieRepository.getGenres()
    }
}
