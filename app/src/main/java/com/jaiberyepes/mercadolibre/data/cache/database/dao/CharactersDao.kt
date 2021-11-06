package com.jaiberyepes.mercadolibre.data.cache.database.dao

import androidx.room.*
import com.jaiberyepes.mercadolibre.data.cache.database.entities.CharacterEntity


/**
 * Class that defines AppAccessToken database operations.
 *
 * @author jaiber.yepes
 */
@Dao
interface CharactersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<CharacterEntity>)

    @Update
    suspend fun updateCharacter(character: CharacterEntity)

    @Query("SELECT * FROM character")
    suspend fun getCharacters(): List<CharacterEntity>

    @Query("SELECT * FROM character WHERE id = :id")
    suspend fun getCharacterById(id: Int): CharacterEntity

    @Query("SELECT * FROM character ORDER BY character.isFavorite = 1 DESC")
    suspend fun getCharactersWithFavorites(): List<CharacterEntity>

    @Query("SELECT * FROM character WHERE character.name LIKE :query")
    suspend fun searchCharacter(query: String): List<CharacterEntity>
}
