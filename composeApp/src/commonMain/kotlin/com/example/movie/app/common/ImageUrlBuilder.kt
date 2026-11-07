package com.example.movie.app.common

object ImageUrlBuilder {

    private const val POSTER_SIZE = "w500"
    private const val BACKDROP_SIZE = "w780"
    private const val PROFILE_SIZE = "w185"
    private const val FULL_SIZE = "original"

    fun buildPosterUrl(
        imageHost: String,
        imagePath: String?,
        imageSize: String = POSTER_SIZE
    ): String? {
        return buildUrl(
            imageHost = imageHost,
            imagePath = imagePath,
            imageSize = imageSize
        )
    }

    fun buildBackdropUrl(
        imageHost: String,
        imagePath: String?,
        imageSize: String = BACKDROP_SIZE
    ): String? {
        return buildUrl(
            imageHost = imageHost,
            imagePath = imagePath,
            imageSize = imageSize
        )
    }

    fun buildProfileUrl(
        imageHost: String,
        imagePath: String?,
        imageSize: String = PROFILE_SIZE
    ): String? {
        return buildUrl(
            imageHost = imageHost,
            imagePath = imagePath,
            imageSize = imageSize
        )
    }

    fun buildOriginalUrl(
        imageHost: String,
        imagePath: String?
    ): String? {
        return buildUrl(
            imageHost = imageHost,
            imagePath = imagePath,
            imageSize = FULL_SIZE
        )
    }

    private fun buildUrl(
        imageHost: String,
        imagePath: String?,
        imageSize: String
    ): String? {
        if (imagePath.isNullOrBlank()) return null

        val preparedBase = if (imageHost.endsWith("/")) {
            imageHost
        } else {
            "$imageHost/"
        }

        val preparedPath = imagePath.removePrefix("/")

        return "$preparedBase$imageSize/$preparedPath"
    }
}