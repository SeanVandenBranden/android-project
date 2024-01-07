package com.example.androidproject.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.androidproject.local.breweries.BreweryDao
import com.example.androidproject.local.breweries.DbBrewery

@Database(
    entities = [
        DbBrewery::class, ],
    version = 1,
    exportSchema = false,
)
abstract class AndroidProjectDatabase : RoomDatabase() {
    abstract fun breweryDao(): BreweryDao
}
