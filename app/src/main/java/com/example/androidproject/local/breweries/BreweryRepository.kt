package com.example.androidproject.local.breweries

import com.example.androidproject.api.BreweryApiService
import com.example.androidproject.model.breweries.Brewery
import com.example.androidproject.model.metadata.Metadata
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Repository interface for managing breweries.
 */
interface BreweryRepository {
    /**
     * Retrieve all breweries as a Flow of lists.
     *
     * @return A Flow emitting a list of [Brewery] objects.
     */
    fun getAll(): Flow<List<Brewery>>

    /**
     * Retrieve a brewery by its unique identifier as a Flow.
     *
     * @param id The unique identifier of the brewery.
     * @return A Flow emitting a single [Brewery] object.
     */
    fun getById(id: String): Flow<Brewery>

    /**
     * Refresh breweries from an external source.
     */
    suspend fun refreshBreweries()

    suspend fun getRandom(): Brewery?

    suspend fun getMetadata(): Metadata?
}

/**
 * Implementation of the [BreweryRepository] interface for offline storage and retrieval of breweries.
 *
 * @param breweryDao The DAO for managing breweries in the local database.
 */
class OfflineBreweryRepository(
    private val breweryDao: BreweryDao,
    private val breweryApiService: BreweryApiService
) : BreweryRepository {
    override fun getAll(): Flow<List<Brewery>> {
        val debug : Flow<List<Brewery>> = breweryDao.getAllBreweries().map { dbBreweries -> dbBreweries.map { dbBrewery -> dbBrewery.toDomainObject() }}
        return breweryDao.getAllBreweries().map { dbBreweries -> dbBreweries.map { dbBrewery -> dbBrewery.toDomainObject() }}
    }

    override fun getById(id: String): Flow<Brewery> {
        return breweryDao.getBreweryById(id).map { brewery ->
            brewery.toDomainObject()
        }
    }

    override suspend fun refreshBreweries() {
        val queryOptions = mutableMapOf<String, Any>()
        queryOptions["page"] = 1
        queryOptions["per_page"] = Integer.MAX_VALUE
        val breweries = breweryApiService.getAll(queryOptions)
        coroutineScope {
            // Process the fetched data
            val dbBreweries = mutableListOf<DbBrewery>()

            breweries.forEach { brewery ->
                dbBreweries.add(brewery.toDbBrewery(brewery.id))
            }

            // Insert into database
            breweryDao.insertBreweries(dbBreweries)
        }
    }

    override suspend fun getRandom(): Brewery? {
        var brewery: Brewery? = null;
        coroutineScope {
            brewery = breweryApiService.getRandom()[0]//return list with one item sadly
        }
        return brewery
    }

    override suspend fun getMetadata(): Metadata? {
        var metadata: Metadata? = null;
        coroutineScope {
            metadata = breweryApiService.getMetadata()//return list with one item sadly
        }
        return metadata
    }
}
