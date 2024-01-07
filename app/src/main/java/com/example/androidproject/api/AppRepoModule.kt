package com.example.androidproject.api

import com.example.androidproject.local.breweries.BreweryDao
import com.example.androidproject.local.breweries.BreweryRepository
import com.example.androidproject.local.breweries.OfflineBreweryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
class AppRepoModule {
    @Provides
    fun providesBreweryApiRepository(breweryDao: BreweryDao, breweryApiService: BreweryApiService): BreweryRepository =
        OfflineBreweryRepository(breweryDao, breweryApiService)
}

@InstallIn(SingletonComponent::class)
@Module
class ApiServiceModule {
    @Provides
    fun providesBreweryApiService(retrofit: Retrofit): BreweryApiService =
        BreweryApiService.create(retrofit)
}
