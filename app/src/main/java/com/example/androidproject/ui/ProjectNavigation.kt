package com.example.androidproject.ui

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.androidproject.ui.ProjectDestinations.BREWERIES_METADATA_ROUTE
import com.example.androidproject.ui.ProjectDestinations.BREWERIES_ROUTE
import com.example.androidproject.ui.ProjectDestinations.RANDOM_BREWERY_ROUTE
import com.example.androidproject.ui.ProjectDestinationsArgs.BREWERY_ID_ARG
import com.example.androidproject.ui.ProjectScreens.BREWERIES_METADATA_SCREEN
import com.example.androidproject.ui.ProjectScreens.BREWERIES_SCREEN
import com.example.androidproject.ui.ProjectScreens.BREWERY_DETAIL_SCREEN
import com.example.androidproject.ui.ProjectScreens.RANDOM_BREWERY_SCREEN

/**
 * Screens used in [ProjectDestinations]
 */
object ProjectScreens {
    const val BREWERIES_SCREEN = "breweries"
    const val BREWERY_DETAIL_SCREEN = "detailBrewery"
    const val RANDOM_BREWERY_SCREEN = "randomBrewery"
    const val BREWERIES_METADATA_SCREEN = "metadata"
}

/**
 * Arguments used in [ProjectDestinations] routes
 */
object ProjectDestinationsArgs {
    const val BREWERY_ID_ARG = "breweryId"
}

/**
 * Destinations used in the [ProjectApp]
 */
object ProjectDestinations {
    const val BREWERIES_ROUTE = BREWERIES_SCREEN
    const val BREWERY_DETAIL_ROUTE = "$BREWERY_DETAIL_SCREEN/{$BREWERY_ID_ARG}"
    const val RANDOM_BREWERY_ROUTE = RANDOM_BREWERY_SCREEN
    const val BREWERIES_METADATA_ROUTE = BREWERIES_METADATA_SCREEN
}

/**
 * Models the navigation actions in the app.
 */
class ProjectNavigationActions(private val navController: NavHostController) {
    private fun navigateToMenuItem(route: String) {
        navController.navigate(route) {
            // Pop up to the start destination of the graph to avoid building up a large stack of destinations
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }

    fun navigateToBreweries() {
        navigateToMenuItem(BREWERIES_ROUTE)
    }
    fun navigateToBreweryDetail(breweryId: String) {
        navController.navigate("$BREWERY_DETAIL_SCREEN/$breweryId") {
            popUpTo(BREWERIES_SCREEN)
        }
    }
    fun navigateToBreweriesWithReload() {
        navController.navigate(BREWERIES_ROUTE) {
            popUpTo(BREWERIES_SCREEN) {
                inclusive = true
            }
        }
    }

    fun navigateToRandomBrewery() {
        navigateToMenuItem(RANDOM_BREWERY_ROUTE)
    }

    fun navigateToBreweriesMetadata() {
        navigateToMenuItem(BREWERIES_METADATA_ROUTE)
    }
}
