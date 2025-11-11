package org.example.app.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Card
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.Text
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource

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

    Card(
        onClick = onClick,
        modifier = modifier
            .scale(scale)
            .focusable()
            .clip(RoundedCornerShape(10.dp)),
        interactionSource = CardDefaults.interactionSource(
            onFocus = { focused = true },
            onBlur = { focused = false }
        ),
        colors = CardDefaults.colors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0x14000000))
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,
                modifier = Modifier
                    .aspectRatio(2f / 3f), // typical poster ratio
                contentScale = ContentScale.Crop
            )
            // Simple title overlay
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .background(Color(0x66000000))
                    .padding(8.dp)
            ) {
                Text(text = title)
            }
        }
    }
}
