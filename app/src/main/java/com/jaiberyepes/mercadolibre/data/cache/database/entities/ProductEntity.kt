package com.jaiberyepes.mercadolibre.data.cache.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class for Product [Entity].
 *
 * @author jaiber.yepes
 */
@Entity(tableName = "character")
data class ProductEntity(
    @PrimaryKey @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "isFavorite")
    val isFavorite: Boolean
)
