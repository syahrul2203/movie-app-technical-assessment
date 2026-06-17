package com.syahrul.tmdbmovieapp.domain.usecase

import androidx.paging.PagingData
import com.syahrul.tmdbmovieapp.domain.model.Movie
import com.syahrul.tmdbmovieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesByGenreUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    operator fun invoke(genreId: Int): Flow<PagingData<Movie>> {
        return movieRepository.getMoviesByGenre(genreId)
    }
}
