package com.example.androidproject.local

import android.content.Context
import androidx.room.Room
import com.example.androidproject.local.breweries.BreweryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): AndroidProjectDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AndroidProjectDatabase::class.java,
            "Project.db",
        ).build()
    }

    @Provides
    fun provideBreweryDao(database: AndroidProjectDatabase): BreweryDao = database.breweryDao()
}
