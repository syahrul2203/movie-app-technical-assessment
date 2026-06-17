package com.syahrul.tmdbmovieapp.domain.usecase

import androidx.paging.PagingData
import com.syahrul.tmdbmovieapp.domain.model.Review
import com.syahrul.tmdbmovieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieReviewsUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    operator fun invoke(movieId: Int): Flow<PagingData<Review>> {
        return movieRepository.getMovieReviews(movieId)
    }
}
