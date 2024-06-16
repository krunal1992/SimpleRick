package com.androidexample.simplerick.components.character

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.androidexample.network.models.domain.CharacterStatus

@Composable
fun CharacterStatusCircle(status: CharacterStatus, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(
                brush = Brush.radialGradient(
                    listOf(
                        Color.Black,
                        Color.Transparent
                    )
                ),
                shape = CircleShape
            )
            .size(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(color = status.color, shape = CircleShape)
        )
    }
}