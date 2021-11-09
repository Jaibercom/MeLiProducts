package com.jaiberyepes.mercadolibre.presentation.search

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewStub
import androidx.annotation.StringRes
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jaiberyepes.mercadolibre.R
import com.jaiberyepes.mercadolibre.presentation.adapter.ProductsController
import com.jaiberyepes.mercadolibre.presentation.model.ProductUI
import com.jaiberyepes.mercadolibre.util.base.UIState
import com.jaiberyepes.mercadolibre.util.configureSearchableInfo
import com.jaiberyepes.mercadolibre.util.extensions.gone
import com.jaiberyepes.mercadolibre.util.extensions.hideKeyboard
import com.jaiberyepes.mercadolibre.util.extensions.observe
import com.jaiberyepes.mercadolibre.util.extensions.visible
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import kotlinx.android.synthetic.main.search_fragment.searchEpoxyRecyclerView
import kotlinx.android.synthetic.main.search_fragment.search_view
import timber.log.Timber

/**
 * Search Fragment
 *
 * @author jaiber.yepes
 */
class SearchFragment : Fragment(R.layout.search_fragment), ProductsController.ClickedListener {

    // ViewModel
    @Inject
    lateinit var searchViewModelFactory: SearchViewModelFactory
    private lateinit var viewModel: SearchViewModel

    // Epoxy controller
    private val charactersController: ProductsController by lazy {
        ProductsController(this)
    }

    // Loading
    private lateinit var loadingViewStub: ViewStub
    private var loadingInflated: View? = null

    // No Results found
    private lateinit var noResultsViewStub: ViewStub
    private var noResultsInflated: View? = null

    private val searchListener: SearchView.OnQueryTextListener =
        object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    Timber.d("new text: $it")
                    viewModel.performSearch("Motorola")
                }
                return false
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewModel
        viewModel = ViewModelProvider(this, searchViewModelFactory)[SearchViewModel::class.java]

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCharactersRecyclerView()

        with(search_view) {
            configureSearchableInfo()
            setOnQueryTextListener(searchListener)
        }

        loadingViewStub = view.findViewById(R.id.charactersLoadingViewStub)
        noResultsViewStub = view.findViewById(R.id.noResultsViewStub)

        observe(viewModel.currentUIStateLiveData, ::onUIStateChange)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onProductClicked(productUI: ProductUI) {
        Timber.d("onCharacterClicked")
        hideKeyboard()
        viewModel.navigateTo(
            SearchViewModel.ProductsView.SearchDetailFragment(
                productUI.id
            )
        )
    }

    private fun setupCharactersRecyclerView() = searchEpoxyRecyclerView.apply {
        Timber.d("setupCharactersRecyclerView")
        layoutManager = LinearLayoutManager(context)
        setController(charactersController)
    }

    private fun onUIStateChange(uiState: UIState<SearchViewModel.ProductsDataType>) = when (uiState) {
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

    private fun showData(charactersDataType: SearchViewModel.ProductsDataType) {
        Timber.d("showData")

        when (charactersDataType) {
            is SearchViewModel.ProductsDataType.Products -> {
                loadingInflated?.gone()
                showCharacterList(charactersDataType.characters)
            }
        }
    }

    private fun showCharacterList(characters: List<ProductUI>) {
        Timber.d("showCharacterList")
        if (characters.isNotEmpty()) {
            charactersController.setData(characters)
        } else {
            showError(R.string.characters_search_error_message)
        }
    }

    private fun showError(@StringRes messageResId: Int) {
        Timber.d("showErrorBanner")

        loadingInflated?.gone()

        if (noResultsInflated == null) {
            noResultsInflated = noResultsViewStub.inflate()
        }

        charactersController.setData(listOf())
        noResultsInflated?.visible()
    }
}
