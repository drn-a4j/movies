package ru.mikhailskiy.intensiv.ui.movie_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.movie_details_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.data.Genre
import ru.mikhailskiy.intensiv.data.MovieCredit
import ru.mikhailskiy.intensiv.data.MovieDetails
import ru.mikhailskiy.intensiv.data.ProductionCompany
import ru.mikhailskiy.intensiv.network.MovieApiClient
import timber.log.Timber


class MovieDetailsFragment : Fragment() {

    private var id: Int? = null

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt(ARG_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.movie_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val getMovieDetails = id?.let { MovieApiClient.apiClient.getMovieDetails(it) }
        getMovieDetails?.enqueue(object : Callback<MovieDetails> {
            override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
                Timber.d(t.toString())
            }

            override fun onResponse(call: Call<MovieDetails>, response: Response<MovieDetails>) {
                Picasso.get()
                    .load(response.body()?.posterPath)
                    .into(movie_details_image)

                response.body()?.let {
                    movie_details_title.text = it.title
                    movie_details_rating.rating = it.rating
                    movie_details_description.text = it.overview
                    movie_details_year_value.text = it.releaseDate
                }

                val genreString = StringBuilder()
                val genreIterator =
                    response.body()?.genres?.iterator() ?: emptyList<Genre>().iterator()

                while (genreIterator.hasNext()) {
                    genreString.append(genreIterator.next().name.capitalize())

                    if (genreIterator.hasNext()) {
                        genreString.append(", ")
                    }
                }

                movie_details_genre_value.text = genreString

                val productionString = StringBuilder()
                val productionIterator = response.body()?.productionCompany?.iterator()
                    ?: emptyList<ProductionCompany>().iterator()

                while (productionIterator.hasNext()) {
                    productionString.append(productionIterator.next().name)

                    if (productionIterator.hasNext()) {
                        productionString.append(", ")
                    }
                }

                movie_details_studio_value.text = productionString
            }
        })

        movie_details_cast_recycler_view.adapter = adapter.apply { addAll(listOf()) }

        val getMovieCredits = id?.let { MovieApiClient.apiClient.getMovieCredits(it) }
        getMovieCredits?.enqueue(object : Callback<MovieCredit> {
            override fun onFailure(call: Call<MovieCredit>, t: Throwable) {
                Timber.d(t.toString())
            }

            override fun onResponse(call: Call<MovieCredit>, response: Response<MovieCredit>) {
                val castList = response.body()?.cast?.map {
                    MovieDetailsCastItem(it)
                }?.toList()

                castList?.forEach {
                    adapter.add(it)
                }

                movie_details_cast_recycler_view.adapter = adapter
            }
        })

        movie_details_back_button.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    companion object {
        private const val ARG_ID = "id"
    }
}