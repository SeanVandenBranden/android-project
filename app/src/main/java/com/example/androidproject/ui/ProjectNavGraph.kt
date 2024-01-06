package com.example.androidproject.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidproject.ui.ProjectDestinations.BREWERIES_ROUTE
import com.example.androidproject.ui.screens.breweriesoverview.BreweriesOverviewScreen

@Composable
fun AdminAppNavGraph(
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
            // TODO make this screen
            BreweriesOverviewScreen(onViewDetailClicked = { brewery -> navActions.navigateToBreweryDetail(brewery.id) })
        }
        /*composable(
            BREWERY_DETAIL_ROUTE,
            arguments = listOf(
                navArgument(BREWERY_ID_ARG) { type = NavType.IntType },
            ),
        ) { entry ->
            val breweryId = entry.arguments?.getInt(BREWERY_ID_ARG)
            BreweryDetailScreen(//TODO make this screen
                topAppbarTitle = "",//TODO brewery name here
                onBack = navActions::navigateToBreweriesWithReload,
            )
        }
        composable(RANDOM_BREWERY_ROUTE) {
            RandomBreweryScreen()//TODO make this screen
        }
        composable(BREWERIES_METADATA_ROUTE) {
            BreweriesMetadataScreen()//TODO make this screen
        }*/
    }
}
