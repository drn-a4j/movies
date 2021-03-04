package ru.mikhailskiy.intensiv.data

import com.google.gson.annotations.SerializedName
import ru.mikhailskiy.intensiv.util.Utils

data class Movie(
    @SerializedName("adult")
    var adult: Boolean?,
    @SerializedName("overview")
    var ovarview: String?,
    @SerializedName("release_date")
    var releaseDate: String?,
    @SerializedName("genre_ids")
    var genreIds: List<Int>?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("original_title")
    var originalTitle: String?,
    @SerializedName("original_language")
    var originalLanguage: String?,
    @SerializedName("title")
    var title: String?,
    @SerializedName("backdrop_path")
    var backdropPath: String?,
    @SerializedName("popularity")
    var popularity: Double?,
    @SerializedName("vote_count")
    var voteCount: Int?,
    @SerializedName("video")
    var video: Boolean?,
    @SerializedName("vote_average")
    var voteAverage: Double?
) {
    @SerializedName("poster_path")
    var posterPath: String? = null
        get() = Utils.getFullImagePath(field)

    val rating: Float
        get() = Utils.getRating(voteAverage)
}
