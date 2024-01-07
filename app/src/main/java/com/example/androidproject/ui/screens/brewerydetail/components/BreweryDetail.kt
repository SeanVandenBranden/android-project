package com.example.androidproject.ui.screens.brewerydetail.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import com.example.androidproject.model.breweries.Brewery

@Composable
fun BreweryDetail(
    brewery: Brewery
) {
    Text(modifier = Modifier.zIndex(2f), text = brewery.name)

}