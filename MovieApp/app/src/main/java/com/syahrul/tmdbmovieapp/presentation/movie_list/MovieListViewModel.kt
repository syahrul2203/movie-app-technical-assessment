package com.syahrul.tmdbmovieapp.presentation.movie_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.syahrul.tmdbmovieapp.domain.model.Movie
import com.syahrul.tmdbmovieapp.domain.usecase.GetMoviesByGenreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getMoviesByGenreUseCase: GetMoviesByGenreUseCase,
) : ViewModel() {
    fun getMoviesByGenre(genreId: Int): Flow<PagingData<Movie>> {
        return getMoviesByGenreUseCase(genreId).cachedIn(viewModelScope)
    }
}
