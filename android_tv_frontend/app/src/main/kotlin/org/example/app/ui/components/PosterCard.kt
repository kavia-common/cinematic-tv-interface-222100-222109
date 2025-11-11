package org.example.app.ui.components

import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Card
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Text
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import org.example.app.R

/**
 * PUBLIC_INTERFACE
 * TV Poster card that scales when focused and triggers onClick via D‑pad/enter.
 * Uses Coil to load remote images where available, with rounded corners (12.dp),
 * teal-accented soft glow on focus, and spring-smoothed scale/alpha for polish.
 */
@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun PosterCard(
    title: String,
    imageRes: Int,
    imageUrl: String? = null,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    var focused by remember { mutableStateOf(false) }

    // Focus-in overshoot effect: 1.0 -> 1.12 -> 1.10, focus-out back to 1.0
    val baseTarget = if (focused) 1.10f else 1.0f
    val scale by animateFloatAsState(
        targetValue = baseTarget,
        animationSpec =
        if (focused) {
            // keyframes to briefly overshoot
            keyframes {
                durationMillis = 220
                1.0f at 0
                1.12f at 140 with EaseOutCubic
                1.10f at 220
            }
        } else {
            spring(dampingRatio = Spring.DampingRatioNoBouncy, stiffness = Spring.StiffnessLow)
        },
        label = "posterScaleOvershoot"
    )

    // Stronger but soft teal glow
    val accentTeal = Color(0xFF00BCD4)
    val glowAlpha by animateFloatAsState(
        targetValue = if (focused) 0.45f else 0f,
        animationSpec = tween(durationMillis = 200, easing = EaseOutCubic),
        label = "posterGlowAlpha"
    )
    val corner = 12.dp

    val borderWidth = if (focused) 2.dp else 0.dp
    val borderColor = if (focused) accentTeal.copy(alpha = 0.95f) else Color.Transparent

    val painter: AsyncImagePainter = rememberAsyncImagePainter(
        model = imageUrl,
        placeholder = painterResource(id = imageRes),
        error = painterResource(id = imageRes)
    )

    Card(
        onClick = onClick,
        modifier = modifier
            .scale(scale)
            .focusable()
            .onFocusChanged { state -> focused = state.hasFocus }
            .clip(RoundedCornerShape(corner))
            .drawBehind {
                if (glowAlpha > 0f) {
                    drawIntoCanvas { canvas ->
                        val paint = Paint()
                        val glow = accentTeal.copy(alpha = glowAlpha)
                        paint.asFrameworkPaint().apply {
                            isAntiAlias = true
                            color = glow.toArgb()
                            // Increase radius and soften
                            setShadowLayer(56f, 0f, 12f, glow.copy(alpha = 0.85f).toArgb())
                        }
                        val inset = -14f
                        canvas.drawRoundRect(
                            inset,
                            inset,
                            size.width - inset,
                            size.height - inset,
                            corner.toPx(),
                            corner.toPx(),
                            paint
                        )
                    }
                }
            }
            .border(borderWidth, borderColor, RoundedCornerShape(corner)),
        colors = CardDefaults.colors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(corner))
                .background(Color(0x14000000))
        ) {
            androidx.compose.foundation.Image(
                painter = painter,
                contentDescription = title,
                modifier = Modifier.aspectRatio(2f / 3f),
                contentScale = ContentScale.Crop
            )
            // Title overlay
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.55f))
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}
