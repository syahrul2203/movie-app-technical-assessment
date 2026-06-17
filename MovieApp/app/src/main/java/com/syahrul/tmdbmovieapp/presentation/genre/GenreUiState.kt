package com.syahrul.tmdbmovieapp.presentation.genre

import com.syahrul.tmdbmovieapp.domain.model.Genre

data class GenreUiState(
    val isLoading: Boolean = false,
    val genres: List<Genre> = emptyList(),
    val errorMessage: String? = null,
)
