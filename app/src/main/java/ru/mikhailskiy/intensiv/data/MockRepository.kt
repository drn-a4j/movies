package ru.mikhailskiy.intensiv.data

object MockRepository {

    fun getMovies(): List<Movie> {
        return listOf()
    }

    fun getMovieCast(id: Int): List<Cast> {
        return listOf()
    }

    fun getTvShows(): List<TvShow> {
        return listOf()
    }

}