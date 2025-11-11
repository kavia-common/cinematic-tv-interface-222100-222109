package org.example.app.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.example.app.data.SampleRepository
import org.example.app.navigation.Routes
import org.example.app.ui.components.PosterCard

/**
 * PUBLIC_INTERFACE
 * Search screen with a simple query text field and filtered results row.
 * For TV, initial focus is placed on the query field.
 */
@Composable
fun SearchScreen(navController: NavController) {
    var query by remember { mutableStateOf("") }
    val results = remember(query) { SampleRepository.search(query) }

    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) { focusRequester.requestFocus() }

    androidx.compose.foundation.layout.Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Search",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier.focusRequester(focusRequester),
            singleLine = true,
            label = { Text("Type to search") }
        )

        Spacer(Modifier.height(24.dp))
        if (results.isEmpty() && query.isNotBlank()) {
            Text(
                text = "No results for \"$query\"",
                style = MaterialTheme.typography.bodyLarge
            )
        } else if (results.isNotEmpty()) {
            Text(
                text = "Results",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(Modifier.height(12.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) {
                items(results) { item ->
                    PosterCard(
                        title = item.title,
                        imageRes = item.posterResId,
                        onClick = { navController.navigate(Routes.detailsFor(item.id)) }
                    )
                }
            }
        }
    }
}
