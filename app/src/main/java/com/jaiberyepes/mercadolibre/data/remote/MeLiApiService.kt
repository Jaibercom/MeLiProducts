package com.jaiberyepes.mercadolibre.data.remote

import com.jaiberyepes.mercadolibre.data.remote.model.detail.ProductDescriptionResponse
import com.jaiberyepes.mercadolibre.data.remote.model.detail.ProductDetailResponse
import com.jaiberyepes.mercadolibre.data.remote.model.search.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interface that provides the Breaking Bad API End-Points.
 *
 * @author jaiber.yepes
 */
interface MeLiApiService {

    @GET("sites/{countryId}/search")
    suspend fun getProductsFromSearch(
        @Path("countryId") country: String,
        @Query("q") word: String,
        @Query("offset") offset: Int = 0
    ): SearchResponse

    @GET("items/{id}")
    suspend fun getProductDetail(@Path("id") id: String): ProductDetailResponse

    @GET("items/{id}/description")
    suspend fun getProductDescription(@Path("id") id: String): ProductDescriptionResponse

}
