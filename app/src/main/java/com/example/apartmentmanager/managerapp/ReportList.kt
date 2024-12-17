package com.example.apartmentmanager.managerapp

import androidx.compose.runtime.Composable
import com.example.apartmentmanager.templates.InfoPage

@Composable
fun ReportList(
    onFunctionChange: (Int) -> Unit
) {
    InfoPage(
        title = "Report List",
        onBackClick = { onFunctionChange(0) },
    ) {

    }
}