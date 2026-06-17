package com.syahrul.tmdbmovieapp.presentation.movie_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.syahrul.tmdbmovieapp.domain.model.Movie
import com.syahrul.tmdbmovieapp.presentation.components.EmptyContent
import com.syahrul.tmdbmovieapp.presentation.components.ErrorContent
import com.syahrul.tmdbmovieapp.presentation.components.LoadingContent
import com.syahrul.tmdbmovieapp.presentation.components.MoviePoster
import com.syahrul.tmdbmovieapp.presentation.components.RatingText
import com.syahrul.tmdbmovieapp.presentation.navigation.Screen
import com.syahrul.tmdbmovieapp.ui.theme.MovieAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(
    navHostController: NavHostController,
    genreId: Int,
    genreName: String,
    modifier: Modifier = Modifier,
    viewModel: MovieListViewModel = hiltViewModel(),
) {
    val movies: LazyPagingItems<Movie> = viewModel.getMoviesByGenre(genreId).collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = genreName) })
        },
        modifier = modifier,
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when {
                movies.loadState.refresh is LoadState.Loading -> LoadingContent()
                movies.loadState.refresh is LoadState.Error -> {
                    val error = movies.loadState.refresh as LoadState.Error
                    ErrorContent(
                        message = error.error.localizedMessage ?: "Error loading movies",
                        onRetry = movies::retry,
                    )
                }
                movies.itemCount == 0 -> EmptyContent(message = "No movies found for this genre")
                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        items(movies.itemCount) { index ->
                            movies[index]?.let { movie ->
                                MovieItem(
                                    movie = movie,
                                    onClick = {
                                        navHostController.navigate(Screen.MovieDetail.createRoute(movie.id))
                                    },
                                )
                            }
                        }
                        if (movies.loadState.append is LoadState.Loading) {
                            item {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                ) {
                                    LoadingContent(modifier = Modifier.height(48.dp))
                                }
                            }
                        }
                        if (movies.loadState.append is LoadState.Error) {
                            val error = movies.loadState.append as LoadState.Error
                            item {
                                ErrorContent(
                                    message = error.error.localizedMessage ?: "Error loading more movies",
                                    onRetry = movies::retry,
                                    modifier = Modifier.height(120.dp),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MovieItem(
    movie: Movie,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(360.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            MoviePoster(
                posterPath = movie.posterPath,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(12.dp),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = movie.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .basicMarquee(),
                    maxLines = 1,
                    softWrap = false,
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = movie.releaseDate.orEmpty().ifBlank { "Unknown release date" },
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    RatingText(rating = movie.voteAverage)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "/ 10", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieItemPositivePreview() {
    MovieAppTheme {
        MovieItem(
            movie = Movie(
                id = 1,
                title = "Wicked Minds",
                overview = "Sample overview",
                posterPath = "/sample.jpg",
                backdropPath = "/sample_backdrop.jpg",
                releaseDate = "2003-03-01",
                voteAverage = 6.3
            ),
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieItemLongTitlePreview() {
    MovieAppTheme {
        MovieItem(
            movie = Movie(
                id = 2,
                title = "My Teacher, My Obsession With A Very Long Movie Title",
                overview = "Sample overview",
                posterPath = null,
                backdropPath = null,
                releaseDate = "2018-06-12",
                voteAverage = 4.9
            ),
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieItemMissingDataPreview() {
    MovieAppTheme {
        MovieItem(
            movie = Movie(
                id = 3,
                title = "Unknown Movie",
                overview = "",
                posterPath = null,
                backdropPath = null,
                releaseDate = null,
                voteAverage = 0.0
            ),
            onClick = {}
        )
    }
}
