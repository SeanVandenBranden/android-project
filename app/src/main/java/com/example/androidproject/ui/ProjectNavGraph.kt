package com.example.androidproject.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.androidproject.ui.ProjectDestinations.BREWERIES_ROUTE
import com.example.androidproject.ui.ProjectDestinations.BREWERY_DETAIL_ROUTE
import com.example.androidproject.ui.ProjectDestinationsArgs.BREWERY_ID_ARG
import com.example.androidproject.ui.ProjectDestinationsArgs.BREWERY_NAME_ARG
import com.example.androidproject.ui.screens.breweriesoverview.BreweriesOverviewScreen
import com.example.androidproject.ui.screens.brewerydetail.BreweryDetailScreen

@Composable
fun ProjectAppNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = BREWERIES_ROUTE,
    navActions: ProjectNavigationActions = remember(navController) {
        ProjectNavigationActions(navController)
    },
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(BREWERIES_ROUTE) {
            BreweriesOverviewScreen(onViewDetailClicked = { brewery -> navActions.navigateToBreweryDetail(brewery.id) })
        }
        composable(
            BREWERY_DETAIL_ROUTE,
            arguments = listOf(
                navArgument(BREWERY_ID_ARG) { type = NavType.StringType }
            ),
        ) {
            BreweryDetailScreen(onBack = navActions::navigateToBreweriesWithReload)
        }
        /*composable(RANDOM_BREWERY_ROUTE) {
            RandomBreweryScreen()//TODO make this screen
        }
        composable(BREWERIES_METADATA_ROUTE) {
            BreweriesMetadataScreen()//TODO make this screen
        }*/
    }
}
