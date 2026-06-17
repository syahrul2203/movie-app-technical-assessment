package com.syahrul.tmdbmovieapp.domain.model

data class Review(
    val id: String,
    val author: String,
    val content: String,
    val rating: Double?,
    val createdAt: String?,
)

