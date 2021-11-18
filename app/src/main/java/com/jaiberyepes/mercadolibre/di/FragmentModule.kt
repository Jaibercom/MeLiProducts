package com.jaiberyepes.mercadolibre.di

import com.jaiberyepes.mercadolibre.presentation.CharacterDetailsFragment
import com.jaiberyepes.mercadolibre.presentation.CharactersFragment
import com.jaiberyepes.mercadolibre.presentation.detail.ProductDetailFragment
import com.jaiberyepes.mercadolibre.presentation.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * [Module] to provide Fragments dependencies.
 *
 * @author jaiber.yepes
 */
@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun bindSearchFragment(): SearchFragment

    @ContributesAndroidInjector
    abstract fun bindProductDetailFragment(): ProductDetailFragment

    @ContributesAndroidInjector
    abstract fun bindCharactersFragment(): CharactersFragment

    @ContributesAndroidInjector
    abstract fun bindCharacterDetailsFragment(): CharacterDetailsFragment
}
