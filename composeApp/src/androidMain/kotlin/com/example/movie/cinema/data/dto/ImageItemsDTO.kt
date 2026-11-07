package com.example.movie.cinema.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageItemDto(
    @SerialName("filePath")
    val imagePath: String? = null,

    @SerialName("width")
    val imageWidth: Int? = null,

    @SerialName("height")
    val imageHeight: Int? = null,

    @SerialName("voteAverage")
    val averageVote: Double? = null,

    @SerialName("language")
    val languageCode: String? = null
)