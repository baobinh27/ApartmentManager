package com.example.apartmentmanager.mainfunctions

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.apartmentmanager.templates.InfoPage
import com.example.apartmentmanager.ui.theme.ApartmentManagerTheme

//Function 1: Thông tin chung cư
@Composable
fun ApartmentInfoPage(
    modifier: Modifier,
    onFunctionChange: (Int) -> Unit
) {
    InfoPage(
        title = "Apartment Information",
        onBackClick = { onFunctionChange(0) },
        modifier = modifier
    ) {
        Text("Apartment Name: ")
        Text("Address: ")
        Text("Number of Rooms: ")
        Text("Number of Floors: ")
        Text("Area: ")
        Text("Description: ")
        Text("Owner: ")
        Text("Contact Information: ")
        Text("Maintenance Information: ")
        Text("Financial Information: ")
        Text("Other Information: ")
    }
}

@Preview(showBackground = true)
@Composable
fun ApartmentInfoPagePreviewLightMode() {
    ApartmentManagerTheme {
        ApartmentInfoPage(modifier = Modifier, onFunctionChange = {})
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ApartmentInfoPagePreviewDarkMode() {
    ApartmentManagerTheme {
        ApartmentInfoPage(modifier = Modifier, onFunctionChange = {})
    }
}