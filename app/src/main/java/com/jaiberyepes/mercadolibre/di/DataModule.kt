package com.jaiberyepes.mercadolibre.di

import com.jaiberyepes.mercadolibre.data.CharactersRepositoryImpl
import com.jaiberyepes.mercadolibre.domain.repository.CharactersRepository
import dagger.Binds
import dagger.Module

/**
 * [Module] to provide data level dependencies.
 *
 * @author jaiber.yepes
 */
@Module(
    includes = [
        NetworkModule::class,
        CacheModule::class
    ]
)
abstract class DataModule {

    @Binds
    internal abstract fun providesCharactersRepository(
        charactersRepository: CharactersRepositoryImpl
    ): CharactersRepository
}
