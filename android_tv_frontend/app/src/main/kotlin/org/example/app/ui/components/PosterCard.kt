package org.example.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.tv.material3.Card
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.Text
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.toArgb

/**
 * PUBLIC_INTERFACE
 * TV Poster card that scales when focused and triggers onClick via D‑pad/enter.
 */
@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun PosterCard(
    title: String,
    imageRes: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    var focused by remember { mutableStateOf(false) }
    val scale = if (focused) 1.08f else 1.0f
    val corner = 12.dp
    val borderWidth = if (focused) 2.dp else 0.dp
    val borderColor = if (focused) MaterialTheme.colorScheme.primary else Color.Transparent
    val glowColor = MaterialTheme.colorScheme.primary.copy(alpha = if (focused) 0.55f else 0f)

    Card(
        onClick = onClick,
        modifier = modifier
            .scale(scale)
            .focusable()
            .onFocusChanged { state -> focused = state.hasFocus }
            .clip(RoundedCornerShape(corner))
            // soft outer glow to mimic elevation when focused
            .drawBehind {
                if (focused) {
                    drawIntoCanvas { canvas ->
                        val paint = Paint()
                        paint.asFrameworkPaint().apply {
                            isAntiAlias = true
                            color = glowColor.toArgb()
                            setShadowLayer(24f, 0f, 10f, glowColor.copy(alpha = 0.9f).toArgb())
                        }
                        val inset = -8f
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
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,
                modifier = Modifier
                    .aspectRatio(2f / 3f), // typical poster ratio
                contentScale = ContentScale.Crop
            )
            // Title overlay
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .background(
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.55f)
                    )
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
