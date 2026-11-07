package com.example.movie.cinema.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsDto(
    @SerialName("imdbId")
    val imdbId: String,

    @SerialName("tmdbId")
    val tmdbId: Int? = null,

    @SerialName("title")
    val movieTitle: String = "",

    @SerialName("originalTitle")
    val originalMovieTitle: String? = null,

    @SerialName("overview")
    val movieOverview: String? = null,

    @SerialName("tagline")
    val movieTagline: String? = null,

    @SerialName("releaseDate")
    val releaseDateText: String? = null,

    @SerialName("year")
    val releaseYear: Int? = null,

    @SerialName("runtime")
    val runtimeMinutes: Int? = null,

    @SerialName("budget")
    val budgetAmount: Long? = null,

    @SerialName("revenue")
    val revenueAmount: Long? = null,

    @SerialName("languageCode")
    val originalLanguageCode: String? = null,

    @SerialName("popularity")
    val popularityScore: Double? = null,

    @SerialName("imdbRating")
    val imdbScore: Double? = null,

    @SerialName("imdbVotes")
    val imdbVotesCount: Int? = null,

    @SerialName("tmdbRating")
    val tmdbScore: Double? = null,

    @SerialName("tmdbVotes")
    val tmdbVotesCount: Int? = null,

    @SerialName("posterPath")
    val posterImagePath: String? = null,

    @SerialName("backdropPath")
    val backdropImagePath: String? = null,

    @SerialName("homepage")
    val homepageUrl: String? = null,

    @SerialName("genres")
    val genreItems: List<GenreDto> = emptyList()
)