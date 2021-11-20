package com.jaiberyepes.mercadolibre.presentation.ui

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
import com.jaiberyepes.mercadolibre.presentation.ui.search.SearchFragmentDirections
import com.jaiberyepes.mercadolibre.presentation.ui.search.SearchViewModel
import com.jaiberyepes.mercadolibre.presentation.ui.search.SearchViewModelFactory
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

    @Inject
    lateinit var searchViewModelFactory: SearchViewModelFactory
    private lateinit var searchViewModel: SearchViewModel

    private lateinit var appBarConfiguration: AppBarConfiguration

    // Navigation
    private val navController: NavController by lazy {
        findNavController(R.id.navHostFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        toolbar.title = getText(R.string.meli)
        // ViewModel
        searchViewModel = ViewModelProvider(this, searchViewModelFactory)[SearchViewModel::class.java]
        observe(searchViewModel.currentViewLiveData, ::onViewChange)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        setSupportActionBar(toolbar)

        appBarConfiguration = AppBarConfiguration(setOf(R.id.searchFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    private fun onViewChange(destination: SearchViewModel.ProductsView) {
        Timber.d("onCharactersViewChange")
        when (destination) {
            is SearchViewModel.ProductsView.SearchDetailFragment -> showDetailFragment(destination.product)
            else -> {}
        }
    }

    private fun showDetailFragment(product: ProductUI) {
        Timber.d("showDetailFragment")
        val action = SearchFragmentDirections.actionSearchFragmentToProductDetailFragment(product)
        navController.navigate(action)
    }
}
