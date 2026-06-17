package com.syahrul.tmdbmovieapp.presentation.movie_detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.syahrul.tmdbmovieapp.BuildConfig
import com.syahrul.tmdbmovieapp.domain.model.Genre
import com.syahrul.tmdbmovieapp.domain.model.Review
import com.syahrul.tmdbmovieapp.presentation.components.EmptyContent
import com.syahrul.tmdbmovieapp.presentation.components.ErrorContent
import com.syahrul.tmdbmovieapp.presentation.components.LoadingContent
import com.syahrul.tmdbmovieapp.presentation.components.RatingText
import com.syahrul.tmdbmovieapp.ui.theme.MovieAppTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MovieDetailScreen(
    navHostController: NavHostController,
    movieId: Int,
    modifier: Modifier = Modifier,
    viewModel: MovieDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val reviews: LazyPagingItems<Review> = viewModel.getMovieReviews(movieId).collectAsLazyPagingItems()
    val context = LocalContext.current

    LaunchedEffect(movieId) {
        viewModel.loadMovieDetail(movieId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Movie Detail") },
                navigationIcon = {
                    IconButton(onClick = navHostController::popBackStack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
        modifier = modifier,
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when {
                uiState.isLoading -> LoadingContent()
                uiState.errorMessage != null -> ErrorContent(
                    message = uiState.errorMessage!!,
                    onRetry = viewModel::retry,
                )
                uiState.movieDetail == null -> EmptyContent(message = "Movie not found")
                else -> {
                    val movieDetail = uiState.movieDetail!!
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        item {
                            movieDetail.backdropPath?.let { backdropPath ->
                                AsyncImage(
                                    model = ImageRequest.Builder(context)
                                        .data("${BuildConfig.TMDB_IMAGE_BASE_URL}w780$backdropPath")
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(16f / 9f)
                                        .clip(RoundedCornerShape(8.dp)),
                                )
                            }
                        }
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                            ) {
                                movieDetail.posterPath?.let { posterPath ->
                                    AsyncImage(
                                        model = ImageRequest.Builder(context)
                                            .data("${BuildConfig.TMDB_IMAGE_BASE_URL}w500$posterPath")
                                            .crossfade(true)
                                            .build(),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .width(120.dp)
                                            .aspectRatio(2f / 3f)
                                            .clip(RoundedCornerShape(8.dp)),
                                    )
                                }
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = movieDetail.title, style = MaterialTheme.typography.titleLarge)
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        RatingText(rating = movieDetail.voteAverage)
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(text = "/ 10", style = MaterialTheme.typography.bodySmall)
                                    }
                                    movieDetail.releaseDate?.let { releaseDate ->
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(text = "Release date: $releaseDate", style = MaterialTheme.typography.bodySmall)
                                    }
                                    movieDetail.runtime?.let { runtime ->
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(text = "Runtime: $runtime min", style = MaterialTheme.typography.bodySmall)
                                    }
                                }
                            }
                        }
                        item {
                            LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            items(movieDetail.genres, key = { it.id }) { genre ->
                                GenreChip(name = genre.name)
                            }
                        }
                        }
                        item {
                            Text(text = "Overview", style = MaterialTheme.typography.titleMedium)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = movieDetail.overview, style = MaterialTheme.typography.bodyMedium)
                        }
                        item {
                            if (uiState.trailer != null) {
                                Button(
                                    onClick = {
                                        openYoutube(
                                            context = context,
                                            trailerKey = uiState.trailer!!.key,
                                        )
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                ) {
                                    Text(text = "Watch Trailer")
                                }
                            } else {
                                Text(text = "Trailer not available", style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                        item {
                            Text(text = "Reviews", style = MaterialTheme.typography.titleMedium)
                        }
                        when {
                            reviews.loadState.refresh is LoadState.Loading -> {
                                item { LoadingContent(modifier = Modifier.height(100.dp)) }
                            }
                            reviews.loadState.refresh is LoadState.Error -> {
                                val error = reviews.loadState.refresh as LoadState.Error
                                item {
                                    ErrorContent(
                                        message = error.error.localizedMessage ?: "Error loading reviews",
                                        onRetry = reviews::retry,
                                        modifier = Modifier.height(200.dp),
                                    )
                                }
                            }
                            reviews.itemCount == 0 -> {
                                item { EmptyContent(message = "No reviews available yet", modifier = Modifier.height(100.dp)) }
                            }
                            else -> {
                                items(reviews.itemCount) { index ->
                                    reviews[index]?.let { review ->
                                        ReviewItem(review = review)
                                    }
                                }
                                if (reviews.loadState.append is LoadState.Loading) {
                                    item {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.Center,
                                        ) {
                                            LoadingContent(modifier = Modifier.height(48.dp))
                                        }
                                    }
                                }
                                if (reviews.loadState.append is LoadState.Error) {
                                    val error = reviews.loadState.append as LoadState.Error
                                    item {
                                        ErrorContent(
                                            message = error.error.localizedMessage ?: "Error loading more reviews",
                                            onRetry = reviews::retry,
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
    }
}

@Composable
fun ReviewItem(
    review: Review,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = review.author, style = MaterialTheme.typography.titleSmall)
                Spacer(modifier = Modifier.weight(1f))
                review.rating?.let { rating ->
                    RatingText(rating = rating)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "/ 10", style = MaterialTheme.typography.bodySmall)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = review.content, style = MaterialTheme.typography.bodySmall)
            review.createdAt?.let { createdAt ->
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Posted on: $createdAt", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReviewItemPositivePreview() {
    MovieAppTheme {
        ReviewItem(
            review = Review(
                id = "1",
                author = "John Doe",
                content = "This is a good movie with interesting story and characters.",
                rating = 8.0,
                createdAt = "2024-01-01"
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ReviewItemLongContentPreview() {
    MovieAppTheme {
        ReviewItem(
            review = Review(
                id = "2",
                author = "Long Review Author",
                content = "This is a very long review content used to verify that the UI handles long text properly without breaking the layout. The movie has several interesting scenes and the performance is quite memorable.",
                rating = 7.5,
                createdAt = "2024-01-02"
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ReviewItemMissingRatingPreview() {
    MovieAppTheme {
        ReviewItem(
            review = Review(
                id = "3",
                author = "Anonymous",
                content = "No rating was provided for this review.",
                rating = null,
                createdAt = null
            )
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun GenreChip(
    name: String,
    modifier: Modifier = Modifier,
) {
    AssistChip(
        onClick = {},
        label = {
            Text(
                text = name,
                modifier = Modifier.basicMarquee(),
                maxLines = 1,
                softWrap = false,
                overflow = TextOverflow.Clip,
            )
        },
        modifier = modifier.widthIn(min = 80.dp, max = 140.dp),
    )
}

@Preview(showBackground = true)
@Composable
private fun GenreChipLongTextPreview() {
    MovieAppTheme {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            items(
                listOf(
                    Genre(id = 1, name = "Science Fiction"),
                    Genre(id = 2, name = "Action Adventure"),
                    Genre(id = 3, name = "Thriller"),
                    Genre(id = 4, name = "Documentary")
                ),
                key = { it.id }
            ) { genre ->
                GenreChip(name = genre.name)
            }
        }
    }
}

private fun openYoutube(context: Context, trailerKey: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=$trailerKey"))
    context.startActivity(intent)
}
