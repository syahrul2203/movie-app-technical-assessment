package com.syahrul.tmdbmovieapp.presentation.movie_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.syahrul.tmdbmovieapp.core.common.AppResult
import com.syahrul.tmdbmovieapp.domain.model.Review
import com.syahrul.tmdbmovieapp.domain.usecase.GetMovieDetailUseCase
import com.syahrul.tmdbmovieapp.domain.usecase.GetMovieReviewsUseCase
import com.syahrul.tmdbmovieapp.domain.usecase.GetMovieTrailerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getMovieTrailerUseCase: GetMovieTrailerUseCase,
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MovieDetailUiState())
    val uiState = _uiState.asStateFlow()

    fun loadMovieDetail(movieId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            val detailResult = getMovieDetailUseCase(movieId)
            val trailerResult = getMovieTrailerUseCase(movieId)

            when {
                detailResult is AppResult.Success -> {
                    val trailer = (trailerResult as? AppResult.Success)?.data
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        movieDetail = detailResult.data,
                        trailer = trailer,
                    )
                }
                detailResult is AppResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = detailResult.message,
                    )
                }
            }
        }
    }

    fun retry() {
        _uiState.value.movieDetail?.id?.let { movieId ->
            loadMovieDetail(movieId)
        }
    }

    fun getMovieReviews(movieId: Int): Flow<PagingData<Review>> {
        return getMovieReviewsUseCase(movieId).cachedIn(viewModelScope)
    }
}
