package com.example.movie.app.common

data class BrowseFilters(
    val searchText: String = "",
    val pickedGenreId: Int? = null,
    val yearFrom: Int? = null,
    val yearTo: Int? = null,
    val ratingFrom: Double? = null
) {
    fun hasActiveFilters(): Boolean {
        return searchText.isNotBlank() ||
                pickedGenreId != null ||
                yearFrom != null    ||
                yearTo != null ||
                ratingFrom != null
    }

    fun activeFiltersCount(): Int {
        var activeCount = 0

        if (searchText.isNotBlank()) activeCount++
        if (pickedGenreId != null) activeCount++
        if (yearFrom != null || yearTo != null) activeCount++
        if (ratingFrom != null) activeCount++

        return activeCount
    }
}