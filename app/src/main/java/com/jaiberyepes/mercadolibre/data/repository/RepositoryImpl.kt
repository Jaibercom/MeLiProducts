package com.jaiberyepes.mercadolibre.data.repository

import com.jaiberyepes.mercadolibre.data.CharactersDataMapper
import com.jaiberyepes.mercadolibre.data.cache.CacheDataSource
import com.jaiberyepes.mercadolibre.data.remote.RemoteDataSource
import com.jaiberyepes.mercadolibre.domain.repository.Repository
import com.jaiberyepes.mercadolibre.presentation.model.ProductDescriptionUI
import com.jaiberyepes.mercadolibre.presentation.model.ProductDetailUI
import com.jaiberyepes.mercadolibre.presentation.model.ProductUI
import com.jaiberyepes.mercadolibre.util.Output
import timber.log.Timber
import javax.inject.Inject

/**
 * Characters related Repository implementation.
 *
 * @author jaiber.yepes
 */
class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val cacheDataSource: CacheDataSource
) : Repository {

    override suspend fun getProductsFromSearch(keyword: String, offset: Int): Output<List<ProductUI>> {
        Timber.d("getProductsFromSearch")
        val productsRemote = remoteDataSource.getProductsFromSearch("MCO", keyword, offset)

        return if (productsRemote is Output.Success) {
            Output.success(CharactersDataMapper.ProductsResponseToProductUI.map(productsRemote.data))
        } else {
            Output.success(emptyList())
        }
    }

    override suspend fun getProductDetail(id: String): Output<ProductDetailUI> {
        Timber.d("getProductDetail")
        val productDetail = remoteDataSource.getProductDetail(id)

        return if (productDetail is Output.Success) {
            Output.success(CharactersDataMapper.ProductDetailResponseToProductDetailUI.map(productDetail.data))
        } else {
            Output.success(ProductDetailUI())
        }
    }

    override suspend fun getProductDescription(id: String): Output<ProductDescriptionUI> {
        Timber.d("getProductDetail")
        val productDescription = remoteDataSource.getProductDescriptionResponse(id)

        return if (productDescription is Output.Success) {
            Output.success(CharactersDataMapper.ProductDescriptionResponseToProductDescriptionUI.map(productDescription.data))
        } else {
            Output.success(ProductDescriptionUI())
        }
    }
}
