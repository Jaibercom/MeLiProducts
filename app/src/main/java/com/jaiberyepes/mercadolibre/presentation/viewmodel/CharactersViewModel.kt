package com.jaiberyepes.mercadolibre.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaiberyepes.mercadolibre.R
import com.jaiberyepes.mercadolibre.domain.usescases.UseCases
import com.jaiberyepes.mercadolibre.presentation.model.CharacterDetailsUI
import com.jaiberyepes.mercadolibre.presentation.model.CharacterUI
import com.jaiberyepes.mercadolibre.util.Output
import com.jaiberyepes.mercadolibre.util.base.NavigationProvider
import com.jaiberyepes.mercadolibre.util.base.UIState
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * [ViewModel] for the Characters flow.
 *
 * @author jaiber.yepes
 */
class CharactersViewModel @Inject constructor(
    private val charactersUseCases: UseCases
) : ViewModel(), NavigationProvider<CharactersViewModel.CharactersView> {

    // Current view LiveData
    private val currentView = MutableLiveData<CharactersView>()
    val currentViewLiveData: LiveData<CharactersView>
        get() = currentView

    // UIState LiveData
    private val currentUIState = MutableLiveData<UIState<CharactersDataType>>()
    val currentUIStateLiveData: LiveData<UIState<CharactersDataType>>
        get() = currentUIState

    private var characters: List<CharacterUI> = listOf()

    private lateinit var character: CharacterDetailsUI


    override fun navigateTo(destinationView: CharactersView) {
        currentView.value = destinationView
    }

    fun getCharacters() = viewModelScope.launch {
        Timber.d("getCharacters")
        currentUIState.value = UIState.Loading()

        val output = charactersUseCases.getCharacters()
        if (output is Output.Success) {
            characters = output.data
            Timber.d("Characters: $characters")
            currentUIState.value = UIState.Data(CharactersDataType.CharactersData(characters))
        } else {
            currentUIState.value = UIState.Error(R.string.characters_error_message)
        }
    }

    fun getCharacterDetails(id: Int) = viewModelScope.launch {
        Timber.d("getCharacterDetails")
        currentUIState.value = UIState.Loading()

        val output = charactersUseCases.getCharacterDetails(id)
        if (output is Output.Success) {
            character = output.data
            Timber.d("Character: $output.data")
            currentUIState.value = UIState.Data(CharactersDataType.CharacterDetailsData(output.data))
        } else {
            Timber.d("Character: error")
            currentUIState.value = UIState.Error(R.string.characters_error_message)
        }
    }

    fun updateFavorite() = viewModelScope.launch {
        character.isFavorite = !character.isFavorite
        charactersUseCases.updateCharacter(character)
    }

    sealed class CharactersView {
        object CharactersFragment : CharactersView()
        data class CharacterDetailsFragment(val id: Int) : CharactersView()
    }

    sealed class CharactersDataType {
        data class CharactersData(val characters: List<CharacterUI>) : CharactersDataType()
        data class CharacterDetailsData(val charactersDetails: CharacterDetailsUI) : CharactersDataType()
    }
}