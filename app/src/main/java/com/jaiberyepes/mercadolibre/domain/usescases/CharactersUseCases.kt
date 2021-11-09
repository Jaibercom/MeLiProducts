package com.jaiberyepes.mercadolibre.domain.usescases

import javax.inject.Inject

/**
 * Use Cases for the Characters flow.
 *
 * @author jaiber.yepes
 */
data class CharactersUseCases @Inject constructor(
    val GetProductsFromSearch: GetProductsFromSearch,
    val getCharacters: GetCharacters,
    val getCharacterDetails: GetCharacterDetails,
    val updateCharacter: UpdateCharacter
)
