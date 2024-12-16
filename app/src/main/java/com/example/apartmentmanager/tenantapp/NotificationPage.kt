package com.example.apartmentmanager.tenantapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.apartmentmanager.templates.InfoPage

@Composable
fun NotificationPage(
    onFunctionChange: (Int) -> Unit
) {
    InfoPage(
        title = "Notifications",
        onBackClick = { onFunctionChange(0) },
    ) {

    }
}