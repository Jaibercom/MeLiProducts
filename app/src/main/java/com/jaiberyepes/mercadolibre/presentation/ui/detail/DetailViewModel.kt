package com.jaiberyepes.mercadolibre.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaiberyepes.mercadolibre.R
import com.jaiberyepes.mercadolibre.domain.usescases.UseCases
import com.jaiberyepes.mercadolibre.presentation.model.ProductDescriptionUI
import com.jaiberyepes.mercadolibre.presentation.model.ProductDetailUI
import com.jaiberyepes.mercadolibre.presentation.model.ProductUI
import com.jaiberyepes.mercadolibre.util.Output
import com.jaiberyepes.mercadolibre.util.base.UIState
import javax.inject.Inject
import kotlinx.coroutines.launch
import timber.log.Timber


class DetailViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private lateinit var productDetail: ProductDetailUI
    private lateinit var productDescription: ProductDescriptionUI

    // UIState LiveData
    private val currentUIState = MutableLiveData<UIState<ProductDataType>>()
    val currentUIStateLiveData: LiveData<UIState<ProductDataType>> = currentUIState

    fun setProduct(product: ProductUI) {
        getProductDetail(product.id)
        getProductDescription(product.id)
    }

    private fun getProductDetail(id: String) {
        viewModelScope.launch {
            currentUIState.value = UIState.Loading()
            val output = useCases.getProductDetail(id)

            if (output is Output.Success) {
                productDetail = output.data
                Timber.d("Products: $productDetail")
                currentUIState.value = UIState.Data(ProductDataType.ProductDetailData(productDetail))
            } else {
                currentUIState.value = UIState.Error(R.string.products_error_message)
            }
        }
    }

    private fun getProductDescription(id: String) {
        viewModelScope.launch {
            currentUIState.value = UIState.Loading()
            val output = useCases.getProductDescription(id)

            if (output is Output.Success) {
                productDescription = output.data
                Timber.d("productDescription: $productDescription")
                currentUIState.value = UIState.Data(ProductDataType.ProductDescriptionData(productDescription))
            } else {
                currentUIState.value = UIState.Error(R.string.products_error_message)
            }
        }
    }


    sealed class ProductDataType {
        data class ProductDetailData(val productDetail: ProductDetailUI) : ProductDataType()
        data class ProductDescriptionData(val productDescription: ProductDescriptionUI) : ProductDataType()
    }

}
