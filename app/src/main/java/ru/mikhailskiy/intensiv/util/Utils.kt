package ru.mikhailskiy.intensiv.util

object Utils {
    private const val BASE_IMAGE_PATH = "https://image.tmdb.org/t/p/w500"

    fun getFullImagePath(path: String?): String? {
        return "$BASE_IMAGE_PATH$path"
    }

    fun getRating(voteAverage: Double?): Float {
        return voteAverage?.div(2)?.toFloat() ?: 0.0f
    }

    fun formatDate(releaseDate: String): String {
        if (releaseDate.length >= 4) {
            return releaseDate.substring(0, 4)
        }
        return ""
    }
}