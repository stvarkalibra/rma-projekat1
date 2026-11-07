package com.example.movie.cinema.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreDto(
    @SerialName("id")
    val genreId: Int,

    @SerialName("name")
    val genreLabel: String = ""
)