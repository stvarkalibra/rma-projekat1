package com.example.movie.cinema.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfigDto(
    @SerialName("key")
    val configKey: String = "",

    @SerialName("value")
    val configValue: String = ""
)