package com.syahrul.tmdbmovieapp.data.mapper

import com.syahrul.tmdbmovieapp.data.remote.dto.GenreDto
import com.syahrul.tmdbmovieapp.data.remote.dto.MovieDetailDto
import com.syahrul.tmdbmovieapp.data.remote.dto.MovieDto
import com.syahrul.tmdbmovieapp.data.remote.dto.ReviewDto
import com.syahrul.tmdbmovieapp.data.remote.dto.VideoDto
import com.syahrul.tmdbmovieapp.domain.model.Genre
import com.syahrul.tmdbmovieapp.domain.model.Movie
import com.syahrul.tmdbmovieapp.domain.model.MovieDetail
import com.syahrul.tmdbmovieapp.domain.model.Review
import com.syahrul.tmdbmovieapp.domain.model.Trailer

fun GenreDto.toDomain(): Genre = Genre(
    id = id,
    name = name,
)

fun MovieDto.toDomain(): Movie = Movie(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
)

fun MovieDetailDto.toDomain(): MovieDetail = MovieDetail(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    releaseDate = releaseDate,
    runtime = runtime,
    voteAverage = voteAverage,
    genres = genres.map { it.toDomain() },
)

fun ReviewDto.toDomain(): Review = Review(
    id = id,
    author = author,
    content = content,
    rating = authorDetails?.rating,
    createdAt = createdAt,
)

fun VideoDto.toDomainTrailer(): Trailer = Trailer(
    id = id,
    key = key,
    name = name,
    site = site,
    type = type,
)

