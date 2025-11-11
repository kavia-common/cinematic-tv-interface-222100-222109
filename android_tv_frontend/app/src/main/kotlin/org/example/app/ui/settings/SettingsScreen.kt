package org.example.app.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * PUBLIC_INTERFACE
 * Settings screen showing a theme toggle persisted in-memory.
 * Consumers should hoist and persist to DataStore in a future update.
 */
@Composable
fun SettingsScreen(
    isDark: Boolean,
    onToggleDark: (Boolean) -> Unit
) {
    androidx.compose.foundation.layout.Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text("Settings", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(24.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Dark theme", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.weight(1f))
            Switch(
                checked = isDark,
                onCheckedChange = onToggleDark
            )
        }
        Spacer(Modifier.height(12.dp))
        Text(
            text = "Theme preference is kept in memory only for this session.",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
