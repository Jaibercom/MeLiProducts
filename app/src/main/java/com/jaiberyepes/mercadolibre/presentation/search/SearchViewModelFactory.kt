package com.jaiberyepes.mercadolibre.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jaiberyepes.mercadolibre.domain.repository.Repository
import com.jaiberyepes.mercadolibre.domain.usescases.CharactersUseCases
import javax.inject.Inject

/**
 * ViewModel provider factory to instantiate [ProductsViewModel].
 * Required given CharactersViewModel has a non-empty constructor.
 *
 * @author jaiber.yepes
 */
class SearchViewModelFactory @Inject constructor(
        private val useCases: CharactersUseCases
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(useCases) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
