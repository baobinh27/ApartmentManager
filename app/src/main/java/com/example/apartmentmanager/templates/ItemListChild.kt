package com.example.apartmentmanager.templates

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

//unused
@Composable
fun ItemListChild(
    modifier: Modifier,
    onClick: () -> Unit,
    title: String,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val cardPadding = screenWidth * 0.025f
    Button(
        onClick = onClick,
        modifier = Modifier.padding(horizontal = cardPadding * 2).width(screenWidth * 0.8f),
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
        shape = ShapeDefaults.ExtraSmall
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}