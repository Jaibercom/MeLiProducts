package com.jaiberyepes.mercadolibre.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Data class for Character UI entity.
 *
 * @author jaiber.yepes
 */
@Parcelize
data class ProductUI(
    val id: String,
    val title: String,
    val price: Double? = 0.0,
    val thumbnail: String?
) : Parcelable
