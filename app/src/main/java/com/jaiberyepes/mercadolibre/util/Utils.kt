package com.jaiberyepes.mercadolibre.util

import com.jaiberyepes.mercadolibre.presentation.model.ProductDetailUI
import com.jaiberyepes.mercadolibre.presentation.model.ProductUI
import java.text.NumberFormat


fun getCurrencyFormat(price: Double): String {
    val format: NumberFormat = NumberFormat.getCurrencyInstance()
    format.maximumFractionDigits = 0
    return format.format(price)
}

fun ProductUI.formatMeliImgUrl(): String {
    return "https://http2.mlstatic.com/D_${this.imgId}-I.jpg"
}

fun ProductDetailUI.formatMeliImgUrl(): String {
    return "https://http2.mlstatic.com/D_${this.imgId}-O.jpg"
}