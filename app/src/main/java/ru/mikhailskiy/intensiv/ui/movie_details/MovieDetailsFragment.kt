package ru.mikhailskiy.intensiv.ui.movie_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.movie_details_fragment.*
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.data.MockRepository

private const val ARG_TITLE = "title"
private const val ARG_RATING = "rating"
private const val ARG_DESCRIPTION = "description"
private const val ARG_STUDIO = "studio"
private const val ARG_GENRE = "genre"
private const val ARG_YEAR = "year"

class MovieDetailsFragment : Fragment() {

    private var title: String? = null
    private var rating: Float? = null
    private var description: String? = null
    private var studio: String? = null
    private var genre: String? = null
    private var year: Int? = null


    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(ARG_TITLE)
            rating = it.getFloat(ARG_RATING)
            description = it.getString(ARG_DESCRIPTION)
            studio = it.getString(ARG_STUDIO)
            genre = it.getString(ARG_GENRE)
            year = it.getInt(ARG_YEAR)
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

        movie_details_title.text = title
        movie_details_rating.rating = rating ?: 0.0f
        movie_details_description.text = description
        movie_details_studio_value.text = studio
        movie_details_genre_value.text = genre
        movie_details_year_value.text = year.toString()

        movie_details_cast_recycler_view.adapter = adapter.apply { addAll(listOf()) }

        val castList = MockRepository.getMovieCast(1).map {
            MovieDetailsCastItem(it)
        }.toList()

        castList.forEach {
            adapter.add(it)
        }

        movie_details_cast_recycler_view.adapter = adapter

        movie_details_back_button.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(
            title: String,
            rating: Float,
            description: String,
            studio: String,
            genre: String,
            year: Int
        ) =
            MovieDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putFloat(ARG_RATING, rating)
                    putString(ARG_DESCRIPTION, description)
                    putString(ARG_STUDIO, studio)
                    putString(ARG_GENRE, genre)
                    putInt(ARG_YEAR, year)
                }
            }
    }
}