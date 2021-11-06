package com.jaiberyepes.mercadolibre.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewStub
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jaiberyepes.mercadolibre.R
import com.jaiberyepes.mercadolibre.presentation.adapter.CharactersController
import com.jaiberyepes.mercadolibre.presentation.model.CharacterUI
import com.jaiberyepes.mercadolibre.presentation.viewmodel.CharactersViewModel
import com.jaiberyepes.mercadolibre.presentation.viewmodel.CharactersViewModel.CharactersDataType
import com.jaiberyepes.mercadolibre.presentation.viewmodel.CharactersViewModelFactory
import com.jaiberyepes.mercadolibre.util.base.UIState
import com.jaiberyepes.mercadolibre.util.extensions.gone
import com.jaiberyepes.mercadolibre.util.extensions.hideKeyboard
import com.jaiberyepes.mercadolibre.util.extensions.observe
import com.jaiberyepes.mercadolibre.util.extensions.visible
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_characters.*
import timber.log.Timber
import javax.inject.Inject

/**
 * Fragment for the Characters view.
 *
 * @author jaiber.yepes
 */
class CharactersFragment : Fragment(R.layout.fragment_characters),
    CharactersController.CharacterClickedListener {

    //ViewModel
    @Inject
    lateinit var charactersViewModelFactory: CharactersViewModelFactory
    private lateinit var charactersViewModel: CharactersViewModel

    // Epoxy controller
    private val charactersController: CharactersController by lazy {
        CharactersController(this)
    }

    // Loading
    private lateinit var loadingViewStub: ViewStub
    private var loadingInflated: View? = null

    // No Results found
    private lateinit var noResultsViewStub: ViewStub
    private var noResultsInflated: View? = null

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewModel
        charactersViewModel = ViewModelProvider(
            requireActivity(),
            charactersViewModelFactory
        ).get(CharactersViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingViewStub = view.findViewById(R.id.charactersLoadingViewStub)
        noResultsViewStub = view.findViewById(R.id.noResultsViewStub)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupCharactersRecyclerView()
        charactersViewModel.getCharacters()
        observe(charactersViewModel.currentUIStateLiveData, ::onUIStateChange)
    }

    private fun setupCharactersRecyclerView() = charactersEpoxyRecyclerView.apply {
        Timber.d("setupCharactersRecyclerView")
        layoutManager = LinearLayoutManager(context)
        setController(charactersController)
    }

    private fun onUIStateChange(uiState: UIState<CharactersDataType>) = when (uiState) {
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

    private fun showData(charactersDataType: CharactersDataType) {
        Timber.d("showData")

        when (charactersDataType) {
            is CharactersDataType.CharactersData -> {
                loadingInflated?.gone()
                showCharacterList(charactersDataType.characters)
            }
        }
    }

    private fun showCharacterList(characters: List<CharacterUI>) {
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

    override fun onCharacterClicked(characterUI: CharacterUI) {
        Timber.d("onCharacterClicked")
        hideKeyboard()
        charactersViewModel.navigateTo(
            CharactersViewModel.CharactersView.CharacterDetailsFragment(
                characterUI.id
            )
        )
    }
}
