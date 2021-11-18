package com.jaiberyepes.mercadolibre.presentation.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jaiberyepes.mercadolibre.R
import com.jaiberyepes.mercadolibre.databinding.ProductDetailFragmentBinding
import com.jaiberyepes.mercadolibre.presentation.model.ProductUI
import com.jaiberyepes.mercadolibre.presentation.search.SearchViewModel
import com.jaiberyepes.mercadolibre.presentation.search.SearchViewModelFactory
import com.jaiberyepes.mercadolibre.util.base.UIState
import com.jaiberyepes.mercadolibre.util.extensions.gone
import com.jaiberyepes.mercadolibre.util.extensions.observe
import com.jaiberyepes.mercadolibre.util.extensions.setRoundCorners
import com.jaiberyepes.mercadolibre.util.extensions.visible
import com.jaiberyepes.mercadolibre.util.formatMeliImgUrl
import com.jaiberyepes.mercadolibre.util.getCurrencyFormat
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import timber.log.Timber

/**
 * Fragment for the Product Details view.
 *
 * @author jaiber.yepes
 */
class ProductDetailFragment : Fragment() {

    // ViewModel
    @Inject
    lateinit var viewModelFactory: SearchViewModelFactory
    private lateinit var viewModel: SearchViewModel

    private lateinit var binding: ProductDetailFragmentBinding

    // Navigation
    private val args: ProductDetailFragmentArgs by navArgs()
    private val product: ProductUI by lazy {
        args.product
    }

    // Loading
    private lateinit var loadingViewStub: ViewStub
    private var loadingInflated: View? = null

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewModel
        ViewModelProvider(requireActivity(), viewModelFactory)[SearchViewModel::class.java].also { viewModel = it }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProductDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingViewStub = view.findViewById(R.id.detailLoadingViewStub)

//        characterFavorite.setOnClickListener {
//            onCharacterFavoriteClicked()
//        }

        observe(viewModel.currentUIStateLiveData, ::onUIStateChange)
//        viewModel.getCharacterDetails(characterId)
    }
//
//    private fun onCharacterFavoriteClicked() {
//        Timber.d("onCharacterFavoriteClicked")
////        viewModel.updateFavorite()
//        viewModel.navigateTo(CharactersViewModel.CharactersView.CharactersFragment)
//    }

    override fun onResume() {
        super.onResume()
        viewModel.setDetailScreen(product)
    }

    private fun onUIStateChange(uiState: UIState<SearchViewModel.ProductsDataType>) =
        when (uiState) {
            is UIState.Loading -> showLoading()
            is UIState.Data -> showData(uiState.data)
            is UIState.Error -> showError(uiState.message)
        }

    private fun showLoading() {
        Timber.d("showLoading")
        if (loadingInflated == null) {
            loadingInflated = loadingViewStub.inflate()
        }

        loadingInflated?.visible()
    }

    private fun showData(dataType: SearchViewModel.ProductsDataType) {
        Timber.d("showData")

        loadingInflated?.gone()
        when (dataType) {
            is SearchViewModel.ProductsDataType.ProductDetailData -> showProductDetail(dataType.productDetail)
        }
    }

    private fun showProductDetail(product: ProductUI) {
        Timber.d("showProductDetail: $product")

        binding.titleTextView.text = product.title
        binding.priceTextView.text = getCurrencyFormat(product.price)

        Glide.with(this)
            .load(product.formatMeliImgUrl())
            .apply(RequestOptions().placeholder(R.color.grayLight))
            .into(binding.pictureImageView)

        binding.pictureImageView.setRoundCorners(R.dimen.margin_x_small)
//
//        if (product.isFavorite) {
//            characterFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
//        } else {
//            characterFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
//        }
//        characterFavorite.visible()
    }

    private fun showError(@StringRes messageResId: Int) {
        Timber.d("showErrorBanner")
        loadingInflated?.gone()
    }
}