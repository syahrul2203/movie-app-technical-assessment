package com.syahrul.tmdbmovieapp.presentation.navigation

sealed class Screen(val route: String) {
    data object Genre : Screen("genre")
    data object MovieList : Screen("movie_list/{genreId}/{genreName}") {
        fun createRoute(genreId: Int, genreName: String): String =
            "movie_list/$genreId/$genreName"
    }
    data object MovieDetail : Screen("movie_detail/{movieId}") {
        fun createRoute(movieId: Int): String =
            "movie_detail/$movieId"
    }
}
