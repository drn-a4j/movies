package ru.mikhailskiy.intensiv.data

object MockRepository {

    fun getMovies(): List<Movie> {

        val moviesList = mutableListOf<Movie>()
        for (x in 0..10) {
            val movie = Movie(
                title = "Spider-Man $x",
                voteAverage = 10.0 - x,
                description = "In 1985 Maine, lighthouse keeper Thomas Curry rescues Atlanna, the queen of the underwater nation of Atlantis, during a storm. They eventually fall in love and have a son named Arthur, who is born with the power to communicate with marine lifeforms.",
                studio = "Warner Bros",
                genre = "Action, Adventure, Fantasy",
                year = 2021 - x
            )
            moviesList.add(movie)
        }

        return moviesList
    }

    fun getMovieCast(id: Int): List<Cast> {

        val castList = mutableListOf<Cast>()
        for (x in 0..10) {
            val actor = Cast(
                firstName = "First name $x",
                lastName = "Last name $x"
            )
            castList.add(actor)
        }

        return castList
    }

    fun getTvShows(): List<TvShow> {
        val tvShowsList = mutableListOf<TvShow>()
        for (x in 0..10) {
            val tvShow = TvShow(
                title = "Spider-Man $x",
                voteAverage = 10.0 - x
            )
            tvShowsList.add(tvShow)
        }

        return tvShowsList
    }

}