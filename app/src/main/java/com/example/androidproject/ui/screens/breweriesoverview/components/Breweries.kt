package com.example.androidproject.ui.screens.breweriesoverview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.androidproject.R
import com.example.androidproject.model.breweries.Brewery

@Composable
fun Breweries(
    breweries: List<Brewery>,
    onViewDetailClicked: (Brewery) -> Unit,
) {
    if (breweries.isNotEmpty()) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
        ) {
            items(breweries) { brewery ->
                BreweryListItem(
                    brewery,
                    onViewDetailClicked = { onViewDetailClicked(brewery) },
                )
            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(R.string.breweries_no_breweries),
            )
        }
    }
}
