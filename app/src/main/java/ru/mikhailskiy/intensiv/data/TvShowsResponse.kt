package ru.mikhailskiy.intensiv.data

import com.google.gson.annotations.SerializedName

class TvShowsResponse(
    var page:Int,
    var results: List<TvShow>,
    @SerializedName("total_result")
    var totalResult:Int,
    @SerializedName("total_pages")
    var totalPages:Int
)