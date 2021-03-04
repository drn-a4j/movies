package ru.mikhailskiy.intensiv.util

import ru.mikhailskiy.intensiv.BuildConfig

object Utils {

    fun getFullImagePath(path: String?): String? {
        return "${BuildConfig.BASE_IMAGE_PATH}$path"
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