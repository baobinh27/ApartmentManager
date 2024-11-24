package com.example.apartmentmanager.tenantapp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material.icons.twotone.Info
import androidx.compose.material.icons.twotone.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.apartmentmanager.templates.InfoCardBar
import com.example.apartmentmanager.templates.InfoPage
import com.example.apartmentmanager.templates.ItemListChild
import com.example.apartmentmanager.ui.theme.ApartmentManagerTheme


//Function 7: Cài đặt
@Composable
fun SettingsPage(
    modifier: Modifier,
    onFunctionChange: (Int) -> Unit
) {
    var showLanguageSetting by rememberSaveable { mutableStateOf(false) }
    var selectedLanguage by rememberSaveable { mutableStateOf("en") }
    var showThemeSetting by rememberSaveable { mutableStateOf(false) }
    InfoPage(
        title = "Settings",
        onBackClick = { onFunctionChange(0) },
        modifier = modifier
    ) {
        InfoCardBar(
            painter1 = painterResource(com.example.apartmentmanager.R.drawable.language),
            tint1 = Color.Unspecified,
            size1 = 0.07f,
            title = "Language",
            onClick = { showLanguageSetting = !showLanguageSetting }
        )
        AnimatedVisibility(
            visible = showLanguageSetting,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Column {
                ItemListChild(
                    onClick = {
                        selectedLanguage = "en"
                        showLanguageSetting = false
                    },
                    modifier = Modifier,
                    title = "English"
                )
                ItemListChild(
                    onClick = {
                        selectedLanguage = "vi"
                    },
                    modifier = Modifier,
                    title = "Tiếng Việt"
                )
            }
        }
        InfoCardBar(
            painter1 = painterResource(com.example.apartmentmanager.R.drawable.theme),
            tint1 = Color.Unspecified,
            size1 = 0.07f,
            title = "Theme",
            onClick = { showThemeSetting = !showThemeSetting }
        )
        AnimatedVisibility(
            visible = showThemeSetting,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Column {
                ItemListChild(
                    onClick = { /*TODO*/ },
                    modifier = Modifier,
                    title = "Light"
                )
                ItemListChild(
                    onClick = { /*TODO*/ },
                    modifier = Modifier,
                    title = "Dark"
                )
                ItemListChild(
                    onClick = { /*TODO*/ },
                    modifier = Modifier,
                    title = "System"
                )
            }
        }
        InfoCardBar(
            painter1 = painterResource(com.example.apartmentmanager.R.drawable.notifications),
            tint1 = Color.Unspecified,
            size1 = 0.07f,
            title = "Notifications",
            onClick = { }
        )
        InfoCardBar(
            icon1 = Icons.TwoTone.Info,
            tint1 = Color.Black,
            size1 = 0.07f,
            title = "About",
            onClick = { }
        )
        InfoCardBar(
            icon1 = Icons.TwoTone.Lock,
            tint1 = Color.Black,
            size1 = 0.07f,
            title = "Change password",
            onClick = { }
        )
        InfoCardBar(
            icon1 = Icons.TwoTone.Delete,
            tint1 = Color.Black,
            size1 = 0.07f,
            textColor = Color.Red,
            title = "Request to leave apartment",
            onClick = { }
        )
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