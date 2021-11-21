package com.jaiberyepes.mercadolibre.data.cache.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.jaiberyepes.mercadolibre.data.cache.database.entities.ProductEntity


/**
 * Class that defines AppAccessToken database operations.
 *
 * @author jaiber.yepes
 */
@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(characters: List<ProductEntity>)

    @Update
    suspend fun updateProduct(product: ProductEntity)

}
