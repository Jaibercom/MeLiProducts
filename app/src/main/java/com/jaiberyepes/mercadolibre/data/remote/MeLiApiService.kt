package com.jaiberyepes.mercadolibre.data.remote

import com.jaiberyepes.mercadolibre.data.remote.model.CharacterDetailResponse
import com.jaiberyepes.mercadolibre.data.remote.model.CharacterResponse
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

    @GET("characters")
    suspend fun getCharacters(): List<CharacterResponse>

    @GET("characters/{id}")
    suspend fun getCharacterDetails(@Path("id") id: Int): List<CharacterDetailResponse>

    @GET("sites/{countryId}/search")
    suspend fun getProductsFromSearch(
        @Path("countryId") country: String,
        @Query("q") word: String,
        @Query("offset") offset: Int = 0
    ): SearchResponse

}
