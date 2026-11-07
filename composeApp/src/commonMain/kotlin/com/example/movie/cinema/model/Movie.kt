package com.example.movie.cinema.model

data class Movie(
    val movieId: String,
    val movieTitle: String,
    val movieOverview: String,
    val posterUrl: String?,
    val backdropUrl: String?,
    val releaseDateYear: Int?,
    val imdbScore: Double,
    val imdbVotesCount: Int,
    val trendingScore: Double,
    val categoryIds: List<Int> = emptyList(),
    val durationMinutes: Int? = null,
    val languageCode: String? = null,
    val productionBudget: Long? = null,
    val boxOfficeRevenue: Long? = null,
    val tmdbScore: Double? = null
)