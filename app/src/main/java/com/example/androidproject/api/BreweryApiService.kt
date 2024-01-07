package com.example.androidproject.api

import com.example.androidproject.model.breweries.Brewery
import com.example.androidproject.model.metadata.Metadata


import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface BreweryApiService {
    @GET("breweries")
    @JvmSuppressWildcards
    suspend fun getAll(@QueryMap options: Map<String, Any>
    ): List<Brewery>

    @GET("breweries/{id}")
    suspend fun getById(@Path("id") id: Int): Brewery

    @GET("breweries/random")
    suspend fun getRandom(): List<Brewery>

    @GET("breweries/search")
    suspend fun getSearch(@Query("query") query: String): List<Brewery>

    @GET("breweries/autocomplete")
    suspend fun getAutocomplete(@Query("query") query: String): List<Brewery>

    @GET("breweries/meta")
    suspend fun getMetadata(): Metadata

    companion object Factory {
        fun create(retrofit: Retrofit): BreweryApiService = retrofit.create(BreweryApiService::class.java)
    }
}
