package com.jaiberyepes.mercadolibre.presentation.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaiberyepes.mercadolibre.R
import com.jaiberyepes.mercadolibre.domain.usescases.UseCases
import com.jaiberyepes.mercadolibre.presentation.model.ProductUI
import com.jaiberyepes.mercadolibre.util.Output
import com.jaiberyepes.mercadolibre.util.base.NavigationProvider
import com.jaiberyepes.mercadolibre.util.base.UIState
import javax.inject.Inject
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Search ViewModel
 *
 * @author jaiber.yepes
 */
class SearchViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel(), NavigationProvider<SearchViewModel.ProductsView?> {

    // Current view LiveData
    private val currentView = MutableLiveData<ProductsView?>()
    val currentViewLiveData: LiveData<ProductsView?> = currentView

    // UIState LiveData
    private val currentUIState = MutableLiveData<UIState<ProductsDataType>>()
    val currentUIStateLiveData: LiveData<UIState<ProductsDataType>> = currentUIState

    private var products: List<ProductUI> = listOf()

    override fun navigateTo(destinationView: ProductsView?) {
        currentView.value = destinationView
    }

    fun performSearch(keyword: String) {
        Timber.d("performFirstSearch")
        if (keyword.isEmpty()) return

        viewModelScope.launch {
            currentUIState.value = UIState.Loading()
            val output = useCases.getProductsFromSearch(keyword, offset = 0)
            if (output is Output.Success) {
                products = output.data
                Timber.d("Products: $products")
                currentUIState.value = UIState.Data(ProductsDataType.Products(products))
            } else {
                currentUIState.value = UIState.Error(R.string.characters_error_message)
            }
        }
    }

    sealed class ProductsView {
        data class SearchDetailFragment(val product: ProductUI) : ProductsView()
    }

    sealed class ProductsDataType {
        data class Products(val characters: List<ProductUI>) : ProductsDataType()
    }

}
