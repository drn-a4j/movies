package ru.mikhailskiy.intensiv.data

import com.google.gson.annotations.SerializedName
import ru.mikhailskiy.intensiv.util.Utils

data class Cast(
    @SerializedName("adult")
    var adult:Boolean,
    @SerializedName("gender")
    var gender:Int?,
    @SerializedName("id")
    var id:Int,
    @SerializedName("known_for_department")
    var knownForDepartment:String,
    @SerializedName("name")
    var name:String,
    @SerializedName("original_name")
    var originalName:String,
    @SerializedName("popularity")
    var popularity:Double,
    @SerializedName("cast_id")
    var castId:Int,
    @SerializedName("character")
    var character:String,
    @SerializedName("credit_id")
    var creditId:String,
    @SerializedName("order")
    var order:Int
){
    @SerializedName("profile_path")
    var profilePath: String? = null
        get() =  Utils.getFullImagePath(field)
}