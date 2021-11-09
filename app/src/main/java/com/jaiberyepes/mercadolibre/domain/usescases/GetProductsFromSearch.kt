package com.jaiberyepes.mercadolibre.domain.usescases

import com.jaiberyepes.mercadolibre.domain.repository.Repository
import com.jaiberyepes.mercadolibre.presentation.model.ProductUI
import com.jaiberyepes.mercadolibre.util.Output
import javax.inject.Inject


/**
 * UseCase for getting the [ProductUI] list.
 *
 * @author jaiber.yepes
 */
class GetProductsFromSearch @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(keywords: String, offset: Int): Output<List<ProductUI>> = repository.getProductsFromSearch(keywords, offset)
}
