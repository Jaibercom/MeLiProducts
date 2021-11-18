package com.jaiberyepes.mercadolibre.data.remote.model.detail

import com.squareup.moshi.Json

/**
 * Data class for Product data entity (network DTO).
 *
 * @author jaiber.yepes
 */
class ProductDetailResponse(

    @field:Json(name = "id") val id: String,
    @field:Json(name = "title") val title: String,
    @field:Json(name = "price") val price: Double,
    @field:Json(name = "thumbnail") val thumbnail: String,
    @field:Json(name = "thumbnail_id") val imgId: String
//    @field:Json(name = "currency_id") val currency_id: String

)