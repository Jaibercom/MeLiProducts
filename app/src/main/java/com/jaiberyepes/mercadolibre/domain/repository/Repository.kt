package com.jaiberyepes.mercadolibre.domain.repository

import com.jaiberyepes.mercadolibre.presentation.model.ProductDescriptionUI
import com.jaiberyepes.mercadolibre.presentation.model.ProductDetailUI
import com.jaiberyepes.mercadolibre.presentation.model.ProductUI
import com.jaiberyepes.mercadolibre.util.Output

/**
 * Repository for Characters handling.
 *
 * @author jaiber.yepes
 */
interface Repository {

    suspend fun getProductsFromSearch(keyword: String, offset: Int): Output<List<ProductUI>>

    suspend fun getProductDetail(id: String): Output<ProductDetailUI>

    suspend fun getProductDescription(id: String): Output<ProductDescriptionUI>

}
