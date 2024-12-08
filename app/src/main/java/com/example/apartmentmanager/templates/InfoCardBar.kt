package com.example.apartmentmanager.templates

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

//Khuôn mẫu các thẻ chức năng
@Composable
fun InfoCardBar(
    icon1: ImageVector? = null,
    painter1: Painter? = null,
    tint1: Color = Color.Unspecified,
    size1: Float = 0.2f,
    textColor: Color = MaterialTheme.colorScheme.onSecondary,
    cardColor: Color = MaterialTheme.colorScheme.secondary,
    title: String,
    icon2: ImageVector? = null,
    painter2: Painter? = null,
    tint2: Color = Color.Unspecified,
    size2: Float = 0.2f,
    onClick: () -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val cardPadding = screenWidth * 0.05f
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        modifier = Modifier
            .padding(top = cardPadding, start = cardPadding, end = cardPadding)
            .width(screenWidth * 0.9f),
        colors = CardDefaults.cardColors(
            containerColor = cardColor.copy(alpha = 0.9f)
        ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(cardPadding).align(Alignment.Start),
            verticalAlignment = Alignment.CenterVertically // Căn giữa các phần tử theo chiều dọc
        ) {
            if (icon1 != null) {
                Icon(
                    imageVector = icon1,
                    contentDescription = title,
                    modifier = Modifier.height(screenWidth * size1).width(screenWidth * size1),
                    tint = tint1
                )
            }
            if (painter1 != null) {
                Icon(
                    painter = painter1,
                    contentDescription = title,
                    modifier = Modifier.height(screenWidth * size1).width(screenWidth * size1),
                    tint = tint1
                )
            }
            if (icon1 != null || painter1 != null) {
                Spacer(modifier = Modifier.width(screenWidth * 0.05f))
            }
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                color = textColor,
            )
            Spacer(modifier = Modifier.weight(1f))
            if (icon2 != null) {
                Icon(
                    imageVector = icon2,
                    contentDescription = title,
                    modifier = Modifier.height(screenWidth * size2).width(screenWidth * size2),
                    tint = tint2,
                )
            }
            if (painter2 != null) {
                Icon(
                    painter = painter2,
                    contentDescription = title,
                    modifier = Modifier.height(screenWidth * size2).width(screenWidth * size2),
                    tint = tint2
                )
            }
        }
    }
}