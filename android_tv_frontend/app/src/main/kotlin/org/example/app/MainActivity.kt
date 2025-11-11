package org.example.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import org.example.app.navigation.AppNavHost
import org.example.app.navigation.Routes
import org.example.app.ui.theme.CinematicTVTheme
import androidx.compose.foundation.clickable
import androidx.compose.material3.Text

/**
 * PUBLIC_INTERFACE
 * Main activity and Compose entry point.
 *
 * Hosts the CinematicTVTheme and the Navigation Compose NavHost.
 * Renders a persistent TV-friendly top navigation row with initial focus,
 * wired to navigate to: home, search, settings.
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isDark by remember { mutableStateOf(false) }

            CinematicTVTheme(useDarkTheme = isDark) {
                val navController = rememberNavController()

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    TopNavBar(
                        onHome = { navController.navigate(Routes.Home) { launchSingleTop = true; popUpTo(Routes.Home) { inclusive = false } } },
                        onSearch = { navController.navigate(Routes.Search) { launchSingleTop = true } },
                        onSettings = { navController.navigate(Routes.Settings) { launchSingleTop = true } }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    // Content area
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 24.dp)
                    ) {
                        AppNavHost(
                            navController = navController,
                            isDarkTheme = isDark,
                            onToggleDark = { isDark = it }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TopNavBar(
    onHome: () -> Unit,
    onSearch: () -> Unit,
    onSettings: () -> Unit
) {
    val items = listOf(
        "Home" to onHome,
        "Search" to onSearch,
        "Settings" to onSettings
    )

    // Ensure initial focus goes to the first item on TV
    val firstFocusRequester = remember { FocusRequester() }
    var requested by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!requested) {
            requested = true
            firstFocusRequester.requestFocus()
        }
    }

    Row(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEachIndexed { index, pair ->
            val (label, onClick) = pair
            val isFirst = index == 0

            NavChip(
                label = label,
                onClick = onClick,
                modifier = Modifier
                    .then(
                        if (isFirst) Modifier.focusRequester(firstFocusRequester) else Modifier
                    )
            )
        }
    }
}

@Composable
private fun NavChip(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // A focusable TV chip using basic Compose; scales on focus visually via background emphasis
    Box(
        modifier = modifier
            .focusable()
            .focusProperties { canFocus = true }
            .clickable(onClick = onClick, role = Role.Button)
            .background(
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .align(Alignment.CenterStart)
        )
    }
}
