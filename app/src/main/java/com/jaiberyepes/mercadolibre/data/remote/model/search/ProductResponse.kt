package com.jaiberyepes.mercadolibre.data.remote.model.search

import com.squareup.moshi.Json

/**
 * Data class for Product data entity (network DTO).
 *
 * @author jaiber.yepes
 */
class ProductResponse(

    @field:Json(name = "id") val id: String,
    @field:Json(name = "title") val title: String
//    @field:Json(name = "price") val price: String,
//    @field:Json(name = "currency_id") val currency_id: String

)