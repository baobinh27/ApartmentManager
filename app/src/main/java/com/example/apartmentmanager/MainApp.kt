package com.example.apartmentmanager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.apartmentmanager.ui.theme.ApartmentManagerTheme
import com.example.apartmentmanager.mainfunctions.*

@Composable
fun MainApp(
    modifier: Modifier = Modifier,
    onLogOut: () -> Unit
) {
    var function by rememberSaveable { mutableIntStateOf(0) }
    when (function) {
        0 -> HomePage(modifier, onLogOut, onFunctionChange = { function = it })
        1 -> ApartmentInfoPage(modifier, onFunctionChange = { function = 0 })
        2 -> RoomInfoPage(modifier, onFunctionChange = { function = 0 })
        3 -> RentStatusPage(modifier, onFunctionChange = { function = 0 })
        4 -> FinancialReportPage(modifier, onFunctionChange = { function = 0 })
        5 -> ModifyRoomPage(modifier, onFunctionChange = { function = 0 })
        6 -> ReportPage(modifier, onFunctionChange = { function = 0 })
        7 -> SettingsPage(modifier, onFunctionChange = { function = 0 })
    }

}


@Preview(showBackground = true)
@Composable
fun MainAppPreviewLightMode() {
    ApartmentManagerTheme {
        MainApp(modifier = Modifier, onLogOut = {})
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MainAppPreviewDarkMode() {
    ApartmentManagerTheme {
        MainApp(modifier = Modifier, onLogOut = {})
    }
}
