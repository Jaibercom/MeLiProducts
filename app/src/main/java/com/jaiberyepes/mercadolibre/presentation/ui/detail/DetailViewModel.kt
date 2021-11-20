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
//    private val repository: Repository
    private val useCases: UseCases
) : ViewModel() {

    private lateinit var productDetail: ProductDetailUI
    private lateinit var productDescription: ProductDescriptionUI


//    val description = ObservableField("")
//
//    val isFavorite = ObservableField(false)
//
//    val productField = ObservableField<ProductUI>()

//    private val _eventLiveData = MutableLiveData<DashBoardEvents<*>>()
//    val eventLiveData: LiveData<DashBoardEvents<*>> get() = _eventLiveData

    // UIState LiveData
    private val currentUIState = MutableLiveData<UIState<ProductDataType>>()
    val currentUIStateLiveData: LiveData<UIState<ProductDataType>> = currentUIState

    fun setProduct(product: ProductUI) {
        getProductDetail(product.id)
        getProductDescription(product.id)
    }

    fun setupProductInformation(product: ProductUI, isFavorite: Boolean = false) {
//        this.productField.set(product)
//        this.isFavorite.set(isFavorite)

        if (!isFavorite) {
//            getProductDetail(product)
//            getProductDescription(product)
        }

    }

//    fun setDetailScreen(product: ProductUI){
//        currentUIState.value = UIState.Data(ProductDataType.ProductDetailData(product))
//    }

    private fun getProductDetail(id: String) {
        viewModelScope.launch {
//            try {
//                useCases.getProductDetail(product.id)?.let {
//                    productDetail = it
//                }
//            } catch (e: Exception) {
//                Timber.e(e)
//            }

            val output = useCases.getProductDetail(id)

            if (output is Output.Success) {
                productDetail = output.data
                Timber.d("Products: $productDetail")
                currentUIState.value = UIState.Data(ProductDataType.ProductDetailData(productDetail))
            } else {
                currentUIState.value = UIState.Error(R.string.characters_error_message)
            }
        }
    }

    private fun getProductDescription(id: String) {
        viewModelScope.launch {
            val output = useCases.getProductDescription(id)

            if (output is Output.Success) {
                productDescription = output.data
                Timber.d("productDescription: $productDescription")
                currentUIState.value = UIState.Data(ProductDataType.ProductDescriptionData(productDescription))
            } else {
                currentUIState.value = UIState.Error(R.string.characters_error_message)
            }
        }
    }

//    private fun getProductDescription(product: ProductUI) {
//        viewModelScope.launch {
//            try {
//                useCases.getProductDetail(product.id)?.let {
//                    productDescription = it
//                    description.set(it.text)
//                    updateProduct(it)
//                }
//            } catch (e: Exception) {
//                Timber.e(e)
//            }
//        }
//    }

//    private fun updateProduct(description: ProductDescription) {
//        val productTmp = productField.get()
//        productField.set(
//            productTmp?.copy(
//                description = description.text
//            )
//        )
//    }

    fun toggleFavorites() {
//        val state = !(isFavorite.get() ?: false)
//
//        if (state) saveToFavorites()
//        else removeFromFavorites()
//
//        isFavorite.set(state)
    }

    fun openLink() {
//        productField.get()?.let {
//            _eventLiveData.postValue(DashBoardEvents.OpenURL(it.productUrl))
//        }
    }

    private fun saveToFavorites() {
//        productField.get()?.let {
            viewModelScope.launch {
//                favoritesRepository.saveToFavorites(it)
            }
//        }
    }

    private fun removeFromFavorites() {
//        productField.get()?.let {
//            viewModelScope.launch {
////                favoritesRepository.removeFromFavorites(it)
//            }
//        }
    }


    sealed class ProductDataType {
        //        data class DetailProduct(val characters: List<ProductUI>) : DetailDataType()
        data class ProductDetailData(val productDetail: ProductDetailUI) : ProductDataType()
        data class ProductDescriptionData(val productDescription: ProductDescriptionUI) : ProductDataType()
    }

//    sealed class ProductsDataType {
//        data class Products(val characters: List<ProductUI>) : ProductsDataType()
//        data class ProductDetailData(val productDetail: ProductUI) : ProductsDataType()
//    }

}
