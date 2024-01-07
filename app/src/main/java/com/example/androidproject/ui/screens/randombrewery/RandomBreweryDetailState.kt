package com.example.androidproject.ui.screens.randombrewery

import com.example.androidproject.model.breweries.Brewery

data class RandomBreweryDetailState(
    val brewery: Brewery? = null,
    val name: String? = null,
)
