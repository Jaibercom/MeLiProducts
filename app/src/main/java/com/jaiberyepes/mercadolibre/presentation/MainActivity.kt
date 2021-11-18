package com.jaiberyepes.mercadolibre.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.jaiberyepes.mercadolibre.R
import com.jaiberyepes.mercadolibre.presentation.model.ProductUI
import com.jaiberyepes.mercadolibre.presentation.search.SearchFragmentDirections
import com.jaiberyepes.mercadolibre.presentation.search.SearchViewModel
import com.jaiberyepes.mercadolibre.presentation.search.SearchViewModelFactory
import com.jaiberyepes.mercadolibre.presentation.viewmodel.CharactersViewModel
import com.jaiberyepes.mercadolibre.presentation.viewmodel.CharactersViewModel.CharactersView
import com.jaiberyepes.mercadolibre.presentation.viewmodel.CharactersViewModelFactory
import com.jaiberyepes.mercadolibre.util.extensions.observe
import dagger.android.AndroidInjection
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_main.toolbar
import timber.log.Timber

/**
 * Activity for the Main Entry-Point.
 *
 * @author jaiber.yepes
 */
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    // ViewModel
    @Inject
    lateinit var charactersViewModelFactory: CharactersViewModelFactory
    private lateinit var charactersViewModel: CharactersViewModel

    @Inject
    lateinit var searchViewModelFactory: SearchViewModelFactory
    private lateinit var searchViewModel: SearchViewModel

    private lateinit var appBarConfiguration: AppBarConfiguration

    // Navigation
    private val navController: NavController by lazy {
        findNavController(R.id.navHostFragment)
    }

    private val onDestinationChangedListener = NavController.OnDestinationChangedListener { _, destination, _ ->
        when (destination.id) {
//            R.id.characterDetailsFragment -> {
//                toolbar.title = getText(R.string.character_details)
//            }
            else -> {
                toolbar.title = getText(R.string.meli)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        // ViewModel
        charactersViewModel = ViewModelProvider(this, charactersViewModelFactory)[CharactersViewModel::class.java]
        searchViewModel = ViewModelProvider(this, searchViewModelFactory)[SearchViewModel::class.java]
        observe(charactersViewModel.currentViewLiveData, ::onCharactersViewChange)
        observe(searchViewModel.currentViewLiveData, ::onViewChange)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        setSupportActionBar(toolbar)

        appBarConfiguration = AppBarConfiguration(setOf(R.id.charactersFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navController.addOnDestinationChangedListener(onDestinationChangedListener)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun onCharactersViewChange(destination: CharactersView) {
        Timber.d("onCharactersViewChange")
        when (destination) {
            is CharactersView.CharactersFragment -> showCharactersFragment()
            is CharactersView.CharacterDetailsFragment -> showCharacterDetailsFragment(destination.id)
        }
    }

    private fun onViewChange(destination: SearchViewModel.ProductsView) {
        Timber.d("onCharactersViewChange")
        when (destination) {
            is SearchViewModel.ProductsView.SearchDetailFragment -> showDetailFragment(destination.product)
            else -> {}
        }
    }

    private fun showCharactersFragment() {
        Timber.d("showCharactersFragment")
        navController.navigate(R.id.charactersFragment)
    }

    private fun showCharacterDetailsFragment(id: Int) {
        Timber.d("showCharacterDetailsFragment")
        val action = CharactersFragmentDirections.actionCharactersFragmentToCharacterDetailsFragment(id)
        navController.navigate(action)
    }

    private fun showDetailFragment(product: ProductUI) {
        Timber.d("showDetailFragment")
        val action = SearchFragmentDirections.actionSearchFragmentToProductDetailFragment(product)
        navController.navigate(action)
    }
}
