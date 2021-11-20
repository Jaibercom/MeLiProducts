package com.jaiberyepes.mercadolibre.domain.usescases

import com.jaiberyepes.mercadolibre.domain.repository.Repository
import com.jaiberyepes.mercadolibre.presentation.model.ProductDescriptionUI
import com.jaiberyepes.mercadolibre.util.Output
import javax.inject.Inject


/**
 * UseCase for getting the [ProductDescriptionUI] list.
 *
 * @author jaiber.yepes
 */
class GetProductDescription @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(id: String): Output<ProductDescriptionUI> = repository.getProductDescription(id)
}
