package com.jaiberyepes.mercadolibre.di

import com.jaiberyepes.mercadolibre.data.repository.RepositoryImpl
import com.jaiberyepes.mercadolibre.domain.repository.Repository
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
        charactersRepository: RepositoryImpl
    ): Repository
}
