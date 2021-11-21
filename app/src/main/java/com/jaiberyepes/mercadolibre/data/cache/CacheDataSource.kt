package com.jaiberyepes.mercadolibre.data.cache

import com.jaiberyepes.mercadolibre.data.cache.database.dao.ProductDao
import com.jaiberyepes.mercadolibre.data.cache.database.entities.ProductEntity
import javax.inject.Inject

/**
 * Characters related cache DataSource.
 *
 * @author jaiber.yepes
 */
class CacheDataSource @Inject constructor(
    private val charactersDao: ProductDao
) {

    suspend fun insertProducts(products: List<ProductEntity>) =
        charactersDao.insertProducts(products)

}
