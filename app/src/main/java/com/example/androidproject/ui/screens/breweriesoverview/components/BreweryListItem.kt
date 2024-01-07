package com.example.androidproject.ui.screens.breweriesoverview.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import com.example.androidproject.R
import com.example.androidproject.model.breweries.Brewery

@Composable
fun BreweryListItem(
    brewery: Brewery,
    onViewDetailClicked: (Brewery) -> Unit,
) {
    ElevatedCard(
        modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium)).testTag("breweryListItem"),
    ) {
        ListItem(
            modifier = Modifier
                .clickable { onViewDetailClicked(brewery) }
                .padding(vertical = dimensionResource(R.dimen.padding_small)),
            headlineContent = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_extra_small)),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        brewery.name,
                        style = MaterialTheme.typography.titleLarge,
                    )
                    if(!brewery.address1.isNullOrEmpty()){
                        Text(
                            brewery.address1,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                    if (!brewery.address2.isNullOrEmpty()) {
                        Text(
                            brewery.address2,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                    if (!brewery.address3.isNullOrEmpty()) {
                        Text(
                            brewery.address3,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                    Text(
                        "${brewery.city ?: ""}, ${brewery.stateProvince?: brewery.state?: ""} ${brewery.postalCode?: ""}",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    if(!brewery.country.isNullOrEmpty()) {
                        Text(
                            brewery.country,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            },
        )
        Divider()
    }
}
