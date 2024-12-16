package com.example.apartmentmanager.managerapp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.KeyboardArrowRight
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.apartmentmanager.R
import com.example.apartmentmanager.templates.ExpandBar
import com.example.apartmentmanager.templates.InfoCardBar
import com.example.apartmentmanager.templates.InfoPage
import com.example.apartmentmanager.templates.ItemList
import com.example.apartmentmanager.tenantapp.secondarypage.ChangePassword
import com.example.apartmentmanager.ui.theme.ApartmentManagerTheme

@Composable
fun SettingsPage(
    onFunctionChange: (Int) -> Unit
) {
    var showLanguageSetting by rememberSaveable { mutableStateOf(false) }
    var selectedLanguage by rememberSaveable { mutableStateOf("en") }
    var showThemeSetting by rememberSaveable { mutableStateOf(false) }
    var selectedTheme by rememberSaveable { mutableStateOf("system") }

    var secondaryFunction by rememberSaveable { mutableIntStateOf(0) }
    val enterFromRight = slideInHorizontally(initialOffsetX = { it })
    val exitToRight = slideOutHorizontally(targetOffsetX = { it })
    val enterFromLeft = slideInHorizontally(initialOffsetX = { -it })
    val exitToLeft = slideOutHorizontally(targetOffsetX = { -it })

    AnimatedVisibility(
        visible = secondaryFunction == 0,
        enter = enterFromLeft,
        exit = exitToLeft
    ) {
        InfoPage(
            title = "Settings",
            onBackClick = { onFunctionChange(0) },
        ) {
            LanguageSetting(
                showLanguageSetting,
                onClick = { showLanguageSetting = !showLanguageSetting },
                onItemClick = { selectedLanguage = it }
            )
            ThemeSetting(
                showThemeSetting,
                onClick = { showThemeSetting = !showThemeSetting },
                onItemClick = { selectedTheme = it },
                selectedTheme = selectedTheme
            )
            InfoCardBar(
                painter1 = painterResource(R.drawable.notifications),
                tint1 = Color.Unspecified,
                size1 = 0.07f,
                title = "Notifications",
                onClick = { }
            )
            InfoCardBar(
                painter1 = painterResource(R.drawable.info),
                tint1 = MaterialTheme.colorScheme.primary,
                size1 = 0.07f,
                title = "About",
                onClick = { }
            )
            InfoCardBar(
                painter1 = painterResource(R.drawable.key),
                size1 = 0.07f,
                title = "Change password",
                onClick = { secondaryFunction = 3 },
                icon2 = Icons.AutoMirrored.TwoTone.KeyboardArrowRight,
                tint2 = MaterialTheme.colorScheme.onSecondary,
                size2 = 0.07f
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

    AnimatedVisibility(
        visible = secondaryFunction == 3,
        enter = enterFromRight,
        exit = exitToRight
    ) {
        ChangePassword(onFunctionChange = { secondaryFunction = 0 })
    }

}

@Composable
private fun LanguageSetting(
    showLanguageSetting: Boolean,
    onClick: () -> Unit = {},
    onItemClick: (String) -> Unit = {}
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    ExpandBar(
        activated = showLanguageSetting,
        onClick = onClick,
        iconSize = 0.07f,
        barContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.language),
                    contentDescription = "Language",
                    modifier = Modifier.height(screenWidth * 0.07f)
                )
                Spacer(modifier = Modifier.width(screenWidth * 0.05f))
                Text(
                    text = "Language",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        },
        expandedContent = {
            ItemList(
                onClick = {
                    onItemClick("en")
                }
            ) {
                Text(
                    text = "English",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.padding(
                        horizontal = screenWidth * 0.05f,
                        vertical = screenWidth * 0.025f
                    )
                )
            }
            ItemList(
                onClick = {
                    onItemClick("vi")
                }
            ) {
                Text(
                    text = "Tiếng Việt",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.padding(
                        horizontal = screenWidth * 0.05f,
                        vertical = screenWidth * 0.025f
                    )
                )
            }
        }
    )
}

@Composable
private fun ThemeSetting(
    showThemeSetting: Boolean,
    onClick: () -> Unit = {},
    onItemClick: (String) -> Unit = {},
    selectedTheme: String = "system",
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    ExpandBar(
        activated = showThemeSetting,
        onClick = onClick,
        iconSize = 0.07f,
        barContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.theme),
                    contentDescription = "Theme",
                    modifier = Modifier.height(screenWidth * 0.07f)
                )
                Spacer(modifier = Modifier.width(screenWidth * 0.05f))
                Text(
                    text = "Theme",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        },
        expandedContent = {
            ItemList(
                onClick = {
                    onItemClick("light")
                }
            ) {
                Row(
                    modifier = Modifier.padding(
                        horizontal = screenWidth * 0.05f,
                        vertical = screenWidth * 0.025f
                    )
                ) {
                    Text(
                        text = "Light",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondary,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    if (selectedTheme == "light") {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = "Selected",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.height(screenWidth * 0.04f).scale(1.6f)
                        )
                    }
                }
            }
            ItemList(
                onClick = {
                    onItemClick("dark")
                }
            ) {
                Row(
                    modifier = Modifier.padding(
                        horizontal = screenWidth * 0.05f,
                        vertical = screenWidth * 0.025f
                    )
                ) {
                    Text(
                        text = "Dark",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondary,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    if (selectedTheme == "dark") {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = "Selected",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.height(screenWidth * 0.04f).scale(1.6f)
                        )
                    }
                }
            }
            ItemList(
                onClick = {
                    onItemClick("system")
                }
            ) {
                Row(
                    modifier = Modifier.padding(
                        horizontal = screenWidth * 0.05f,
                        vertical = screenWidth * 0.025f
                    )
                ) {
                    Text(
                        text = "System",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondary,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    if (selectedTheme == "system") {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = "Selected",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.height(screenWidth * 0.04f).scale(1.6f)
                        )
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun SettingsPagePreviewLightMode() {
    ApartmentManagerTheme {
        com.example.apartmentmanager.tenantapp.SettingsPage(tenantID = "T00001", onFunctionChange = {})
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SettingsPagePreviewDarkMode() {
    ApartmentManagerTheme {
        com.example.apartmentmanager.tenantapp.SettingsPage(tenantID = "T00001", onFunctionChange = {})
    }
}