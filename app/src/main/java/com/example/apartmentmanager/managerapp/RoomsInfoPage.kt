package com.example.apartmentmanager.managerapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.apartmentmanager.templates.InfoPage

@Composable
fun RoomsInfoPage(
    modifier: Modifier = Modifier,
    onFunctionChange: (Int) -> Unit
) {
    InfoPage(
        title = "All Rooms",
        onBackClick = {onFunctionChange(0)}
    ) { }
}