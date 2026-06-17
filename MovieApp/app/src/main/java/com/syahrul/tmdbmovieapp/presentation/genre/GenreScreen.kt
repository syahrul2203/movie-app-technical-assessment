package com.syahrul.tmdbmovieapp.presentation.genre

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.syahrul.tmdbmovieapp.domain.model.Genre
import com.syahrul.tmdbmovieapp.presentation.components.EmptyContent
import com.syahrul.tmdbmovieapp.presentation.components.ErrorContent
import com.syahrul.tmdbmovieapp.presentation.components.LoadingContent
import com.syahrul.tmdbmovieapp.presentation.navigation.Screen
import com.syahrul.tmdbmovieapp.ui.theme.MovieAppTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun GenreScreen(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: GenreViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    GenreContent(
        uiState = uiState,
        onGenreClick = { genre ->
            navHostController.navigate(
                Screen.MovieList.createRoute(
                    genreId = genre.id,
                    genreName = genre.name,
                ),
            )
        },
        onRetry = viewModel::retry,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun GenreContent(
    uiState: GenreUiState,
    onGenreClick: (Genre) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Movies") })
        },
        modifier = modifier,
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when {
                uiState.isLoading -> LoadingContent()
                uiState.errorMessage != null -> ErrorContent(
                    message = uiState.errorMessage!!,
                    onRetry = onRetry,
                )
                uiState.genres.isEmpty() -> EmptyContent(message = "No genres available")
                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        items(uiState.genres) { genre ->
                            GenreItem(
                                genre = genre,
                                onClick = { onGenreClick(genre) },
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun GenreItem(
    genre: Genre,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(96.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = genre.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .basicMarquee(),
                textAlign = TextAlign.Center,
                maxLines = 1,
                softWrap = false,
                overflow = TextOverflow.Clip,
                style = MaterialTheme.typography.titleLarge,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GenreContentSuccessPreview() {
    MovieAppTheme {
        GenreContent(
            uiState = GenreUiState(
                isLoading = false,
                genres = listOf(
                    Genre(id = 28, name = "Action"),
                    Genre(id = 878, name = "Science Fiction"),
                    Genre(id = 53, name = "Thriller"),
                    Genre(id = 10749, name = "Romance")
                )
            ),
            onGenreClick = {},
            onRetry = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GenreContentLoadingPreview() {
    MovieAppTheme {
        GenreContent(
            uiState = GenreUiState(isLoading = true),
            onGenreClick = {},
            onRetry = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GenreContentErrorPreview() {
    MovieAppTheme {
        GenreContent(
            uiState = GenreUiState(
                errorMessage = "No internet connection. Please try again."
            ),
            onGenreClick = {},
            onRetry = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GenreContentEmptyPreview() {
    MovieAppTheme {
        GenreContent(
            uiState = GenreUiState(
                isLoading = false,
                genres = emptyList()
            ),
            onGenreClick = {},
            onRetry = {}
        )
    }
}
