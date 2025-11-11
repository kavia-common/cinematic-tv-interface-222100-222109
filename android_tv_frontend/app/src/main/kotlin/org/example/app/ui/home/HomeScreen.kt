package org.example.app.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.delay
import org.example.app.R
import org.example.app.data.MediaItem
import org.example.app.data.SampleRepository
import org.example.app.navigation.Routes
import org.example.app.ui.components.PosterCard

/**
 * PUBLIC_INTERFACE
 * Home screen displays:
 * - A cinematic featured banner with parallax and crossfade backdrop plus a "Play Now" CTA
 * - Multiple titled sections below with LazyRow carousels (including Sci‑Fi, Horror, Child, Romantic, Thriller)
 * - Dark gradient background and staggered fade/scale animations
 * - TV-friendly D-pad focus navigation
 */
@Composable
fun HomeScreen(
    navController: NavController
) {
    // Repository lists
    val recommended: List<MediaItem> = SampleRepository.recommended()
    val topPicks: List<MediaItem> = SampleRepository.topPicks()
    val recentlyAdded: List<MediaItem> = SampleRepository.recentlyAdded()
    val sciFi: List<MediaItem> = SampleRepository.sciFi()
    val horror: List<MediaItem> = SampleRepository.horror()
    val child: List<MediaItem> = SampleRepository.child()
    val romantic: List<MediaItem> = SampleRepository.romantic()
    val thriller: List<MediaItem> = SampleRepository.thriller()

    val sections: List<Pair<String, List<MediaItem>>> = listOf(
        "Recommended" to recommended,
        "Top Picks" to topPicks,
        "Recently Added" to recentlyAdded,
        "Sci-Fi" to sciFi,
        "Horror" to horror,
        "Child" to child,
        "Romantic" to romantic,
        "Thriller" to thriller
    )

    // Feature Interstellar if present
    val featuredItem = recommended.firstOrNull { it.title.equals("Interstellar", ignoreCase = true) }
        ?: recommended.firstOrNull()
        ?: topPicks.firstOrNull()
        ?: recentlyAdded.first()

    val bannerBackgrounds = SampleRepository.bannerBackgrounds()

    // Focus requesters
    val bannerCtaFocus = remember { androidx.compose.ui.focus.FocusRequester() }
    val firstRowFirstItemFocus = remember { androidx.compose.ui.focus.FocusRequester() }

    LaunchedEffect(Unit) {
        bannerCtaFocus.requestFocus()
    }

    // Background gradient (#0D0D0D → #1A1A1A)
    val bgGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF0D0D0D), Color(0xFF1A1A1A))
    )

    val listState = remember { LazyListState() }

    LazyColumn(
        state = listState,
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
                bannerImageUrls = bannerBackgrounds.ifEmpty { listOfNotNull(featuredItem.backdropUrl) },
                onPlay = { media -> navController.navigate(Routes.detailsFor(media.id)) },
                ctaFocusRequester = bannerCtaFocus,
                nextDownRequester = firstRowFirstItemFocus,
                listState = listState
            )
        }

        // Build out sections with titles fading before items and per-item stagger
        items(sections.size) { index ->
            val (title, itemsList) = sections[index]
            SectionRow(
                title = title,
                items = itemsList,
                navTo = { id -> navController.navigate(Routes.detailsFor(id)) },
                firstItemFocusRequester = if (index == 0) firstRowFirstItemFocus else null,
                rowIndex = index
            )
        }
    }
}

/**
 * PUBLIC_INTERFACE
 * A large featured banner displaying a backdrop URL set with subtle blur,
 * parallax on vertical scroll, and crossfade rotation between images.
 * Includes title/description and a primary "Play Now" CTA.
 */
