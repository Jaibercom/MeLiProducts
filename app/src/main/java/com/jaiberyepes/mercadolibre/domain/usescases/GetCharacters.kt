package com.jaiberyepes.mercadolibre.domain.usescases

import com.jaiberyepes.mercadolibre.domain.repository.CharactersRepository
import com.jaiberyepes.mercadolibre.presentation.model.CharacterUI
import com.jaiberyepes.mercadolibre.util.Output
import javax.inject.Inject


/**
 * UseCase for getting the [CharacterUI] list.
 *
 * @author jaiber.yepes
 */
class GetCharacters @Inject constructor(private val charactersRepository: CharactersRepository) {

    suspend operator fun invoke(): Output<List<CharacterUI>> = charactersRepository.getCharacters()
}
