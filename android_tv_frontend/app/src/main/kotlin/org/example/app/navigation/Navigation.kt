package org.example.app.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.compose.material3.Text

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
 *
 * Returns:
 * - Nothing. Renders composable destinations within NavHost.
 */
@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.Home
    ) {
        composable(Routes.Home) {
            ScreenScaffold(
                title = "Home",
                subtitle = "Cinematic rows and featured content"
            )
        }

        composable(
            route = Routes.Details,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: "unknown"
            ScreenScaffold(
                title = "Details",
                subtitle = "Item id: $id"
            )
        }

        composable(Routes.Search) {
            ScreenScaffold(
                title = "Search",
                subtitle = "Find movies and shows"
            )
        }

        composable(Routes.Settings) {
            ScreenScaffold(
                title = "Settings",
                subtitle = "Theme, preferences, and about"
            )
        }
    }
}

/**
 * Simple TV-friendly placeholder screen scaffold for each destination.
 * Large typography and generous spacing for 10-foot UI.
 */
@Composable
private fun ScreenScaffold(
    title: String,
    subtitle: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = subtitle,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.85f),
            style = MaterialTheme.typography.titleLarge
        )
    }
}