@Composable
private fun FeaturedBanner(
    item: MediaItem,
    bannerImageUrls: List<String>,
    onPlay: (MediaItem) -> Unit,
    ctaFocusRequester: androidx.compose.ui.focus.FocusRequester,
    nextDownRequester: androidx.compose.ui.focus.FocusRequester?,
    listState: LazyListState
) {
    // Crossfade index that rotates every few seconds
    var index by remember { mutableIntStateOf(0) }
    LaunchedEffect(bannerImageUrls) {
        while (bannerImageUrls.isNotEmpty()) {
            delay(4000)
            index = (index + 1) % bannerImageUrls.size
        }
    }

    // Subtle parallax based on scroll offset
    val offsetFraction by remember {
        derivedStateOf {
            val first = listState.firstVisibleItemScrollOffset.coerceAtMost(200)
            first / 200f
        }
    }
    val parallax by animateFloatAsState(
        targetValue = -16f * offsetFraction, // move up to -16.dp
        animationSpec = tween(300, easing = LinearOutSlowInEasing),
        label = "bannerParallax"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp)
            .clipToBounds()
            .padding(top = parallax.dp) // subtle parallax shift
    ) {
        Crossfade(targetState = index, animationSpec = tween(600, easing = EaseOutCubic), label = "bannerCrossfade") { i ->
            val url = bannerImageUrls.getOrNull(i) ?: item.backdropUrl
            val painter: AsyncImagePainter = rememberAsyncImagePainter(
                model = url,
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
        }

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

        // Fade the text/CTA in smoothly
        var bannerVisible by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) { bannerVisible = true }

        AnimatedVisibility(
            visible = bannerVisible,
            enter = fadeIn(animationSpec = tween(260, easing = EaseOutCubic)),
            exit = fadeOut(animationSpec = tween(150))
        ) {
            Column(
                modifier = Modifier
                    .matchParentSize()
                    .padding(horizontal = 16.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = item.overview,
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
}

/**
 * PUBLIC_INTERFACE
 * A titled row section where the title fades in slightly before items.
 * Items use per-item staggered fade+scale-in animation.
 * The first item can receive a provided FocusRequester for coordinated focus traversal.
 */
@Composable
private fun SectionRow(
    title: String,
    items: List<MediaItem>,
    navTo: (String) -> Unit,
    firstItemFocusRequester: androidx.compose.ui.focus.FocusRequester? = null,
    rowIndex: Int
) {
    var titleVisible by remember { mutableStateOf(false) }
    var rowVisible by remember { mutableStateOf(false) }
    val rowDelay = 80 * rowIndex

    LaunchedEffect(Unit) {
        delay((rowDelay).toLong())
        titleVisible = true
        delay(80) // title fades before items
        rowVisible = true
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        AnimatedVisibility(
            visible = titleVisible,
            enter = fadeIn(animationSpec = tween(durationMillis = 240, easing = EaseOutCubic)),
            exit = fadeOut(animationSpec = tween(durationMillis = 120))
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))

        AnimatedVisibility(
            visible = rowVisible,
            enter = fadeIn(animationSpec = tween(durationMillis = 220, easing = EaseOutCubic)),
            exit = fadeOut(animationSpec = tween(durationMillis = 160))
        ) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 12.dp)
            ) {
                itemsIndexed(items) { idx, media ->
                    // Per-item stagger with fade+scale in
                    var itemVisible by remember { mutableStateOf(false) }
                    LaunchedEffect(Unit) {
                        delay((idx * 40L)) // 40ms stagger
                        itemVisible = true
                    }
                    val itemAlpha by animateFloatAsState(
                        targetValue = if (itemVisible) 1f else 0f,
                        animationSpec = tween(220, easing = EaseOutCubic),
                        label = "itemAlpha"
                    )
                    val itemScale by animateFloatAsState(
                        targetValue = if (itemVisible) 1f else 0.92f,
                        animationSpec = tween(220, easing = EaseOutCubic),
                        label = "itemScale"
                    )

                    val baseModifier =
                        if (idx == 0 && firstItemFocusRequester != null) {
                            Modifier.focusRequester(firstItemFocusRequester)
                        } else {
                            Modifier
                        }

                    Box(
                        modifier = baseModifier
                            .graphicsLayer {
                                alpha = itemAlpha
                                scaleX = itemScale
                                scaleY = itemScale
                            }
                    ) {
                        PosterCard(
                            title = media.title,
                            imageRes = media.posterResId,
                            imageUrl = media.posterUrl,
                            onClick = { navTo(media.id) }
                        )
                    }
                }
            }
        }
    }
}
