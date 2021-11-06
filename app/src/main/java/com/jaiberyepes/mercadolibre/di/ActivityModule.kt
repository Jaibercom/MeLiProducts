package com.jaiberyepes.mercadolibre.di

import com.jaiberyepes.mercadolibre.presentation.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * [Module] to provide Activities dependencies.
 *
 * @author jaiber.yepes
 */
@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}
