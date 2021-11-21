package com.jaiberyepes.mercadolibre.data.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jaiberyepes.mercadolibre.data.cache.database.dao.ProductDao
import com.jaiberyepes.mercadolibre.data.cache.database.entities.ProductEntity

/**
 * Database class for Characters related Dao.
 *
 * @author jaiber.yepes
 */
@Database(entities = [ProductEntity::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {

    abstract fun charactersDao(): ProductDao
}
