package ru.mikhailskiy.intensiv.ui.feed

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.feed_fragment.*
import kotlinx.android.synthetic.main.feed_header.*
import kotlinx.android.synthetic.main.search_toolbar.view.*
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.data.Movie
import ru.mikhailskiy.intensiv.data.MoviesResponse
import ru.mikhailskiy.intensiv.network.MovieApiClient
import ru.mikhailskiy.intensiv.ui.afterTextChanged
import ru.mikhailskiy.intensiv.util.Converter
import timber.log.Timber

class FeedFragment : Fragment() {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.feed_fragment, container, false)
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Добавляем recyclerView
        movies_recycler_view.layoutManager = LinearLayoutManager(context)
        movies_recycler_view.adapter = adapter.apply { addAll(listOf()) }

        search_toolbar.search_edit_text.afterTextChanged {
            Timber.d(it.toString())
            if (it.toString().length > 3) {
                openSearch(it.toString())
            }
        }

        val nowPlayingTitle = R.string.recommended
        val getNowPlayingMovies = MovieApiClient.apiClient.getNowPlayingMovies()

        val upcomingTitle = R.string.upcoming
        val getUpcomingMovies = MovieApiClient.apiClient.getUpcomingMovies()

        val popularTitle = R.string.popular
        val getPopularMovies = MovieApiClient.apiClient.getPopularMovies()

        Observable.zip(
            getNowPlayingMovies,
            getUpcomingMovies,
            getPopularMovies,
            Function3<MoviesResponse, MoviesResponse, MoviesResponse, List<Pair<Int, MoviesResponse>>> { now, upcoming, popular ->
                return@Function3 listOf(
                    Pair(nowPlayingTitle, now),
                    Pair(upcomingTitle, upcoming),
                    Pair(popularTitle, popular)
                )
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { feedProgressBar.visibility = View.VISIBLE }
            .subscribe({
                it.forEach {
                    val moviesList = listOf(
                        MainCardContainer(
                            it.first,
                            Converter.convertToMovieItem(it.second.results) { movie ->
                                openMovieDetails(movie)
                            }
                        )
                    )
                    adapter.apply { addAll(moviesList) }
                    feedProgressBar.visibility = View.GONE
                }
            }, {
                Timber.e(it.toString())
            })
    }

    private fun openMovieDetails(movie: Movie) {
        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }

        val bundle = Bundle()
        movie.id?.let { bundle.putInt(ARG_ID, it) }

        findNavController().navigate(R.id.movie_details_fragment, bundle, options)
    }

    private fun openSearch(searchText: String) {
        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }

        val bundle = Bundle()
        bundle.putString("search", searchText)
        findNavController().navigate(R.id.search_dest, bundle, options)
    }

    override fun onStop() {
        super.onStop()
        search_toolbar.clear()
        adapter.clear()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    companion object {
        const val ARG_ID = "id"
    }
}