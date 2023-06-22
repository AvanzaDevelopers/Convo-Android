package com.hotel.theconvo.presentation.composableItems

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.LocalContentColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hotel.theconvo.ui.theme.yellowColor

@Composable
fun RatingBar(rating: Float,modifier: Modifier) {
    Row {
        repeat(5) { index ->
            val icon = if (index < rating) Icons.Default.Star else Icons.Outlined.StarBorder
            StarIcon(icon, yellowColor)
        }
    }
}

@Composable
fun StarIcon(icon: ImageVector, starColor: Color) {
    Icon(
        imageVector = icon,
        contentDescription = null,
        modifier = Modifier.size(15.dp),
        tint = starColor
    )
}