package com.example.apartmentmanager.tenantapp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.apartmentmanager.templates.InfoPage
import com.example.apartmentmanager.templates.ItemList
import com.example.apartmentmanager.templates.ItemListChild
import com.example.apartmentmanager.ui.theme.ApartmentManagerTheme
import com.example.apartmentmanager.ui.theme.Red


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

        ItemList(
            onClick = { showLanguageSetting = !showLanguageSetting },
            modifier = modifier
        ) {
            Text("Language")
        }
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
                    },
                    modifier = Modifier
                ) {
                    Text("English")
                }
                ItemListChild(
                    onClick = {
                        selectedLanguage = "vi"
                    },
                    modifier = Modifier
                ) {
                    Text("Tiếng Việt")
                }
            }
        }
        ItemList(
            onClick = { showThemeSetting = !showThemeSetting },
            modifier = Modifier
        ) {
            Text("Theme")
        }
        AnimatedVisibility(
            visible = showThemeSetting,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Column {
                ItemListChild(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                ) {
                    Text("Light")
                }
                ItemListChild(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                ) {
                    Text("Dark")
                }
                ItemListChild(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                ) {
                    Text("System")
                }
            }
        }
        ItemList(
            onClick = { /*TODO*/ },
            modifier = Modifier
        ) {
            Text("Notifications")
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
        ItemList(
            onClick = { /*TODO*/ },
            modifier = Modifier
        ) {
            Text(
                text = "Request to leave apartment",
                color = Red
            )
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