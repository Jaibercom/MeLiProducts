package com.jaiberyepes.mercadolibre.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Data class for Product Detail.
 *
 * @author jaiber.yepes
 */
@Parcelize
data class ProductDetailUI(
    val title: String = "",
    val price: Double = 0.0,
    val isFavorite: Boolean = false
) : Parcelable
