package com.jaiberyepes.mercadolibre.data.remote

import com.jaiberyepes.mercadolibre.data.CharactersDataMapper
import com.jaiberyepes.mercadolibre.data.remote.model.CharacterResponse
import com.jaiberyepes.mercadolibre.data.remote.model.search.ProductResponse
import com.jaiberyepes.mercadolibre.presentation.model.CharacterDetailsUI
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

    suspend fun getCharacters(): Output<List<CharacterResponse>> =
        try {
            val charactersResponse = withContext(Dispatchers.IO) {
                apiService.getCharacters()
            }

//            val characters = CharactersDataMapper.CharactersListRemoteToUI.map(charactersResponse)
            Output.success(charactersResponse)
        } catch (e: Throwable) {
            Output.error("Error retrieving the Characters list from remote: ${e.message}")
        }

    suspend fun getCharacterDetails(id: Int): Output<CharacterDetailsUI> =
        try {
            val characterResponse = withContext(Dispatchers.IO) {
                apiService.getCharacterDetails(id)
            }

            val characters = CharactersDataMapper.CharacterDetailsListRemoteToUI.map(characterResponse)
            Output.success(characters)
        } catch (e: Throwable) {
            Output.error("Error retrieving the Characters list from remote: ${e.message}")
        }
}