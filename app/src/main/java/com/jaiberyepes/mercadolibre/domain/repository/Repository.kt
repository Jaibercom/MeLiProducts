package com.jaiberyepes.mercadolibre.domain.repository

import com.jaiberyepes.mercadolibre.presentation.model.CharacterDetailsUI
import com.jaiberyepes.mercadolibre.presentation.model.CharacterUI
import com.jaiberyepes.mercadolibre.presentation.model.ProductDescriptionUI
import com.jaiberyepes.mercadolibre.presentation.model.ProductDetailUI
import com.jaiberyepes.mercadolibre.presentation.model.ProductUI
import com.jaiberyepes.mercadolibre.util.Output

/**
 * Repository for Characters handling.
 *
 * @author jaiber.yepes
 */
interface Repository {

    suspend fun getProductsFromSearch(keyword: String, offset: Int): Output<List<ProductUI>>

    suspend fun getProductDetail(id: String): Output<ProductDetailUI>

//    suspend fun getProductDetailDescription(id: String): Output<List<ProductDescriptionUI>>

    suspend fun getCharacters(): Output<List<CharacterUI>>

    suspend fun updateCharacter(characterUI: CharacterDetailsUI)

    suspend fun getCharacterDetails(id: Int): Output<CharacterDetailsUI>
}
