package ru.mikhailskiy.intensiv.ui.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.feed_header.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.search_toolbar.view.*
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.network.MovieApiClient
import ru.mikhailskiy.intensiv.ui.afterTextChanged
import ru.mikhailskiy.intensiv.ui.feed.MainCardContainer
import ru.mikhailskiy.intensiv.util.Converter
import timber.log.Timber
import java.util.concurrent.TimeUnit

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SearchFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchTerm = requireArguments().getString("search")

        search_toolbar.setText(searchTerm)

        movies_recycler_view.layoutManager = LinearLayoutManager(context)
        movies_recycler_view.adapter = adapter.apply { addAll(listOf()) }

        getSearchText()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.trim() }
            .filter { it.length > 3 }
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribe({
                val searchMovieResult = MovieApiClient.apiClient.searchByQuery(query = it)
                searchMovieResult
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext { searchProgressBar.visibility = View.VISIBLE }
                    .subscribe {
                        adapter.clear()
                        if (it.totalResults > 0) {
                            val moviesList = listOf(
                                MainCardContainer(
                                    R.string.movies,
                                    Converter.convertToMovieItem(it.results) { movie ->
                                    }
                                )
                            )
                            adapter.apply { addAll(moviesList) }
                            searchProgressBar.visibility = View.GONE
                        }
                    }

                val searchTvShowsResult = MovieApiClient.apiClient.searchTvShowsByQuery(query = it)
                searchTvShowsResult
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext { searchProgressBar.visibility = View.VISIBLE }
                    .subscribe {
                        val tvShowsList = listOf(
                            MainCardContainer(
                                R.string.tv_shows,
                                it.let {
                                    Converter.convertToTvShowItem(
                                        it.results,
                                        R.layout.tv_shows_item_search
                                    ) { tvShow ->
                                    }
                                }
                            )
                        )
                        adapter.apply {
                            addAll(tvShowsList)
                        }
                        searchProgressBar.visibility = View.GONE
                    }
            }, {
                Timber.e(it)
            })
    }

    private fun getSearchText(): Observable<String> {
        return Observable.create { emitter ->
            search_toolbar.search_edit_text.afterTextChanged {
                emitter.onNext(it.toString())
            }
            emitter.setCancellable {
                emitter.onComplete()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        adapter.clear()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}