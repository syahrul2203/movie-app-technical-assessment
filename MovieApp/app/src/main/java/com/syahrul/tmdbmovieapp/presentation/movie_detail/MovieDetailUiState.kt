package com.syahrul.tmdbmovieapp.presentation.movie_detail

import com.syahrul.tmdbmovieapp.domain.model.MovieDetail
import com.syahrul.tmdbmovieapp.domain.model.Trailer

data class MovieDetailUiState(
    val isLoading: Boolean = false,
    val movieDetail: MovieDetail? = null,
    val trailer: Trailer? = null,
    val errorMessage: String? = null,
)
