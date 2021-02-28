package ru.mikhailskiy.intensiv.data

import com.google.gson.annotations.SerializedName

data class MovieCredit(
    @SerializedName("id")
    var id:Int,
    @SerializedName("cast")
    var cast:List<Cast>,
    @SerializedName("crew")
    var crew:List<Crew>
)