package com.example.apartmentmanager.tenantapp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.apartmentmanager.R
import com.example.apartmentmanager.templates.InfoCardBar
import com.example.apartmentmanager.templates.InfoPage
import com.example.apartmentmanager.tenantapp.secondarypage.ReportSent
import com.example.apartmentmanager.ui.theme.ApartmentManagerTheme
import com.example.apartmentmanager.ui.theme.DarkGreen
import com.example.apartmentmanager.ui.theme.LightGreen
import kotlinx.coroutines.delay

//Function 5: Gửi báo cáo
@Composable
fun ReportPage(
    tenantID: String,
    onFunctionChange: (Int) -> Unit
) {
    var showSuccessDialog by rememberSaveable { mutableStateOf(false) }
    var sending by rememberSaveable { mutableStateOf(false) }
    var reportNav by rememberSaveable { mutableStateOf(0) }

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    var title by rememberSaveable { mutableStateOf("") }
    var content by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(showSuccessDialog) {
        if (showSuccessDialog) {
            title = ""
            content = ""
            delay(2000)
            showSuccessDialog = false
        }
    }

    LaunchedEffect(sending) {
        if (sending) {
            delay(345)
            sending = false
            showSuccessDialog = true
        }
    }

    AnimatedVisibility(
        visible = reportNav == 0,
        enter = slideInHorizontally(initialOffsetX = { -it }),
        exit = slideOutHorizontally(targetOffsetX = { -it }),
    ) {
        Box {
            InfoPage(
                onBackClick = { onFunctionChange(0) },
                title = "Send Report"
            ) {
                Spacer(modifier = Modifier.height(screenWidth * 0.05f))
                Card(
                    modifier = Modifier
                        .width(screenWidth * 0.9f)
                        .align(Alignment.CenterHorizontally),
                    colors = CardColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f),
                        Color.Unspecified,
                        Color.Unspecified,
                        Color.Unspecified
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(screenWidth * 0.05f)
                    ) {
                        ReportTitle(title, onValueChange = { title = it })
                        Description(content, onValueChange = { content = it })
                        Button(
                            onClick = {
                                sending = true
                            }, //Do something here
                            modifier = Modifier
                                .align(Alignment.End)
                                .width(screenWidth * 0.3f)
                        ) {
                            if (sending) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            } else {
                                Text("Send")
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(screenWidth * 0.05f))
                InfoCardBar(
                    title = "Your reports",
                    onClick = {reportNav = 1},
                    painter1 = painterResource(R.drawable.info),
                    tint1 = MaterialTheme.colorScheme.primary,
                    size1 = 0.08f,
                    icon2 = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    tint2 = MaterialTheme.colorScheme.primary,
                    size2 = 0.08f,

                    )
            }

            AnimatedVisibility(
                visible = showSuccessDialog,
                enter = slideInVertically(initialOffsetY = { -it }),
                exit = slideOutVertically(targetOffsetY = { -it }),
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = screenWidth * 0.05f)
                        .align(Alignment.Center),
                    shape = RectangleShape,
                    colors = CardColors(
                        containerColor = if (isSystemInDarkTheme()) DarkGreen else LightGreen,
                        Color.Unspecified,
                        Color.Unspecified,
                        Color.Unspecified
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(screenWidth * 0.05f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.info),
                            contentDescription = null,
                            modifier = Modifier
                                .scale(1.25f)
                                .width(screenWidth * 0.08f),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(screenWidth * 0.05f))
                        Text(
                            text = "Report sent successfully!",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }
    }

    AnimatedVisibility(
        visible = reportNav == 1,
        enter = slideInHorizontally(initialOffsetX = { it }),
        exit = slideOutHorizontally(targetOffsetX = { it }),
    ) {
        ReportSent(onBackClick = {reportNav = 0})
    }
}

@Composable
private fun Description(
    content: String,
    onValueChange: (String) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    Text(
        text = "Description",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onBackground
    )
    Spacer(modifier = Modifier.height(screenWidth * 0.025f))
    TextField(
        value = content,
        onValueChange = { onValueChange(it) },
        shape = ShapeDefaults.Large,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            focusedContainerColor = MaterialTheme.colorScheme.onTertiaryContainer,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
    )
    Spacer(modifier = Modifier.height(screenWidth * 0.05f))
}

@Composable
private fun ReportTitle(
    title: String,
    onValueChange: (String) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    Text(
        text = "Report title",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onBackground
    )
    Spacer(modifier = Modifier.height(screenWidth * 0.025f))
    TextField(
        value = title,
        onValueChange = { onValueChange(it) },
        singleLine = true,
        shape = ShapeDefaults.Large,
        modifier = Modifier
            .fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            focusedContainerColor = MaterialTheme.colorScheme.onTertiaryContainer,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
    )
    Spacer(modifier = Modifier.height(screenWidth * 0.05f))
}

@Preview(showBackground = true)
@Composable
fun ReportPagePreviewLightMode() {
    ApartmentManagerTheme {
        ReportPage(tenantID = "T00001", onFunctionChange = {})
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ReportPagePreviewDarkMode() {
    ApartmentManagerTheme {
        ReportPage(tenantID = "T00001", onFunctionChange = {})
    }
}