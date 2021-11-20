package com.jaiberyepes.mercadolibre.presentation.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jaiberyepes.mercadolibre.domain.usescases.UseCases
import javax.inject.Inject

/**
 * ViewModel provider factory to instantiate [ProductsViewModel].
 * Required given CharactersViewModel has a non-empty constructor.
 *
 * @author jaiber.yepes
 */
class SearchViewModelFactory @Inject constructor(
        private val useCases: UseCases
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(useCases) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
