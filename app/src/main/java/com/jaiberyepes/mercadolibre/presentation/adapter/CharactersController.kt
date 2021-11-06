package com.jaiberyepes.mercadolibre.presentation.adapter

import com.airbnb.epoxy.TypedEpoxyController
import com.jaiberyepes.mercadolibre.presentation.model.CharacterUI
import com.jaiberyepes.mercadolibre.presentation.model.character

/**
 * Epoxy Controller Class for the Characters list.
 *
 * @author jaiber.yepes
 */
class CharactersController(
    private val listener: CharacterClickedListener
) : TypedEpoxyController<List<CharacterUI>>() {

    override fun buildModels(characters: List<CharacterUI>) {
        characters.forEach {
            character {
                id(it.id)
                characterUI(it)
                characterClickedListener(listener)
            }
        }
    }
    
    interface CharacterClickedListener {
        fun onCharacterClicked(characterUI: CharacterUI)
    }
}
