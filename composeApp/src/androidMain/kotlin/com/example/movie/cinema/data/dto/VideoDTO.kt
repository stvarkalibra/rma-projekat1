package com.example.movie.cinema.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoDto(
    @SerialName("key")
    val videoKey: String = "",

    @SerialName("site")
    val platformName: String = "",

    @SerialName("name")
    val videoName: String? = null,

    @SerialName("type")
    val videoType: String? = null,

    @SerialName("official")
    val isOfficial: Boolean = false,

    @SerialName("publishedAt")
    val publishedAtText: String? = null
)