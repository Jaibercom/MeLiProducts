package com.jaiberyepes.mercadolibre.domain.usescases

import com.jaiberyepes.mercadolibre.domain.repository.CharactersRepository
import com.jaiberyepes.mercadolibre.presentation.model.CharacterDetailsUI
import com.jaiberyepes.mercadolibre.presentation.model.CharacterUI
import javax.inject.Inject


/**
 * UseCase for updating the [CharacterUI].
 *
 * @author jaiber.yepes
 */
class UpdateCharacter @Inject constructor(private val charactersRepository: CharactersRepository) {

    suspend operator fun invoke(characterUI: CharacterDetailsUI)= charactersRepository.updateCharacter(characterUI)
}
