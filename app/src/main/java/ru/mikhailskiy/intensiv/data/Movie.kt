package ru.mikhailskiy.intensiv.data

data class Movie(
    var title: String? = "",
    var voteAverage: Double = 0.0,
    var description: String? = "",
    var studio: String = "",
    var genre: String = "",
    var year: Int = 0
) {
    val rating: Float
        get() = voteAverage.div(2).toFloat()
}
