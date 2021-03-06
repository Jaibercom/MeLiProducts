package com.jaiberyepes.mercadolibre.presentation.ui.detail

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
import com.jaiberyepes.mercadolibre.presentation.model.ProductDescriptionUI
import com.jaiberyepes.mercadolibre.presentation.model.ProductDetailUI
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
    lateinit var viewModelFactory: DetailViewModelFactory
    private lateinit var viewModel: DetailViewModel

    private lateinit var binding: ProductDetailFragmentBinding

    // Navigation
    private val args: ProductDetailFragmentArgs by navArgs()

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
        ViewModelProvider(this, viewModelFactory)[DetailViewModel::class.java].also { viewModel = it }
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

        observe(viewModel.currentUIStateLiveData, ::onUIStateChange)
    }

    override fun onResume() {
        super.onResume()
        viewModel.setProduct(args.product)
    }

    private fun onUIStateChange(uiState: UIState<DetailViewModel.ProductDataType>) =
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

    private fun showData(dataType: DetailViewModel.ProductDataType) {
        Timber.d("showData")

        loadingInflated?.gone()
        when (dataType) {
            is DetailViewModel.ProductDataType.ProductDetailData -> showProductDetail(dataType.productDetail)
            is DetailViewModel.ProductDataType.ProductDescriptionData -> showProductDescription(dataType.productDescription)
        }
    }

    private fun showProductDetail(product: ProductDetailUI) {
        Timber.d("showProductDetail: $product")

        binding.titleView.text = product.title
        binding.priceView.text = getCurrencyFormat(product.price)

        Glide.with(this)
            .load(product.formatMeliImgUrl())
            .apply(RequestOptions().placeholder(R.color.grayLight))
            .into(binding.imageView)

        binding.imageView.setRoundCorners(R.dimen.margin_x_small)
    }

    private fun showProductDescription(product: ProductDescriptionUI) {
        Timber.d("showProductDetail: $product")
        binding.descriptionView.text = product.description
    }

    private fun showError(@StringRes messageResId: Int) {
        Timber.d("showErrorBanner")
        loadingInflated?.gone()
    }
}
