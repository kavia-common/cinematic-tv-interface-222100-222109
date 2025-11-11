package org.example.app.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.app.data.SampleRepository
import org.example.app.navigation.Routes
import org.example.app.ui.components.PosterCard
import androidx.navigation.NavController
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect

/**
 * PUBLIC_INTERFACE
 * Home screen shows multiple content carousels (LazyRows) grouped by category.
 * Cards navigate to details/{id} on click.
 */
@Composable
fun HomeScreen(
    navController: NavController
) {
    val categories = SampleRepository.categories()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(horizontal = 12.dp)
    ) {
        items(categories) { cat ->
            Text(
                text = cat.name,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
            this@LazyColumn.item {
                Spacer(modifier = Modifier.height(12.dp))
            }
            this@LazyColumn.item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp)
                ) {
                    // Ensure predictable initial focus: first item in each row is focused when entering the row
                    items(cat.items.size) { index ->
                        val media = cat.items[index]
                        val requester = remember { FocusRequester() }
                        if (index == 0) {
                            LaunchedEffect(Unit) { requester.requestFocus() }
                        }
                        PosterCard(
                            title = media.title,
                            imageRes = media.posterResId,
                            onClick = { navController.navigate(Routes.detailsFor(media.id)) },
                            modifier = if (index == 0) Modifier.focusRequester(requester) else Modifier
                        )
                    }
                }
            }
        }
    }
}
