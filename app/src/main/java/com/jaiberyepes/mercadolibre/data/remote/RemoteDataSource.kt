package com.jaiberyepes.mercadolibre.data.remote

import com.jaiberyepes.mercadolibre.data.CharactersDataMapper
import com.jaiberyepes.mercadolibre.data.remote.model.detail.ProductDescriptionResponse
import com.jaiberyepes.mercadolibre.data.remote.model.detail.ProductDetailResponse
import com.jaiberyepes.mercadolibre.data.remote.model.search.ProductResponse
import com.jaiberyepes.mercadolibre.util.Output
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Characters related remote DataSource.
 *
 * @author jaiber.yepes
 */
class RemoteDataSource @Inject constructor(
    private val apiService: MeLiApiService
) {

    suspend fun getProductsFromSearch(countryId: String, keywords: String, offset: Int): Output<List<ProductResponse>> =
        try {
            val searchResponse = withContext(Dispatchers.IO) {
                apiService.getProductsFromSearch(countryId, keywords, offset)
            }
            Output.success(searchResponse.results)
        } catch (e: Throwable) {
            Output.error("Error retrieving the Product list from remote: ${e.message}")
        }

    suspend fun getProductDetail(id: String): Output<ProductDetailResponse> =
        try {
            val responseDetail = withContext(Dispatchers.IO) {
                apiService.getProductDetail(id)
            }
            Output.success(responseDetail)
        } catch (e: Throwable) {
            Output.error("Error retrieving the Product Detail from remote: ${e.message}")
        }

    suspend fun getProductDescriptionResponse(id: String): Output<ProductDescriptionResponse> =
        try {
            val responseDescription = withContext(Dispatchers.IO) {
                apiService.getProductDescription(id)
            }
            Output.success(responseDescription)
        } catch (e: Throwable) {
            Output.error("Error retrieving the Product description from remote: ${e.message}")
        }
}
