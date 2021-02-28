package ru.mikhailskiy.intensiv.ui.movie_details

import com.squareup.picasso.Picasso
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.movie_details_item_with_text.*
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.data.Cast

class MovieDetailsCastItem(
    private val content: Cast
) : Item() {

    override fun getLayout() = R.layout.movie_details_item_with_text

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.movie_details_item_name.text = content.name

        // TODO Получать из модели
        Picasso.get()
            .load(content.profilePath)
            .into(viewHolder.movie_details_item_image)
    }
}