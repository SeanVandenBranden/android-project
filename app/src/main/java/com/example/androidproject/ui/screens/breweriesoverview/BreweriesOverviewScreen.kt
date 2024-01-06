package com.example.androidproject.ui.screens.breweriesoverview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.androidproject.R
import com.example.androidproject.model.breweries.Brewery
import com.example.androidproject.ui.common.ErrorSnackbar
import com.example.androidproject.ui.common.LoadingScreen
import com.example.androidproject.ui.common.UIState
import com.example.androidproject.ui.screens.breweriesoverview.components.Breweries
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicator
import eu.bambooapps.material3.pullrefresh.pullRefresh
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreweriesOverviewScreen(
    breweriesOverviewViewModel: BreweriesOverviewViewModel = hiltViewModel(),
    onViewDetailClicked: (Brewery) -> Unit,
) {
    val breweriesOverviewState by breweriesOverviewViewModel.uiState.collectAsState()
    val breweryApiState = breweriesOverviewViewModel.breweryApiState

    val pullRefreshState = rememberPullRefreshState(breweriesOverviewState.isRefreshing, onRefresh = breweriesOverviewViewModel::refreshBreweries)
    ErrorSnackbar(isError = breweriesOverviewState.isError, onErrorConsumed = breweriesOverviewViewModel::onErrorConsumed, stringResource(R.string.breweries_error))
    Box(
        modifier = Modifier
            .pullRefresh(pullRefreshState)
            .fillMaxWidth().testTag("breweriesOverview"),
    ) {
        when (breweryApiState) {
            is UIState.Loading -> LoadingScreen(
                text = stringResource(
                    R.string.loading_screen,
                    stringResource(R.string.title_breweries),
                ),
            )
            is UIState.Error -> Text(breweryApiState.error!!)
            is UIState.Success -> BreweriesOverview(
                breweries = breweriesOverviewState.breweries!!,
                onViewDetailClicked = onViewDetailClicked,
            )
        }
        PullRefreshIndicator(
            refreshing = breweriesOverviewState.isRefreshing,
            state = pullRefreshState,
            modifier = Modifier
                .padding(top = dimensionResource(R.dimen.padding_extra_large))
                .align(Alignment.TopCenter),
        )
    }
}

@Composable
fun BreweriesOverview(
    breweries: List<Brewery>,
    onViewDetailClicked: (Brewery) -> Unit,
) {
    Breweries(
        breweries,
        onViewDetailClicked,
    )
}
