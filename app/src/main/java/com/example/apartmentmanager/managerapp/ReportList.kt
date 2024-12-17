package com.example.apartmentmanager.managerapp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.apartmentmanager.R
import com.example.apartmentmanager.templates.ExpandBar
import com.example.apartmentmanager.templates.InfoPage
import com.example.apartmentmanager.templates.ItemList
import com.example.apartmentmanager.templates.LoadingScreen
import com.example.apartmentmanager.ui.theme.ApartmentManagerTheme
import com.example.apartmentmanager.ui.theme.DarkGreen
import com.example.apartmentmanager.ui.theme.LightGreen
import kotlinx.coroutines.delay

@Composable
fun ReportList(
    onFunctionChange: (Int) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    var showReply by remember { mutableStateOf(false) }
    var doneLoading by remember { mutableStateOf(false) }
    var reply by remember { mutableStateOf("") }
    var sending by remember { mutableStateOf(false) }
    var resolved by remember { mutableStateOf(false) }

    val titles = listOf("Internet Problem", "Water Leak", "Door Lock Issue")
    val descriptions = listOf(
        "The internet isn’t working properly, either it’s completely down or really unstable, please check it.",
        "There’s a water leak in my room that’s starting to cause some trouble.",
        "The door lock isn’t working right, it’s either jammed or broken. It’s making it hard to lock or unlock the door. Please fix it ASAP."
    )
    val rooms = listOf("P103", "P101", "P203")
    val dateSent = listOf("15/12/2024", "17/12/2024", "16/12/2024")
    val active = remember { mutableStateListOf<Boolean>(false, false, false) }
    var limit by remember { mutableIntStateOf(2) }

    LaunchedEffect(doneLoading) {
        if (!doneLoading) {
            delay(487)
            doneLoading = true
        }
    }

    LaunchedEffect(sending) {
        if (sending) {
            delay(487)
            sending = false
            resolved = true
            limit = 1
        }
    }

    LaunchedEffect(resolved) {
        if (resolved) {
            showReply = false
            delay(2000)
            resolved = false
        }
    }

    Box {
        InfoPage(
            title = "Report List",
            onBackClick = { onFunctionChange(0) },
        ) {
            if (!doneLoading) {
                LoadingScreen()
            } else {
                for (i in 0..limit) {
                    ExpandBar(
                        activated = active[i],
                        onClick = { active[i] = !active[i] },
                        barContent = {
                            Row(
                                modifier = Modifier.width(screenWidth * 0.7f),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.warning),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .scale(1.25f)
                                        .width(screenWidth * 0.08f),
                                )
                                Spacer(modifier = Modifier.width(screenWidth * 0.05f))
                                Column() {
                                    Row() {
                                        Text(
                                            text = rooms[i],
                                            style = MaterialTheme.typography.titleMedium,
                                            color = MaterialTheme.colorScheme.onSecondary
                                        )
                                        Spacer(modifier = Modifier.weight(1f))
                                        Text(
                                            text = dateSent[i],
                                            style = MaterialTheme.typography.titleSmall,
                                            color = MaterialTheme.colorScheme.onSecondary
                                        )
                                    }
                                    Text(
                                        text = titles[i],
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                }
                            }
                        },
                        expandedContent = {
                            Column {
                                ItemList {
                                    Text(
                                        text = descriptions[i],
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        modifier = Modifier.padding(screenWidth * 0.05f)
                                    )
                                }
                                ItemList(
                                    modifier = Modifier.align(Alignment.End)
                                ) {
                                    Row {
                                        Spacer(modifier = Modifier.weight(1f))
                                        Button(
                                            onClick = { showReply = true }
                                        ) {
                                            Text(
                                                text = "Reply",
                                                style = MaterialTheme.typography.titleMedium,
                                                color = MaterialTheme.colorScheme.onPrimary
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(screenWidth * 0.05f))
                                    }
                                    Spacer(modifier = Modifier.height(screenWidth * 0.05f))
                                }
                            }
                        }
                    )
                }

            }
        }

        AnimatedVisibility(
            visible = resolved,
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
                        text = "Reply sent!",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }

        if (showReply) {
            Surface(
                color = Color.Black.copy(alpha = 0.5f),
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { showReply = false }) { }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = screenWidth * 0.05f)
                    .align(Alignment.Center),
                colors = CardColors(
                    MaterialTheme.colorScheme.secondary.copy(alpha = 0.9f),
                    MaterialTheme.colorScheme.onSecondary,
                    MaterialTheme.colorScheme.onSecondary,
                    MaterialTheme.colorScheme.onSecondary
                )
            ) {
                Column(
                    modifier = Modifier.padding(screenWidth * 0.05f)
                ) {
                    Text(
                        text = "Reply this report",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                    Spacer(modifier = Modifier.height(screenWidth * 0.05f))
                    TextField(
                        value = reply,
                        onValueChange = { reply = it },
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        OutlinedButton(
                            onClick = { showReply = false },
                        ) {
                            Text(
                                text = "Cancel",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                        }
                        Spacer(modifier = Modifier.width(screenWidth * 0.05f))
                        Button(
                            onClick = {
                                sending = true
                            }
                        ) {
                            if (sending) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            } else {
                                Text(
                                    text = "Send",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        }
                    }
                }
            }
        }


    }
}


@Preview(showBackground = true)
@Composable
private fun ReportListPreviewLightMode() {
    ApartmentManagerTheme {
        ReportList(onFunctionChange = {})
    }
}