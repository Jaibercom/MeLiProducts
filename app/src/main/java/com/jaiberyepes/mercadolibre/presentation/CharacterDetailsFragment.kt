package com.jaiberyepes.mercadolibre.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewStub
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jaiberyepes.mercadolibre.R
import com.jaiberyepes.mercadolibre.presentation.model.CharacterDetailsUI
import com.jaiberyepes.mercadolibre.presentation.viewmodel.CharactersViewModel
import com.jaiberyepes.mercadolibre.presentation.viewmodel.CharactersViewModel.CharactersDataType.CharacterDetailsData
import com.jaiberyepes.mercadolibre.presentation.viewmodel.CharactersViewModelFactory
import com.jaiberyepes.mercadolibre.util.base.UIState
import com.jaiberyepes.mercadolibre.util.extensions.gone
import com.jaiberyepes.mercadolibre.util.extensions.observe
import com.jaiberyepes.mercadolibre.util.extensions.setRoundCorners
import com.jaiberyepes.mercadolibre.util.extensions.visible
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_character_details.*
import timber.log.Timber
import javax.inject.Inject

/**
 * Fragment for the Character Details view.
 *
 * @author jaiber.yepes
 */
class CharacterDetailsFragment : Fragment(R.layout.fragment_character_details) {

    // ViewModel
    @Inject
    lateinit var charactersViewModelFactory: CharactersViewModelFactory
    private lateinit var charactersViewModel: CharactersViewModel

    // Navigation
    private val args: CharacterDetailsFragmentArgs by navArgs()
    private val characterId: Int by lazy {
        args.character
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
        charactersViewModel = ViewModelProvider(requireActivity(), charactersViewModelFactory)
            .get(CharactersViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingViewStub = view.findViewById(R.id.characterDetailsLoadingViewStub)

        characterFavorite.setOnClickListener {
            onCharacterFavoriteClicked()
        }
    }

    private fun onCharacterFavoriteClicked() {
        Timber.d("onCharacterFavoriteClicked")
        charactersViewModel.updateFavorite()
        charactersViewModel.navigateTo(CharactersViewModel.CharactersView.CharactersFragment)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        observe(charactersViewModel.currentUIStateLiveData, ::onUIStateChange)
        charactersViewModel.getCharacterDetails(characterId)
    }

    private fun onUIStateChange(uiState: UIState<CharactersViewModel.CharactersDataType>) =
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

    private fun showData(charactersDataType: CharactersViewModel.CharactersDataType) {
        Timber.d("showData")

        loadingInflated?.gone()
        when (charactersDataType) {
            is CharacterDetailsData -> showCharacterDetails(charactersDataType.charactersDetails)
        }
    }

    private fun showCharacterDetails(character: CharacterDetailsUI) {
        Timber.d("showCharacterDetailsList")

        val toolbar = requireActivity().toolbar
        toolbar.title = character.name

        nickNameTextView.text = character.nickName
        occupationTextView.text = character.occupation[0]
        statusTextView.text = character.status
        portrayedTextView.text = character.portrayed

        Glide.with(characterImageView)
            .load(character.image)
            .apply(RequestOptions().placeholder(R.color.grayLight))
            .into(characterImageView)
        characterImageView.setRoundCorners(R.dimen.margin_x_small)

        if (character.isFavorite) {
            characterFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            characterFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }

        characterFavorite.visible()
    }

    private fun showError(@StringRes messageResId: Int) {
        Timber.d("showErrorBanner")

        loadingInflated?.gone()
    }
}
