package org.example.app.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.example.app.R
import org.example.app.data.MediaItem
import org.example.app.data.SampleRepository
import org.example.app.navigation.Routes
import org.example.app.ui.components.PosterCard
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.res.painterResource

/**
 * PUBLIC_INTERFACE
 * Home screen displays:
 * - A cinematic featured banner with blurred backdrop and a prominent "Play Now" CTA
 * - 3 titled sections below with LazyRow carousels fed from SampleRepository lists
 * - Dark gradient background and fade-in animations
 * - TV-friendly D-pad focus navigation
 */
@Composable
fun HomeScreen(
    navController: NavController
) {
    // Use explicit repository lists as requested
    val recommended: List<MediaItem> = SampleRepository.recommended()
    val topPicks: List<MediaItem> = SampleRepository.topPicks()
    val recentlyAdded: List<MediaItem> = SampleRepository.recentlyAdded()
    val sections: List<Pair<String, List<MediaItem>>> = listOf(
        "Recommended" to recommended,
        "Top Picks" to topPicks,
        "Recently Added" to recentlyAdded
    )
    // The banner should feature Interstellar prominently if present
    val featuredItem = recommended.firstOrNull { it.title.equals("Interstellar", ignoreCase = true) }
        ?: recommended.firstOrNull()
        ?: topPicks.firstOrNull()
        ?: recentlyAdded.first()

    val bannerBackgrounds = SampleRepository.bannerBackgrounds()
    val bannerUrl = remember(bannerBackgrounds) { bannerBackgrounds.firstOrNull() ?: featuredItem.backdropUrl }

    // Focus requesters
    val bannerCtaFocus = remember { FocusRequester() }
    val firstRowFirstItemFocus = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        bannerCtaFocus.requestFocus()
    }

    // Background gradient (#0D0D0D → #1A1A1A)
    val bgGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF0D0D0D), Color(0xFF1A1A1A))
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGradient)
            .padding(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(28.dp),
        contentPadding = PaddingValues(horizontal = 12.dp)
    ) {
        item {
            FeaturedBanner(
                item = featuredItem,
                bannerImageUrl = bannerUrl ?: featuredItem.backdropUrl,
                onPlay = { media -> navController.navigate(Routes.detailsFor(media.id)) },
                ctaFocusRequester = bannerCtaFocus,
                nextDownRequester = firstRowFirstItemFocus
            )
        }

        // Build out requested sections
        items(sections) { (title, items) ->
            SectionRow(
                title = title,
                items = items,
                navTo = { id -> navController.navigate(Routes.detailsFor(id)) },
                firstItemFocusRequester = if (title == sections.first().first) firstRowFirstItemFocus else null
            )
        }
    }
}

/**
 * PUBLIC_INTERFACE
 * A large featured banner displaying a backdrop URL (if available) with subtle blur,
 * title/description from the selected item, and a primary "Play Now" CTA.
 */
@Composable
private fun FeaturedBanner(
    item: MediaItem,
    bannerImageUrl: String?,
    onPlay: (MediaItem) -> Unit,
    ctaFocusRequester: FocusRequester,
    nextDownRequester: FocusRequester?
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp)
            .clipToBounds()
    ) {
        val painter: AsyncImagePainter = rememberAsyncImagePainter(
            model = bannerImageUrl ?: item.backdropUrl,
            placeholder = painterResource(id = R.drawable.placeholder_backdrop),
            error = painterResource(id = R.drawable.placeholder_backdrop)
        )

        androidx.compose.foundation.Image(
            painter = painter,
            contentDescription = "Featured Banner",
            modifier = Modifier
                .matchParentSize()
                .blur(radius = 8.dp),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0x00000000),
                            Color(0x66000000),
                            Color(0xCC000000)
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .matchParentSize()
                .padding(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = "Interstellar",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = "A team travels through a wormhole in space in an attempt to ensure humanity's survival.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.85f),
                maxLines = 2
            )
            Spacer(Modifier.height(12.dp))

            Button(
                onClick = { onPlay(item) },
                modifier = Modifier
                    .focusRequester(ctaFocusRequester),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00BCD4),
                    contentColor = Color.Black
                )
            ) {
                Text(text = "Play Now")
            }
        }
    }
}

/**
 * PUBLIC_INTERFACE
 * A titled row section that fades in and shows a LazyRow of PosterCard items.
 * The first item can receive a provided FocusRequester for coordinated focus traversal.
 */
@Composable
private fun SectionRow(
    title: String,
    items: List<MediaItem>,
    navTo: (String) -> Unit,
    firstItemFocusRequester: FocusRequester? = null
) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 12.dp)
            ) {
                items(items) { media ->
                    val isFirst = items.firstOrNull()?.id == media.id
                    PosterCard(
                        title = media.title,
                        imageRes = media.posterResId,
                        imageUrl = media.posterUrl,
                        onClick = { navTo(media.id) },
                        modifier = if (isFirst && firstItemFocusRequester != null) {
                            Modifier.focusRequester(firstItemFocusRequester)
                        } else {
                            Modifier
                        }
                    )
                }
            }
        }
    }
}
