package com.syahrul.tmdbmovieapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.syahrul.tmdbmovieapp.presentation.genre.GenreScreen
import com.syahrul.tmdbmovieapp.presentation.movie_detail.MovieDetailScreen
import com.syahrul.tmdbmovieapp.presentation.movie_list.MovieListScreen

@Composable
fun AppNavigation(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Genre.route,
    ) {
        composable(Screen.Genre.route) {
            GenreScreen(navHostController = navHostController)
        }
        composable(Screen.MovieList.route) { backStackEntry ->
            val genreId = backStackEntry.arguments?.getString("genreId")?.toIntOrNull() ?: 0
            val genreName = backStackEntry.arguments?.getString("genreName").orEmpty()
            MovieListScreen(
                navHostController = navHostController,
                genreId = genreId,
                genreName = genreName,
            )
        }
        composable(Screen.MovieDetail.route) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")?.toIntOrNull() ?: 0
            MovieDetailScreen(
                navHostController = navHostController,
                movieId = movieId,
            )
        }
    }
}
