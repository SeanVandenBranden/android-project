package com.example.androidproject.local.breweries

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for managing Breweries in the database.
 */
@Dao
interface BreweryDao {
    /**
     * Inserts a single Brewery into the database. If a Brewery with the same primary key
     * already exists, it will be replaced.
     *
     * @param brewery The Brewery to be inserted or replaced.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBrewery(brewery: DbBrewery)

    /**
     * Inserts a list of Breweries into the database. If any of the Breweries with the same
     * primary keys already exist, they will be replaced.
     *
     * @param breweries The list of Breweries to be inserted or replaced.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBreweries(breweries: List<DbBrewery>)

    /**
     * Retrieves all Breweries from the database where the `mostRecentVersionId` is `null`.
     *
     * @return A [Flow] of a list of [DbBrewery] representing the Breweries.
     */
    @Query("SELECT * FROM Brewery")
    fun getAllBreweries(): Flow<List<DbBrewery>>

    /**
     * Retrieves a single Brewery with the specified `BreweryId`.
     *
     * @param breweryId The ID of the Brewery to retrieve.
     * @return A [Flow] of [DbBrewery] representing the Brewery.
     */
    @Query("SELECT * FROM Brewery WHERE id = :breweryId")
    fun getBreweryById(breweryId: String): Flow<DbBrewery>
}
