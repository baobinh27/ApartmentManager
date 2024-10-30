package com.example.apartmentmanager3

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.apartmentmanager3.ui.theme.ApartmentManager3Theme
import com.example.apartmentmanager3.mainfunctions.*

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
        6 -> {}
        7 -> {}
    }

}


@Preview(showBackground = true)
@Composable
fun MainAppPreviewLightMode() {
    ApartmentManager3Theme {
        MainApp(modifier = Modifier, onLogOut = {})
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MainAppPreviewDarkMode() {
    ApartmentManager3Theme {
        MainApp(modifier = Modifier, onLogOut = {})
    }
}
