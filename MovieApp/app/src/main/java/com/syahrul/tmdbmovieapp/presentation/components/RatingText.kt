package com.syahrul.tmdbmovieapp.presentation.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.syahrul.tmdbmovieapp.ui.theme.MovieAppTheme

@Composable
fun RatingText(
    rating: Double,
) {
    Text(text = String.format("%.1f", rating))
}

@Preview(showBackground = true)
@Composable
private fun RatingTextPreview() {
    MovieAppTheme {
        RatingText(rating = 8.4)
    }
}
