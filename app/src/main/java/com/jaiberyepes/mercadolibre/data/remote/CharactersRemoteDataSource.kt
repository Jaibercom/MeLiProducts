package com.jaiberyepes.mercadolibre.data.remote

import com.jaiberyepes.mercadolibre.data.CharactersDataMapper
import com.jaiberyepes.mercadolibre.data.remote.model.CharacterResponse
import com.jaiberyepes.mercadolibre.presentation.model.CharacterDetailsUI
import com.jaiberyepes.mercadolibre.util.Output
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Characters related remote DataSource.
 *
 * @author jaiber.yepes
 */
class CharactersRemoteDataSource @Inject constructor(
    private val breakingBadApi: MeLiApiService
) {

    suspend fun getCharacters(): Output<List<CharacterResponse>> =
        try {
            val charactersResponse = withContext(Dispatchers.IO) {
                breakingBadApi.getCharacters()
            }

//            val characters = CharactersDataMapper.CharactersListRemoteToUI.map(charactersResponse)
            Output.success(charactersResponse)
        } catch (e: Throwable) {
            Output.error("Error retrieving the Characters list from remote: ${e.message}")
        }

    suspend fun getCharacterDetails(id: Int): Output<CharacterDetailsUI> =
        try {
            val characterResponse = withContext(Dispatchers.IO) {
                breakingBadApi.getCharacterDetails(id)
            }

            val characters = CharactersDataMapper.CharacterDetailsListRemoteToUI.map(characterResponse)
            Output.success(characters)
        } catch (e: Throwable) {
            Output.error("Error retrieving the Characters list from remote: ${e.message}")
        }
}
