package com.example.apartmentmanager.tenantapp.secondarypage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
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
import kotlinx.coroutines.delay

@Composable
fun ReportSent(
    onBackClick: (Int) -> Unit,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    var showDetails by remember { mutableStateOf(false) }
    var doneLoading by remember { mutableStateOf(false) }

    LaunchedEffect(doneLoading) {
        if (!doneLoading) {
            delay(212)
            doneLoading = true
        }
    }

    InfoPage(
        onBackClick = { onBackClick(0) },
        title = "Your reports"
    ) {
        if (!doneLoading) {
            LoadingScreen()
        } else {
            ExpandBar(
                activated = showDetails,
                onClick = { showDetails = !showDetails },
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
                            Text(
                                text = "17/12/2024",
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                            Text(
                                text = "Water Leak",
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
                                text = "Description: ",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.padding(screenWidth * 0.05f)
                            )
                            Text(
                                text = "There’s a water leak in my room that’s starting to cause some trouble.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.padding(screenWidth * 0.05f)
                            )
                        }
                        ItemList(
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text(
                                text = "Reply: ",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.padding(screenWidth * 0.05f)
                            )
                            Text(
                                text = "No reply yet",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.padding(screenWidth * 0.05f)
                            )
                        }
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReportSentPreviewLightMode() {
    ApartmentManagerTheme {
        ReportSent(onBackClick = {})
    }
}