package com.example.androidproject.ui.screens.brewerydetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreweryDetailScreen(onBack: () -> Unit, viewModel: BreweryDetailViewModel = hiltViewModel()) {
    val breweryDetailState by viewModel.uiState.collectAsState()
    val breweryApiState = viewModel.breweryApiState

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag("quotationDetailScaffold"),
        topBar = {
            TopAppBar(
                title = {
                    if (breweryApiState is UIState.Success) {
                        breweryDetailState.brewery?.let { Text(text = it.name) }
                    } else {
                        breweryDetailState.name?.let { Text(it) }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, stringResource(id = R.string.contentDescription_menu_back))
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            )
        },
    ) { innerPadding ->
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(innerPadding)
            .testTag("breweriesOverview"),) {
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
}


@Composable
fun Brewery(
    brewery: Brewery
) {
    BreweryDetail(brewery)
}
