package com.jaiberyepes.mercadolibre.domain.usescases

import javax.inject.Inject

/**
 * Use Cases for the Characters flow.
 *
 * @author jaiber.yepes
 */
data class UseCases @Inject constructor(
    val getProductsFromSearch: GetProductsFromSearch,
    val getProductDetail: GetProductDetail,
    val getProductDescription: GetProductDescription,


    val getCharacters: GetCharacters,
    val getCharacterDetails: GetCharacterDetails,
    val updateCharacter: UpdateCharacter
)
