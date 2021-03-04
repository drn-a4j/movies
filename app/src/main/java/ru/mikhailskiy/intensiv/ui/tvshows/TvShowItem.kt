package ru.mikhailskiy.intensiv.ui.tvshows

import com.squareup.picasso.Picasso
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.tv_shows_item_with_text.*
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.data.TvShow

class TvShowItem(
    private val content: TvShow,
    private val onClick: (tvShow: TvShow) -> Unit
) : Item() {

    override fun getLayout() = R.layout.tv_shows_item_with_text

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.tv_show_title.text = content.name?.toUpperCase()
        viewHolder.tv_show_rating.rating = content.rating
        viewHolder.tv_shows_item_container.setOnClickListener {
            onClick.invoke(content)
        }

        // TODO Получать из модели
        Picasso.get()
            .load(content.posterPath)
            .into(viewHolder.tv_show_image)
    }
}