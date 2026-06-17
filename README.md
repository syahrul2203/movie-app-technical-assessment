# MovieApp

A native Android movie application built with Kotlin, Jetpack Compose, Material 3, MVVM/Clean Architecture, Retrofit, Hilt, and Paging 3. The app integrates with The Movie Database (TMDB) API to display official movie genres, discover movies by genre, show movie details, user reviews, and YouTube trailers.

## Technical Assessment Context

This project was created as a technical assessment to demonstrate Android development skills, including API integration, UI/UX implementation, pagination, error handling, and clean code quality using the latest Android technologies.

## Features

- [x] Display official movie genres
- [x] Display discover movies by genre
- [x] Display movie primary information
- [x] Display user reviews
- [x] Display YouTube trailer
- [x] Endless scrolling for movies
- [x] Endless scrolling for reviews
- [x] Loading state
- [x] Empty state
- [x] Error state
- [x] Retry action

## Tech Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose, Material 3
- **Architecture**: Clean Architecture, MVVM
- **Networking**: Retrofit, OkHttp
- **Dependency Injection**: Hilt
- **Pagination**: Paging 3
- **Async/Reactive**: Kotlin Coroutines, StateFlow
- **Image Loading**: Coil 3
- **Debugging**: Chucker

## Architecture Overview

The project follows **Clean Architecture** with clear separation of concerns:
1. **Data Layer**: Handles API communication, DTO models, mappers, repository implementation, PagingSource
2. **Domain Layer**: Contains domain models, repository contract, use cases
3. **Presentation Layer**: UI screens, ViewModels, UI state management
4. **Core**: Common utilities, network configuration, error handling

## Project Structure

```
com.syahrul.tmdbmovieapp
├── core
│   ├── common          # AppResult sealed class, ErrorHandler
│   ├── di              # Hilt modules
│   └── network         # API constants, AuthInterceptor, NetworkModule
├── data
│   ├── mapper          # DTO → domain model mappers
│   ├── remote
│   │   ├── api         # TMDB API service interface
│   │   ├── dto         # Data Transfer Objects
│   │   └── paging      # MoviePagingSource, ReviewPagingSource
│   └── repository      # MovieRepositoryImpl
├── domain
│   ├── model           # Genre, Movie, MovieDetail, Review, Trailer
│   ├── repository      # MovieRepository contract
│   └── usecase         # Use cases
└── presentation
    ├── components      # Reusable UI components
    ├── genre           # GenreScreen, GenreViewModel, GenreUiState
    ├── movie_detail    # MovieDetailScreen, MovieDetailViewModel, MovieDetailUiState
    ├── movie_list      # MovieListScreen, MovieListViewModel
    └── navigation      # AppNavigation, Screen
```

## API Integration

The app uses TMDB API endpoints:
- `/genre/movie/list`: Get movie genres
- `/discover/movie`: Discover movies by genre
- `/movie/{id}`: Get movie details
- `/movie/{id}/reviews`: Get movie reviews
- `/movie/{id}/videos`: Get movie videos (for trailers)

Image base URL: `https://image.tmdb.org/t/p/`

## TMDB API Token Setup

To run the app, you'll need a TMDB API access token:

1. Sign up for a TMDB account at [https://www.themoviedb.org/signup](https://www.themoviedb.org/signup)
2. Go to [https://www.themoviedb.org/settings/api](https://www.themoviedb.org/settings/api) and create an API access token
3. Create a file named `local.properties` in the project root directory
4. Add your API access token to `local.properties`:
```properties
TMDB_ACCESS_TOKEN=your_tmdb_access_token_here
```

⚠️ **Important**: Do not commit `local.properties` to GitHub! This file contains your private API token.

## How to Run the Project

1. Clone or download this repository
2. Set up your TMDB API token (see instructions above)
3. Open the project in Android Studio
4. Sync Gradle
5. Run the app on an emulator or physical device

## Positive and Negative Cases Handled

### Positive Cases
- Genres load successfully
- Movies load successfully for selected genre
- Movie details load successfully
- User reviews load successfully
- YouTube trailer is available and opens in browser/app

### Negative Cases
- No internet connection: Shows error state with retry button
- Server error: Shows error state with retry button
- Unauthorized request: Shows error state
- No genres available: Shows empty state
- No movies available for genre: Shows empty state
- No reviews available for movie: Shows empty state
- Trailer not available: Hides trailer button or shows "Trailer not available"

## Screens Included

1. **Genre Screen**: Displays list of official TMDB movie genres, click to browse movies by genre
2. **Movie List Screen**: Displays movies by selected genre in a grid with endless scrolling, shows movie poster, title, release date, and rating
3. **Movie Detail Screen**: Displays movie poster, backdrop, title, overview, release date, runtime, rating, genre chips, YouTube trailer button, and user reviews list

## Endless Scrolling

Uses **Paging 3 Library**:
- `MoviePagingSource`: Handles pagination for movie list from TMDB `/discover/movie` endpoint
- `ReviewPagingSource`: Handles pagination for movie reviews from TMDB `/movie/{id}/reviews` endpoint
- Pagination states handled in UI: initial loading, initial error, append loading, append error

## YouTube Trailer

- Fetches movie videos from TMDB API
- Filters to find first YouTube video with type "Trailer"
- If found, shows "Watch Trailer" button that opens YouTube app/browser
- If not found, shows "Trailer not available" text or hides button

## Submission Notes

All required features for the technical assessment are implemented, including:
- Clean Architecture with clear layer separation
- MVVM pattern
- Proper error handling with loading, empty, and error states
- Pagination 3 for endless scrolling
- YouTube trailer integration
- Compose UI with Material 3
- Hilt dependency injection
- Retrofit networking layer
- No hardcoded API keys
