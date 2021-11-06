package com.jaiberyepes.mercadolibre.data.remote.model.search

/**
 * Data class for Paging data entity (network DTO).
 *
 * @author jaiber.yepes
 */
data class PagingResponse(
    val total: Int,
    val offset: Int,
    val limit: Int
)
