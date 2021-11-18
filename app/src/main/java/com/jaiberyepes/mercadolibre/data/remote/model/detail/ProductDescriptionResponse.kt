package com.jaiberyepes.mercadolibre.data.remote.model.detail

import com.squareup.moshi.Json

data class ProductDescriptionResponse(
    @Json(name = "plain_text") val description: String = "",
    val last_updated: String = "",
    val date_created: String = ""
)
