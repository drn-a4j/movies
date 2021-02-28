package ru.mikhailskiy.intensiv.data

import com.google.gson.annotations.SerializedName

data class Crew(
    @SerializedName("adult")
    var adult: Boolean,
    @SerializedName("gender")
    var gender: Int?,
    @SerializedName("id")
    var id: Int,
    @SerializedName("known_for_department")
    var knownForDepartment: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("original_name")
    var originalName: String,
    @SerializedName("popularity")
    var popularity: Double,
    @SerializedName("profile_path")
    var profilePath: String?,
    @SerializedName("credit_id")
    var creditId: String,
    @SerializedName("department")
    var department: String,
    @SerializedName("job")
    var job: String
)
