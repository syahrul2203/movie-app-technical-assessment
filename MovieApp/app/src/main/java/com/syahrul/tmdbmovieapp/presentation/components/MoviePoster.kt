package com.syahrul.tmdbmovieapp.presentation.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.syahrul.tmdbmovieapp.BuildConfig
import com.syahrul.tmdbmovieapp.ui.theme.MovieAppTheme

@Composable
fun MoviePoster(
    posterPath: String?,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("${BuildConfig.TMDB_IMAGE_BASE_URL}w500$posterPath")
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f / 3f)
                .clip(RoundedCornerShape(8.dp)),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MoviePosterPreviewWithPath() {
    MovieAppTheme {
        MoviePoster(posterPath = "/sample.jpg")
    }
}

@Preview(showBackground = true)
@Composable
private fun MoviePosterPreviewWithoutPath() {
    MovieAppTheme {
        MoviePoster(posterPath = null)
    }
}
