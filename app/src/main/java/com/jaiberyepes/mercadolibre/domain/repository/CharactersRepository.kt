package com.jaiberyepes.mercadolibre.domain.repository

import com.jaiberyepes.mercadolibre.presentation.model.CharacterDetailsUI
import com.jaiberyepes.mercadolibre.presentation.model.CharacterUI
import com.jaiberyepes.mercadolibre.util.Output

/**
 * Repository for Characters handling.
 *
 * @author jaiber.yepes
 */
interface CharactersRepository {

    suspend fun getCharacters(): Output<List<CharacterUI>>

    suspend fun updateCharacter(characterUI: CharacterDetailsUI)

    suspend fun getCharacterDetails(id: Int): Output<CharacterDetailsUI>
}
