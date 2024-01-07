package com.example.androidproject.ui.screens.brewerydetail

import com.example.androidproject.model.breweries.Brewery

data class BreweryDetailState(
    val brewery: Brewery? = null,
    val name: String? = null,
)
