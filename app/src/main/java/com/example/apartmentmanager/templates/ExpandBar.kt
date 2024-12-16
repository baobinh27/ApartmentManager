package com.example.apartmentmanager.templates

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.apartmentmanager.R
import com.example.apartmentmanager.ui.theme.ApartmentManagerTheme

@Composable
fun ExpandBar(
    onClick: () -> Unit = {},
    activated: Boolean,
    iconSize: Float = 0.1f,
    cardColor: Color = MaterialTheme.colorScheme.secondary,
    barContent: @Composable () -> Unit,
    expandedContent: @Composable () -> Unit
) {

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val tint = when (cardColor) {
        MaterialTheme.colorScheme.secondary -> MaterialTheme.colorScheme.onSecondary
        MaterialTheme.colorScheme.primary -> MaterialTheme.colorScheme.onPrimary
        MaterialTheme.colorScheme.tertiary -> MaterialTheme.colorScheme.onTertiary
        else -> MaterialTheme.colorScheme.onBackground
    }

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        modifier = Modifier
            .padding(
                top = screenWidth * 0.05f,
                start = screenWidth * 0.05f,
                end = screenWidth * 0.05f
            )
            .fillMaxWidth(),
        colors = CardColors(
            cardColor.copy(alpha = 0.9f),
            Color.Unspecified,
            Color.Unspecified,
            Color.Unspecified
        ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(screenWidth * 0.05f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            barContent()
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = if (!activated) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                contentDescription = if (!activated) "Show Details" else "Hide Details",
                modifier = Modifier.height(screenWidth * iconSize),
                tint = tint
            )
        }
        AnimatedVisibility(
            visible = activated,
            enter = androidx.compose.animation.expandVertically(),
            exit = androidx.compose.animation.shrinkVertically()
        ) {
            Column() {
                expandedContent()
            }

        }
    }
}