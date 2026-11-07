package com.example.movie.cinema.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonDto(
    @SerialName("imdbId")
    val imdbId: String,

    @SerialName("name")
    val fullName: String = "",

    @SerialName("professions")
    val professionText: String? = null,

    @SerialName("department")
    val departmentName: String? = null,

    @SerialName("profilePath")
    val profileImagePath: String? = null
)