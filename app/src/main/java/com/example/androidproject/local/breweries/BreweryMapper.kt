package com.example.androidproject.local.breweries

import com.example.androidproject.model.breweries.Brewery

/**
 * Converts a [Brewery] object to a [DbBrewery] object with the given [id].
 *
 * @param id The ID to assign to the resulting [DBrewery].
 * @return A [DbBrewery] object representing the converted [Brewery].
 */
fun Brewery.toDbBrewery(id: String): DbBrewery {
    return DbBrewery(
        id = id,
        name = name,
        breweryType = breweryType,
        address1 = address1,
        address2 = address2,
        address3 = address3,
        city = city,
        stateProvince = stateProvince,
        postalCode = postalCode,
        country = country,
        longitude = longitude,
        latitude = latitude,
        phone = phone,
        websiteUrl = websiteUrl,
        state = state,
        street = street
    )
}

/**
 * Converts a [DbBrewery] object to a [Brewery].
 * @return A [Brewery] object representing the converted [DbBrewery].
 */
fun DbBrewery.toDomainObject(): Brewery {
    return Brewery(
        id = id,
        name = name,
        breweryType = breweryType,
        address1 = address1,
        address2 = address2,
        address3 = address3,
        city = city,
        stateProvince = stateProvince,
        postalCode = postalCode,
        country = country,
        longitude = longitude,
        latitude = latitude,
        phone = phone,
        websiteUrl = websiteUrl,
        state = state,
        street = street
    )
}
