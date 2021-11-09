package com.jaiberyepes.mercadolibre.presentation.model

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jaiberyepes.mercadolibre.R
import com.jaiberyepes.mercadolibre.presentation.adapter.CharactersController
import com.jaiberyepes.mercadolibre.util.extensions.setRoundCorners

/**
 * EpoxyModelClass for the Character list.
 *
 * @author jaiber.yepes
 */
@EpoxyModelClass(layout = R.layout.item_character)
abstract class CharacterModel : EpoxyModelWithHolder<CharacterModel.CharactersHolder>() {

    @EpoxyAttribute
    lateinit var characterUI: CharacterUI

    @EpoxyAttribute
    lateinit var characterClickedListener: CharactersController.CharacterClickedListener

    override fun bind(holder: CharactersHolder) = with(holder) {
        nameView.text = characterUI.name
        nickNameView.text = characterUI.nickName

        Glide.with(imageView)
            .load(characterUI.image)
            .apply(RequestOptions().placeholder(R.color.grayLight))
            .into(holder.imageView)
        imageView.setRoundCorners(R.dimen.margin_x_small)

        if (characterUI.isFavorite) {
            favoriteView.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            favoriteView.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }

        container.setOnClickListener {
            characterClickedListener.onCharacterClicked(characterUI)
        }
    }

    inner class CharactersHolder : EpoxyHolder() {
        lateinit var nameView: TextView
        lateinit var nickNameView: TextView
        lateinit var imageView: ImageView
        lateinit var favoriteView: ImageView
        lateinit var container: View

        override fun bindView(itemView: View) {
            nameView = itemView.findViewById(R.id.item_title)
            nickNameView = itemView.findViewById(R.id.item_price)
            imageView = itemView.findViewById(R.id.item_image)
            favoriteView = itemView.findViewById(R.id.item_favorite)
            container = itemView
        }
    }
}
