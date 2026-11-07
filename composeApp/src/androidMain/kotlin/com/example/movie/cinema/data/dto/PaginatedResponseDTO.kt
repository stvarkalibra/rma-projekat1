package com.example.movie.cinema.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaginatedResponseDto<T>(
    @SerialName("page")
    val pageNumber: Int = 1,

    @SerialName("pageSize")
    val itemsPerPage: Int = 20,

    @SerialName("totalItems")
    val totalCount: Int = 0,

    @SerialName("totalPages")
    val pageCount: Int = 0,

    @SerialName("items")
    val results: List<T> = emptyList()
)