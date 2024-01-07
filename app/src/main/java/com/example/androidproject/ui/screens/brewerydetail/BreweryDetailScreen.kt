package com.example.androidproject.ui.screens.brewerydetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.androidproject.R
import com.example.androidproject.model.breweries.Brewery
import com.example.androidproject.ui.common.LoadingScreen
import com.example.androidproject.ui.common.UIState
import com.example.androidproject.ui.screens.brewerydetail.components.BreweryDetail

@Composable
fun BreweryDetailScreen(viewModel: BreweryDetailViewModel = hiltViewModel()) {
    val breweryDetailState by viewModel.uiState.collectAsState()
    val breweryApiState = viewModel.breweryApiState

    Box(modifier = Modifier.fillMaxWidth().testTag("breweriesOverview"),) {
        when (breweryApiState) {
            is UIState.Loading -> LoadingScreen(
                text = stringResource(
                    R.string.loading_screen,
                    stringResource(R.string.title_brewery),
                ),
            )
            is UIState.Error -> Text(breweryApiState.error!!)
            is UIState.Success -> Brewery(brewery = breweryDetailState.brewery!!)
        }
    }
}

@Composable
fun Brewery(
    brewery: Brewery
) {
    BreweryDetail(brewery)
}
