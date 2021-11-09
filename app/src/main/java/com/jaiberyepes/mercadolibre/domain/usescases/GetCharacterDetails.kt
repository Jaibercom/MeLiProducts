package com.jaiberyepes.mercadolibre.domain.usescases

import com.jaiberyepes.mercadolibre.domain.repository.Repository
import com.jaiberyepes.mercadolibre.presentation.model.CharacterDetailsUI
import com.jaiberyepes.mercadolibre.presentation.model.CharacterUI
import com.jaiberyepes.mercadolibre.util.Output
import javax.inject.Inject


/**
 * UseCase for getting the [CharacterUI] list.
 *
 * @author jaiber.yepes
 */
class GetCharacterDetails @Inject constructor(private val charactersRepository: Repository) {

    suspend operator fun invoke(id: Int): Output<CharacterDetailsUI> = charactersRepository.getCharacterDetails(id)
}
