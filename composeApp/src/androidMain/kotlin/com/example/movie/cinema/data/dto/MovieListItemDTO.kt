package com.example.movie.cinema.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieListItemDto(
    @SerialName("imdbId")
    val imdbId: String,

    @SerialName("title")
    val movieTitle: String = "",

    @SerialName("year")
    val releaseYear: Int? = null,

    @SerialName("imdbRating")
    val imdbScore: Double? = null,

    @SerialName("imdbVotes")
    val imdbVotesCount: Int? = null,

    @SerialName("popularity")
    val popularityScore: Double? = null,

    @SerialName("posterPath")
    val posterImagePath: String? = null,

    @SerialName("genres")
    val genreItems: List<GenreDto> = emptyList()
)