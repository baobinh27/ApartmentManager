package com.example.apartmentmanager.managerapp

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.apartmentmanager.R
import com.example.apartmentmanager.getRoomList
import com.example.apartmentmanager.getTenantInfo
import com.example.apartmentmanager.getTenantsList
import com.example.apartmentmanager.templates.ExpandBar
import com.example.apartmentmanager.templates.FailedLoadingScreen
import com.example.apartmentmanager.templates.InfoCardBar
import com.example.apartmentmanager.templates.InfoPage
import com.example.apartmentmanager.templates.ItemList
import com.example.apartmentmanager.templates.LoadingScreen
import com.example.apartmentmanager.ui.theme.ApartmentManagerTheme
import kotlinx.coroutines.delay
import java.util.HashMap

@Composable
fun RoomsInfoPage(
    modifier: Modifier = Modifier,
    onFunctionChange: (Int) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    var doneLoading by remember { mutableStateOf(false) }
    var failed by remember { mutableStateOf(false) }
    var roomList by remember { mutableStateOf(listOf<String>()) }
    val showContent = remember { mutableStateMapOf<String, Boolean>() }
    val tenantList = remember { mutableStateMapOf<String, List<Pair<String, String>>>() }
    var tenantIDSelected by remember { mutableStateOf("") }
    var nameSelected by remember { mutableStateOf("") }

    LaunchedEffect(doneLoading) {
        delay(10000)
        if (!doneLoading) failed = true
    }

    LaunchedEffect(failed) {
        if (!failed) {
            roomList = getRoomList()
            roomList.forEach { room ->
                showContent[room] = false
                tenantList[room] = getTenantsList(room)
            }
            doneLoading = true
            Log.d("Firebase", "roomList: $roomList")
            Log.d("data", "showContent: ${showContent.toMap().toString()}")
            Log.d("data", "tenantList: ${tenantList.toMap().toString()}")
        }
    }

    LaunchedEffect(failed) {
        doneLoading = !failed && doneLoading
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {


        InfoPage(
            title = "All Rooms",
            onBackClick = { onFunctionChange(0) }
        ) {
            if (!doneLoading) {
                if (failed) {
                    FailedLoadingScreen()
                } else {
                    LoadingScreen()
                }
            } else {
                for (room in roomList) {
                    ExpandBar(
                        onClick = { showContent[room] = !(showContent[room] ?: false) },
                        activated = showContent.getOrDefault(room, false),
                        barContent = {
                            Icon(
                                painter = painterResource(id = R.drawable.key),
                                contentDescription = null,
                                modifier = Modifier.width(screenWidth * 0.08f)
                            )
                            Spacer(modifier = Modifier.width(screenWidth * 0.05f))
                            Text(
                                text = room,
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                        },
                        expandedContent = {
                            for (tenant in tenantList.getOrDefault(room, listOf())) {
                                ItemList(
                                    onClick = {
                                        tenantIDSelected = tenant.first
                                        nameSelected = tenant.second
                                    }
                                ) {
                                    Row(
                                        modifier = Modifier.padding(screenWidth * 0.025f)
                                    ) {
                                        Text(
                                            text = tenant.second,
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = MaterialTheme.colorScheme.onSecondary
                                        )
                                        Spacer(modifier = Modifier.weight(1f))
                                        Text(
                                            text = tenant.first,
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = MaterialTheme.colorScheme.onSecondary
                                        )
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }

        if (tenantIDSelected != "") {
            Surface(modifier = Modifier.fillMaxSize(), color = Color.Black.copy(alpha = 0.5f)) {}
            Card(
                modifier = Modifier.fillMaxWidth().padding(screenWidth * 0.05f).align(Alignment.Center).height(screenWidth * 0.6f),
            ) {
                var doneFetching by remember { mutableStateOf(false) }
                var tenantInfo by remember { mutableStateOf(listOf<String>()) }
                LaunchedEffect(tenantIDSelected) {
                    tenantInfo = getTenantInfo(tenantIDSelected)
                    // (name, dateOfBirth, phone, dateArrive, dateLeave, roomID, hometown)
                    doneFetching = true
                }
                if (!doneFetching) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxHeight().width(screenWidth * 0.6f).padding(screenWidth * 0.15f).align(Alignment.CenterHorizontally),
                        color = MaterialTheme.colorScheme.primary,
                        strokeWidth = 5.dp,
                    )
                } else {
                    Column(
                        modifier = Modifier.padding(screenWidth * 0.05f).fillMaxWidth()
                    ) {
                        Text(text = tenantInfo[0], style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onSecondary)
                        Text(text = tenantIDSelected, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSecondary)
                        Spacer(modifier = Modifier.height(screenWidth * 0.025f))
                        Text(text = "Date of birth: ${tenantInfo[1]}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSecondary)
                        Text(text = "Phone: ${tenantInfo[2]}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSecondary)
                        Text(text = "Date moved in: ${tenantInfo[3]}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSecondary)
                        Text(text = "Date moved out: ${if (tenantInfo[4] == "null") "N/A" else {tenantInfo[4]}}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSecondary)
                        Text(text = "Room: ${tenantInfo[5]}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSecondary)
                        Text(text = "Hometown: ${tenantInfo[6]}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSecondary)
                        Spacer(modifier = Modifier.height(screenWidth * 0.05f))
                        Button(
                            onClick = {
                                tenantIDSelected = ""
                            },
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                        ) {
                            Text("Done")
                        }
                    }
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun preview() {
    ApartmentManagerTheme {
        val screenWidth = LocalConfiguration.current.screenWidthDp.dp
        Card(
            modifier = Modifier.fillMaxWidth().padding(screenWidth * 0.05f).height(screenWidth * 0.6f),
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxHeight().width(screenWidth * 0.6f).padding(screenWidth * 0.15f).align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 5.dp,
            )
        }
    }
}
