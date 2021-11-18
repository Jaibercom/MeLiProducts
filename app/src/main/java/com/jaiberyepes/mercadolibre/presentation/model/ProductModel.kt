package com.jaiberyepes.mercadolibre.presentation.model

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.jaiberyepes.mercadolibre.R
import com.jaiberyepes.mercadolibre.presentation.adapter.ProductsController
import com.jaiberyepes.mercadolibre.util.extensions.setRoundCorners
import com.jaiberyepes.mercadolibre.util.formatMeliImgUrl
import com.jaiberyepes.mercadolibre.util.getCurrencyFormat

/**
 * EpoxyModelClass for the Character list.
 *
 * @author jaiber.yepes
 */
@EpoxyModelClass(layout = R.layout.item_product)
abstract class ProductModel : EpoxyModelWithHolder<ProductModel.CharactersHolder>() {

    @EpoxyAttribute
    lateinit var productUI: ProductUI

    @EpoxyAttribute
    lateinit var clickedListener: ProductsController.ClickedListener

    override fun bind(holder: CharactersHolder) = with(holder) {
        titleView.text = productUI.title.trimStart()
        priceView.text = getCurrencyFormat(productUI.price)

        Glide.with(imageView)
            .load(productUI.formatMeliImgUrl())
//            .apply(RequestOptions().placeholder(R.color.grayLight))
            .into(holder.imageView)
        imageView.setRoundCorners(R.dimen.margin_x_small)

//        if (productUI.isFavorite) {
//            favoriteView.setImageResource(R.drawable.ic_baseline_favorite_24)
//        } else {
//            favoriteView.setImageResource(R.drawable.ic_baseline_favorite_border_24)
//        }

        container.setOnClickListener {
            clickedListener.onProductClicked(productUI)
        }
    }

    inner class CharactersHolder : EpoxyHolder() {
        lateinit var titleView: TextView
        lateinit var priceView: TextView
        lateinit var imageView: ImageView
        lateinit var favoriteView: ImageView
        lateinit var container: View

        override fun bindView(itemView: View) {
            imageView = itemView.findViewById(R.id.item_image)
            titleView = itemView.findViewById(R.id.item_title)
            priceView = itemView.findViewById(R.id.item_price)
//            favoriteView = itemView.findViewById(R.id.item_favorite)
            container = itemView
        }
    }
}
