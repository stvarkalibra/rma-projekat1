package com.example.movie.cinema.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImagesDto(
    @SerialName("backdrops")
    val backdropItems: List<ImageItemDto> = emptyList(),

    @SerialName("posters")
    val posterItems: List<ImageItemDto> = emptyList(),

    @SerialName("logos")
    val logoItems: List<ImageItemDto> = emptyList()
)