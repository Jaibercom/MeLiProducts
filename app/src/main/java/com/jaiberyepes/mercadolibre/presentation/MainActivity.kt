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
import com.jaiberyepes.mercadolibre.presentation.viewmodel.CharactersViewModel
import com.jaiberyepes.mercadolibre.presentation.viewmodel.CharactersViewModel.CharactersView
import com.jaiberyepes.mercadolibre.presentation.viewmodel.CharactersViewModelFactory
import com.jaiberyepes.mercadolibre.util.extensions.observe
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject

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

    private lateinit var appBarConfiguration: AppBarConfiguration

    // Navigation
    private val charactersNavController: NavController by lazy {
        findNavController(R.id.charactersNavHostFragment)
    }

    private val onDestinationChangedListener = NavController.OnDestinationChangedListener { _, destination, _ ->
        when (destination.id) {
//            R.id.characterDetailsFragment -> {
//                toolbar.title = getText(R.string.character_details)
//            }
            else -> {
                toolbar.title = getText(R.string.characters)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        // ViewModel
        charactersViewModel = ViewModelProvider(this, charactersViewModelFactory)
                .get(CharactersViewModel::class.java)
        observe(charactersViewModel.currentViewLiveData, ::onCharactersViewChange)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        setSupportActionBar(toolbar)

        appBarConfiguration = AppBarConfiguration(setOf(R.id.charactersFragment))
        setupActionBarWithNavController(charactersNavController, appBarConfiguration)
        charactersNavController.addOnDestinationChangedListener(onDestinationChangedListener)
    }

    override fun onSupportNavigateUp(): Boolean {
        return charactersNavController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun onCharactersViewChange(destination: CharactersViewModel.CharactersView) {
        Timber.d("onCharactersViewChange")
        when (destination) {
            is CharactersView.CharactersFragment -> showCharactersFragment()
            is CharactersView.CharacterDetailsFragment -> showCharacterDetailsFragment(destination.id)
        }
    }

    private fun showCharactersFragment() {
        Timber.d("showCharactersFragment")
        charactersNavController.navigate(R.id.charactersFragment)
    }

    private fun showCharacterDetailsFragment(id: Int) {
        Timber.d("showCharacterDetailsFragment")
        val action = CharactersFragmentDirections.actionCharactersFragmentToCharacterDetailsFragment(id)
        charactersNavController.navigate(action)
    }
}
