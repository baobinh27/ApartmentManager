package com.example.apartmentmanager.templates

import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

//Khuôn mẫu các thẻ chức năng
@Composable
fun InfoCard(
    icon: ImageVector,
    title: String,
    onFunctionChange: (Int) -> Unit,
    functionId: Int
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val cardPadding = screenWidth * 0.05f
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        modifier = Modifier
            .padding(cardPadding)
            .width(screenWidth * 0.4f)
            .height(screenWidth * 0.4f),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary
        ),
        onClick = { onFunctionChange(functionId) }
    ) {
        Column(
            modifier = Modifier
                .padding(cardPadding)
                .width(screenWidth * 0.3f)
                .height(screenWidth * 0.3f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier
                    .scale(2f)
                    .padding(bottom = cardPadding, top = cardPadding * 0.8f),
                tint = MaterialTheme.colorScheme.onSecondary
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondary,
                textAlign = TextAlign.Center
            )
        }
    }
}