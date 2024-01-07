package com.example.androidproject.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.outlined.Style
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.androidproject.MainViewModel
import com.example.androidproject.R
import com.example.androidproject.ui.navigation.BottomNavBar
import com.example.androidproject.ui.navigation.NavigationMenuItem

@Composable
fun ProjectApp(
    mainViewModel: MainViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController(),
    navActions: ProjectNavigationActions = remember(navController) {
        ProjectNavigationActions(navController)
    },
    startDestination: String = ProjectDestinations.BREWERIES_ROUTE,
) {
    mainViewModel.setContext(LocalContext.current)

    val breweriesMenuItem = NavigationMenuItem(
        route = ProjectDestinations.BREWERIES_ROUTE,
        title = R.string.title_breweries,
        Icons.Filled.ReceiptLong,
        navigationAction = { navActions.navigateToBreweries() },
    )

    val randomBreweryMenuItem = NavigationMenuItem(
        route = ProjectDestinations.RANDOM_BREWERY_ROUTE,
        title = R.string.title_random_breweries,
        Icons.Outlined.Style,
        navigationAction = { navActions.navigateToRandomBrewery() },
    )

    val breweryMetadataMenuItem = NavigationMenuItem(
        route = ProjectDestinations.BREWERIES_METADATA_ROUTE,
        title = R.string.title_metadata,
        Icons.Outlined.Style,
        navigationAction = { navActions.navigateToBreweriesMetadata() },
    )

    val menuItems = arrayOf(breweriesMenuItem, randomBreweryMenuItem, breweryMetadataMenuItem)
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: startDestination

    MenuScaffold(currentRoute = currentRoute, menuItems = menuItems) {
        ProjectAppNavGraph(navController = navController)
    }
}

val LocalSnackbarHostState = compositionLocalOf<SnackbarHostState> {
    error("No Snackbar Host State provided")
}

@Composable
fun MenuScaffold(currentRoute: String, menuItems: Array<NavigationMenuItem>, content: @Composable () -> Unit) {
    val snackbarHostState = remember { SnackbarHostState() }

    CompositionLocalProvider(
        LocalSnackbarHostState provides snackbarHostState,
    ) {
        Scaffold(
            modifier = Modifier.testTag(stringResource(R.string.test_menuscaffold)),
            bottomBar = {
                if (menuItems.any { item -> item.route == currentRoute }) {
                    BottomNavBar(currentRoute, menuItems)
                }
            },
            snackbarHost = { SnackbarHost(snackbarHostState) },
        ) { innerPadding ->
            Column(Modifier.padding(innerPadding)) {
                content()
            }
        }
    }
}
