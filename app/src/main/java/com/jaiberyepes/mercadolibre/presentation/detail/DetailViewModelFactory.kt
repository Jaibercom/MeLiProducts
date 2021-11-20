package com.jaiberyepes.mercadolibre.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jaiberyepes.mercadolibre.domain.usescases.UseCases
import javax.inject.Inject

/**
 * ViewModel provider factory to instantiate [DetailViewModel].
 * Required given CharactersViewModel has a non-empty constructor.
 *
 * @author jaiber.yepes
 */
class DetailViewModelFactory @Inject constructor(
        private val useCases: UseCases
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(useCases) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
