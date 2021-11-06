package com.jaiberyepes.mercadolibre.data.remote.model.search

import com.squareup.moshi.Json

/**
 * Data class for Search data entity (network DTO).
 *
 * @author jaiber.yepes
 */
data class SearchResponse(
    val paging : PagingResponse? = null,
    @Json(name = "results") val productList : List<ProductResponse> = emptyList()
)
