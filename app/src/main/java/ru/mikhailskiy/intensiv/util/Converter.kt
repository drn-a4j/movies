package ru.mikhailskiy.intensiv.util

import ru.mikhailskiy.intensiv.data.Movie
import ru.mikhailskiy.intensiv.data.TvShow
import ru.mikhailskiy.intensiv.ui.feed.MovieItem
import ru.mikhailskiy.intensiv.ui.tvshows.TvShowItem

object Converter {

    fun convertToMovieItem(
        content: List<Movie>,
        openMovieDetails: (movie: Movie) -> Unit
    ): List<MovieItem> {
        return content.map {
            MovieItem(it) { movie ->
                openMovieDetails.invoke(movie)
            }
        }.toList()
    }

    fun convertToTvShowItem(
        content: List<TvShow>,
        openTvShowDetails: (tvShow: TvShow) -> Unit
    ): List<TvShowItem> {
        return content.map {
            TvShowItem(it) { tvShow ->
                openTvShowDetails.invoke(tvShow)
            }
        }.toList()
    }
}