package com.example.androidproject.ui.screens.brewerydetail.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.androidproject.model.breweries.Brewery

@Composable
fun BreweryDetail(
    brewery: Brewery
) {
    Text(text = brewery.name)
    //TODO create UI
}