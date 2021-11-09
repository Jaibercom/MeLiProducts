package com.jaiberyepes.mercadolibre.util

import java.text.NumberFormat


fun getCurrencyFormat(price: Double): String {
    val format: NumberFormat = NumberFormat.getCurrencyInstance()
    format.maximumFractionDigits = 0
    return format.format(price)
}