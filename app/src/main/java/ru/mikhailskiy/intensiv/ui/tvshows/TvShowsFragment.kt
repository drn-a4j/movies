package ru.mikhailskiy.intensiv.ui.tvshows

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.tv_shows_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.mikhailskiy.intensiv.BuildConfig
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.data.TvShow
import ru.mikhailskiy.intensiv.data.TvShowsResponse
import ru.mikhailskiy.intensiv.network.MovieApiClient

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class TvShowsFragment : Fragment() {

    // TODO: Rename and change types of parameters
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
        return inflater.inflate(R.layout.tv_shows_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_shows_recycler_view.adapter = adapter.apply { addAll(listOf()) }

        val getPopularTvShows = MovieApiClient.apiClient.getPopularTvShows(API_KEY, "ru")

        getPopularTvShows.enqueue(object : Callback<TvShowsResponse> {
            override fun onFailure(call: Call<TvShowsResponse>, t: Throwable) {
                Log.e(TAG, t.toString())
            }

            override fun onResponse(
                call: Call<TvShowsResponse>,
                response: Response<TvShowsResponse>
            ) {
                val tvShowsList = response.body()!!.results.map {
                    TvShowsItem(it) { tvShow ->
                        openTvShowDetails(
                            tvShow
                        )
                    }
                }.toList()

                tvShowsList.forEach {
                    adapter.add(it)
                }

                tv_shows_recycler_view.adapter = adapter
            }

        })
    }

    private fun openTvShowDetails(tvShow: TvShow) {

    }

    companion object {
        private val TAG = this::class.java.simpleName
        const val API_KEY = BuildConfig.THE_MOVIE_DATABASE_API

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TvShowsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}