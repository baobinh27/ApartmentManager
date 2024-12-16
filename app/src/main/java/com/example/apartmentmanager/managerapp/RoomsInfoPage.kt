package com.example.apartmentmanager.managerapp

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.apartmentmanager.R
import com.example.apartmentmanager.getRoomList
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
    InfoPage(
        title = "All Rooms",
        onBackClick = { onFunctionChange(0) }
    ) {
        val screenWidth = LocalConfiguration.current.screenWidthDp.dp

        var doneLoading by remember { mutableStateOf(false) }
        var failed by remember { mutableStateOf(false) }
        var roomList by remember { mutableStateOf(listOf<String>()) }
        val showContent = remember { mutableStateMapOf<String, Boolean>() }
        val tenantList = remember { mutableStateMapOf<String, List<Pair<String, String>>>() }

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
                                onClick = {}
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
}

@Preview(showBackground = true)
@Composable
private fun preview() {
    ApartmentManagerTheme {

        InfoPage(
            title = "All Rooms",
            onBackClick = {}
        ) {
            val screenWidth = LocalConfiguration.current.screenWidthDp.dp
            val roomList = listOf(
                "P101",
                "P102",
                "P103",
                "P104",
                "P105",
                "P106",
                "P107",
                "P108",
                "P109",
                "P110"
            )
            Column() {
                for (room in roomList) {
                    ExpandBar(
                        activated = false,
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

                        }
                    )
                }
            }
        }
    }
}
