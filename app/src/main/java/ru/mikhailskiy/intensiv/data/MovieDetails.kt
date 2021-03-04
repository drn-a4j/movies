package ru.mikhailskiy.intensiv.data

import com.google.gson.annotations.SerializedName
import ru.mikhailskiy.intensiv.util.Utils

data class MovieDetails(
    @SerializedName("adult")
    var adult: Boolean,
    @SerializedName("backdrop_path")
    var backdropPath: String?,
    @SerializedName("belongs_to_collection")
    var belongsToCollection: BelongToCollection?,
    @SerializedName("budget")
    var budget: Int,
    @SerializedName("genres")
    var genres: List<Genre>,
    @SerializedName("homepage")
    var homepage: String?,
    @SerializedName("id")
    var id: Int,
    @SerializedName("imdb_id")
    var imdbId: String?,
    @SerializedName("original_language")
    var originalLanguage: String,
    @SerializedName("original_title")
    var originalTitle: String,
    @SerializedName("overview")
    var overview: String?,
    @SerializedName("popularity")
    var popularity: Double,
    @SerializedName("production_companies")
    var productionCompany: List<ProductionCompany>,
    @SerializedName("production_countries")
    var productionCountry: List<ProductionCountry>,

    @SerializedName("revenue")
    var revenue: Int,
    @SerializedName("runtime")
    var runtime: Int?,
    @SerializedName("spoken_languages")
    var spokenLanguages: List<SpokenLanguages>,
    @SerializedName("status")
    var status: String,
    @SerializedName("tagline")
    var tagline: String?,
    @SerializedName("title")
    var title: String,
    @SerializedName("video")
    var video: Boolean,
    @SerializedName("vote_average")
    var voteAverage: Double,
    @SerializedName("vote_count")
    var vote_count: Int
) {
    @SerializedName("poster_path")
    var posterPath: String? = null
        get() = Utils.getFullImagePath(field)

    @SerializedName("release_date")
    var releaseDate: String = ""
        get() = Utils.formatDate(field)

    val rating: Float
        get() = Utils.getRating(voteAverage)
}

data class Genre(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String
)

data class ProductionCompany(
    var name: String,
    var id: Int,
    @SerializedName("logo_path")
    var logoPath: String?,
    @SerializedName("origin_country")
    var originCountry: String
)

data class SpokenLanguages(
    @SerializedName("iso_639_1")
    var iso6391: String,
    var name: String
)

data class ProductionCountry(
    @SerializedName("iso_3166_1")
    var iso31661: String,
    @SerializedName("name")
    var name: String
)

class BelongToCollection()