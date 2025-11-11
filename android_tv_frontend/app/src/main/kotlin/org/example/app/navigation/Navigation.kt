package org.example.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.example.app.ui.details.DetailsScreen
import org.example.app.ui.home.HomeScreen
import org.example.app.ui.search.SearchScreen
import org.example.app.ui.settings.SettingsScreen

// PUBLIC_INTERFACE
object Routes {
    /** Home route. */
    const val Home = "home"

    /** Details route with id argument. Example: details/42 */
    const val Details = "details/{id}"

    /** Search route. */
    const val Search = "search"

    /** Settings route. */
    const val Settings = "settings"

    /** Build a details route for a concrete id. */
    // PUBLIC_INTERFACE
    fun detailsFor(id: String): String = "details/$id"
}

/**
 * PUBLIC_INTERFACE
 * App NavHost that wires Home, Details(id), Search, and Settings destinations.
 *
 * Parameters:
 * - navController: NavHostController to drive navigation.
 * - isDarkTheme: current dark theme setting.
 * - onToggleDark: callback to update theme setting.
 *
 * Returns:
 * - Nothing. Renders composable destinations within NavHost.
 */
@Composable
fun AppNavHost(
    navController: NavHostController,
    isDarkTheme: Boolean,
    onToggleDark: (Boolean) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Home
    ) {
        composable(Routes.Home) {
            HomeScreen(navController = navController)
        }

        composable(
            route = Routes.Details,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: "unknown"
            DetailsScreen(id = id)
        }

        composable(Routes.Search) {
            SearchScreen(navController = navController)
        }

        composable(Routes.Settings) {
            SettingsScreen(
                isDark = isDarkTheme,
                onToggleDark = onToggleDark
            )
        }
    }
}
