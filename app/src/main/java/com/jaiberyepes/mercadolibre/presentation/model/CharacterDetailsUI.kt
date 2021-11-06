package com.jaiberyepes.mercadolibre.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Data class for Character UI entity.
 *
 * @author jaiber.yepes
 */
@Parcelize
data class CharacterDetailsUI(
    val id: Int,
    val name: String,
    val nickName: String,
    val image: String,
    var isFavorite: Boolean,
    val occupation: List<String>,
    val status: String,
    val portrayed: String
) : Parcelable
