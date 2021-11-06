package com.jaiberyepes.mercadolibre.data.cache.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class for Character [Entity].
 *
 * @author jaiber.yepes
 */
@Entity(tableName = "character")
data class CharacterEntity(
    @PrimaryKey @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "nickname")
    val nickName: String,

    @ColumnInfo(name = "image")
    val image: String,

    @ColumnInfo(name = "occupation")
    val occupation: String,

    @ColumnInfo(name = "status")
    val status: String,

    @ColumnInfo(name = "portrayed")
    val portrayed: String,

    @ColumnInfo(name = "isFavorite")
    val isFavorite: Boolean
)
