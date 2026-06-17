package com.syahrul.tmdbmovieapp.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.syahrul.tmdbmovieapp.ui.theme.MovieAppTheme

@Composable
fun EmptyContent(
    message: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = message)
    }
}

@Preview(showBackground = true)
@Composable
private fun EmptyContentPreview() {
    MovieAppTheme {
        EmptyContent(message = "No data available")
    }
}
