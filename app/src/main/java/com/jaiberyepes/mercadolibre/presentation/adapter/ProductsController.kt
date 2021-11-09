package com.jaiberyepes.mercadolibre.presentation.adapter

import com.airbnb.epoxy.TypedEpoxyController
import com.jaiberyepes.mercadolibre.presentation.model.ProductUI
import com.jaiberyepes.mercadolibre.presentation.model.product

/**
 * Epoxy Controller Class for the Characters list.
 *
 * @author jaiber.yepes
 */
class ProductsController(
    private val listener: ClickedListener
) : TypedEpoxyController<List<ProductUI>>() {

    override fun buildModels(products: List<ProductUI>) {
        products.forEach {
            product {
                id(it.id)
                productUI(it)
                clickedListener(listener)
            }
        }
    }
    
    interface ClickedListener {
        fun onProductClicked(productUI: ProductUI)
    }
}
