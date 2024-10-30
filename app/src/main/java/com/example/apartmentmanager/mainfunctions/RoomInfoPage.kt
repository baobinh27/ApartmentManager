package com.example.apartmentmanager.mainfunctions

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.apartmentmanager.templates.InfoPage
import com.example.apartmentmanager.ui.theme.ApartmentManagerTheme

//Function 2: Thông tin phòng
@Composable
fun RoomInfoPage(
    modifier: Modifier,
    onFunctionChange: (Int) -> Unit
) {
    InfoPage(
        title = "Room Information",
        onBackClick = { onFunctionChange(0) },
        modifier = modifier
    ) {
        Text(text = "Room Number:")
        Text(text = "Floor:")
        Text(text = "Area:")
        Text(text = "Rent:")
        Text(text = "Deposit:")
        Text(text = "Status:")
        Text(text = "Description:")
        Text(text = "Note:")
        Text(text = "Last Updated:")
    }
}

@Preview(showBackground = true)
@Composable
fun RoomInfoPagePreviewLightMode() {
    ApartmentManagerTheme {
        RoomInfoPage(modifier = Modifier, onFunctionChange = {})
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun RoomInfoPagePreviewDarkMode() {
    ApartmentManagerTheme {
        RoomInfoPage(modifier = Modifier, onFunctionChange = {})
    }
}