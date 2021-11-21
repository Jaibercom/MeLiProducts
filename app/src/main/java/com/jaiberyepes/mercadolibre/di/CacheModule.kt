package com.jaiberyepes.mercadolibre.di

import android.app.Application
import androidx.room.Room
import com.jaiberyepes.mercadolibre.data.cache.database.Database
import com.jaiberyepes.mercadolibre.data.cache.database.dao.ProductDao
import dagger.Module
import dagger.Provides

/**
 * [Module] to provide cache/local level dependencies.
 *
 * @author jaiber.yepes
 */
@Module
object CacheModule {

    @Provides
    @JvmStatic
    internal fun providesCharactersDatabase(context: Application): Database =
        Room.databaseBuilder(context, Database::class.java, "characters_db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @JvmStatic
    fun providesAppAccessTokenDao(charactersDatabase: Database): ProductDao =
        charactersDatabase.charactersDao()
}
