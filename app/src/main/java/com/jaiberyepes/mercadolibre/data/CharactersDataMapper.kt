package com.jaiberyepes.mercadolibre.data

import com.jaiberyepes.mercadolibre.data.remote.model.detail.ProductDescriptionResponse
import com.jaiberyepes.mercadolibre.data.remote.model.detail.ProductDetailResponse
import com.jaiberyepes.mercadolibre.data.remote.model.search.ProductResponse
import com.jaiberyepes.mercadolibre.presentation.model.ProductDescriptionUI
import com.jaiberyepes.mercadolibre.presentation.model.ProductDetailUI
import com.jaiberyepes.mercadolibre.presentation.model.ProductUI
import com.jaiberyepes.mercadolibre.util.BaseMapper

/**
 * Auth Mapper for mapping between data & presentation layers.
 *
 * @author jaiber.yepes
 */
object CharactersDataMapper {

    object ProductsResponseToProductUI : BaseMapper<List<ProductResponse>, List<ProductUI>> {

        override fun map(type: List<ProductResponse>): List<ProductUI> {
            return type.map {
                ProductUI(
                    id = it.id,
                    title = it.title,
                    price = it.price,
                    thumbnail = it.thumbnail,
                    imgId = it.imgId
                )
            }
        }
    }

    object ProductDetailResponseToProductDetailUI : BaseMapper<ProductDetailResponse, ProductDetailUI> {

        override fun map(type: ProductDetailResponse): ProductDetailUI {
            return with(type) {
                ProductDetailUI(
                    title = title,
                    price = price,
                    imgId = imgId
                )
            }
        }
    }

    object ProductDescriptionResponseToProductDescriptionUI : BaseMapper<ProductDescriptionResponse, ProductDescriptionUI> {

        override fun map(type: ProductDescriptionResponse): ProductDescriptionUI {
            return with(type) {
                ProductDescriptionUI(
                    description = plain_text,
                    lastUpdated = last_updated,
                    dateCreated = date_created
                )
            }
        }
    }

}
