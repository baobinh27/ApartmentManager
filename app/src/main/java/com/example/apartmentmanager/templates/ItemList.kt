package com.example.apartmentmanager.templates

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

@Composable
fun ItemList(
    modifier: Modifier,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    OutlinedButton(
        onClick = onClick,
        shape = ShapeDefaults.ExtraSmall,
        modifier = Modifier.fillMaxWidth().height(screenWidth * 0.15f),
        content = content,
        colors = androidx.compose.material3.ButtonDefaults.outlinedButtonColors(
            containerColor = androidx.compose.material3.MaterialTheme.colorScheme.secondary,
            contentColor = androidx.compose.material3.MaterialTheme.colorScheme.onSecondary,
            disabledContainerColor = androidx.compose.material3.MaterialTheme.colorScheme.tertiaryContainer,
            disabledContentColor = androidx.compose.material3.MaterialTheme.colorScheme.onTertiaryContainer
        )
    )
}