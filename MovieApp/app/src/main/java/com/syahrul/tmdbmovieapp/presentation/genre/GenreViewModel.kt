package com.syahrul.tmdbmovieapp.presentation.genre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syahrul.tmdbmovieapp.core.common.AppResult
import com.syahrul.tmdbmovieapp.domain.usecase.GetGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(
    private val getGenresUseCase: GetGenresUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(GenreUiState())
    val uiState = _uiState.asStateFlow()

    fun loadGenres() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            when (val result = getGenresUseCase()) {
                is AppResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        genres = result.data,
                    )
                }
                is AppResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = result.message,
                    )
                }
            }
        }
    }

    fun retry() {
        loadGenres()
    }

    init {
        loadGenres()
    }
}
