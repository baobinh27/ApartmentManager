package com.example.apartmentmanager.mainfunctions

import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.apartmentmanager.templates.InfoPage
import com.example.apartmentmanager.templates.ItemList
import com.example.apartmentmanager.ui.theme.ApartmentManagerTheme

@Composable
fun SettingsPage(
    modifier: Modifier,
    onFunctionChange: (Int) -> Unit
) {
    InfoPage(
        title = "Settings",
        onBackClick = { onFunctionChange(0) },
        modifier = modifier
    ) {
        ItemList(
            onClick = { /*TODO*/ },
            modifier = Modifier
        ) {
            Text("Language")
        }
        ItemList(
            onClick = { /*TODO*/ },
            modifier = Modifier
        ) {
            Text("Theme")
        }
        ItemList(
            onClick = { /*TODO*/ },
            modifier = Modifier
        ) {
            Text("Notification")
        }
        ItemList(
            onClick = { /*TODO*/ },
            modifier = Modifier
        ) {
            Text("About")
        }
        ItemList(
            onClick = { /*TODO*/ },
            modifier = Modifier
        ) {
            Text("Change password")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsPagePreviewLightMode() {
    ApartmentManagerTheme {
        SettingsPage(modifier = Modifier, onFunctionChange = {})
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SettingsPagePreviewDarkMode() {
    ApartmentManagerTheme {
        SettingsPage(modifier = Modifier, onFunctionChange = {})
    }
}