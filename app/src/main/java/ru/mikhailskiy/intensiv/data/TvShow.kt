package ru.mikhailskiy.intensiv.data

import com.google.gson.annotations.SerializedName
import ru.mikhailskiy.intensiv.util.Utils

data class TvShow(
    @SerializedName("popularity")
    var popularity: Double?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("backdrop_path")
    var backdropPath: String?,
    @SerializedName("vote_average")
    var voteAverage: Double?,
    @SerializedName("overview")
    var overview: String?,
    @SerializedName("first_air_date")
    var firstAirDate: String?,
    @SerializedName("origin_country")
    var originCountry: List<String>?,
    @SerializedName("genre_ids")
    var genreIds: List<Int>?,
    @SerializedName("original_language")
    var originalLanguage: String?,
    @SerializedName("vote_count")
    var voteCount: Int?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("original_name")
    var originalName: String?
) {
    val rating: Float
        get() = Utils.getRating(voteAverage)

    @SerializedName("poster_path")
    var posterPath: String? = null
        get() =  Utils.getFullImagePath(field)
}