package com.example.apartmentmanager.managerapp

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apartmentmanager.R
import com.example.apartmentmanager.getApartmentInfo
import com.example.apartmentmanager.getManagerInfo
import com.example.apartmentmanager.getRoomCount
import com.example.apartmentmanager.templates.ExpandBar
import com.example.apartmentmanager.templates.FailedLoadingScreen
import com.example.apartmentmanager.templates.InfoCardBar
import com.example.apartmentmanager.templates.InfoPage
import com.example.apartmentmanager.templates.ItemList
import com.example.apartmentmanager.templates.LoadingScreen
import com.example.apartmentmanager.tenantapp.OwnerCard
import com.example.apartmentmanager.ui.theme.ApartmentManagerTheme
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay

//Function 1: Thông tin chung cư
@Composable
fun ApartmentInfoPage(
    onFunctionChange: (Int) -> Unit
) {
    // y hệt bên tenant, không khác tẹo nào
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    var showManager by rememberSaveable { mutableStateOf(false) }
    var managerIDSelected by rememberSaveable { mutableStateOf(listOf<String>()) }

    var managerCount by remember { mutableIntStateOf(0) }

    var failed by remember { mutableStateOf(false) }
    var doneLoading by remember { mutableStateOf(false) }

    var apartmentInfo by remember { mutableStateOf(listOf<String>("", "", "0", "0", "", "")) }
    // (apartmentName, address, area, numberRooms, owner, contact)
    var managerList by remember { mutableStateOf(listOf<List<String>>()) }
    // listOf (id, name, DOB, phone)

    LaunchedEffect(doneLoading) {
        delay(10000)
        if (!doneLoading) failed = true
    }

    LaunchedEffect(failed) {
        if (!failed) {
            apartmentInfo = getApartmentInfo()
            managerList = getManagerInfo()
            if (apartmentInfo.isNotEmpty() && managerList.isNotEmpty()) {
                managerCount = managerList.size
                doneLoading = true
            }
            Log.d("Firebase", "apartmentInfo: $apartmentInfo")
            Log.d("Firebase", "managerInfo: $managerList")
        }
    }
    // Kiểm tra tất cả các tác vụ đã hoàn thành, sẵn sàng hiển thị hay chưa
    LaunchedEffect(failed) {
        doneLoading = !failed && doneLoading
    }

    Box() {
        InfoPage(
            title = "Apartment Information",
            onBackClick = { onFunctionChange(0) },
        ) {
            if (!doneLoading) {
                if (failed) {
                    FailedLoadingScreen()
                } else {
                    LoadingScreen()
                }
            } else {
                NameAndAddress(
                    name = apartmentInfo[0],
                    address = apartmentInfo[1]
                )
                RoomsAndArea(
                    numberRooms = apartmentInfo[3].toInt(),
                    area = apartmentInfo[2].toInt()
                )
                OwnerCard(name = apartmentInfo[4], phone = apartmentInfo[5])
                ExpandBar(
                    activated = showManager,
                    onClick = { showManager = !showManager },
                    barContent = {
                        Icon(
                            painter = painterResource(R.drawable.manager),
                            contentDescription = null,
                            modifier = Modifier.height(screenWidth * 0.08f)
                        )
                        Spacer(modifier = Modifier.width(screenWidth * 0.05f))
                        Text(
                            text = "Managers (${managerCount})",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    },
                    expandedContent = {
                        for (id in managerList) {
                            ItemList(
                                onClick = { managerIDSelected = id }
                            ) {
                                Row(
                                    modifier = Modifier.padding(
                                        vertical = screenWidth * 0.025f,
                                        horizontal = screenWidth * 0.05f
                                    )
                                ) {
                                    Text(id[1])
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text(id[0])
                                }
                            }

                        }
                    }
                )
            }
        }

        if (managerIDSelected.isNotEmpty()) {
            Surface(modifier = Modifier.fillMaxSize(), color = Color.Black.copy(alpha = 0.5f)) {}
            Card(
                modifier = Modifier.fillMaxWidth().padding(screenWidth * 0.05f)
                    .align(Alignment.Center).height(screenWidth * 0.5f),
            ) {
                Column(
                    modifier = Modifier.padding(screenWidth * 0.05f).fillMaxWidth()
                ) {
                    Text(
                        text = managerIDSelected[1],
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                    Text(
                        text = managerIDSelected[0],
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                    Spacer(modifier = Modifier.height(screenWidth * 0.025f))
                    Text(
                        text = "Date of birth: ${managerIDSelected[2]}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                    Text(
                        text = "Phone: ${managerIDSelected[3]}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondary
                    )

                    Spacer(modifier = Modifier.height(screenWidth * 0.05f))
                    Button(
                        onClick = {
                            managerIDSelected = emptyList()
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

@Composable
private fun NameAndAddress(
    name: String,
    address: String,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        modifier = Modifier
            .padding(
                top = screenWidth * 0.05f,
                start = screenWidth * 0.05f,
                end = screenWidth * 0.05f
            )
            .fillMaxWidth(),
        colors = CardColors(
            MaterialTheme.colorScheme.secondary,
            Color.Unspecified,
            Color.Unspecified,
            Color.Unspecified
        )
    ) {
        Column(
            modifier = Modifier
                .padding(screenWidth * 0.05f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.apartment2),
                    contentDescription = "Apartment Information",
                    modifier = Modifier.height(screenWidth * 0.2f)
                )
                Spacer(modifier = Modifier.width(screenWidth * 0.05f))
                Text(
                    text = "$name Apartment",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onSecondary,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(
                modifier = Modifier.height(20.dp)
            )
            Text(
                text = "Address: $address",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
        }

    }
}

@Composable
private fun RoomsAndArea(
    numberRooms: Int,
    area: Int
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = screenWidth * 0.05f)
    ) {
        Spacer(modifier = Modifier.width(screenWidth * 0.05f))
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            modifier = Modifier
                .width(screenWidth * 0.35f),
            colors = CardColors(
                MaterialTheme.colorScheme.secondary,
                Color.Unspecified,
                Color.Unspecified,
                Color.Unspecified
            )

        ) {
            Column(
                modifier = Modifier
                    .padding(screenWidth * 0.05f)
                    .fillMaxSize()
            ) {
                Text(
                    text = "$numberRooms",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(screenWidth * 0.025f))
                Text(
                    text = "Number of rooms",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center
                )
            }

        }
        Spacer(modifier = Modifier.width(screenWidth * 0.05f))

        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            modifier = Modifier
                .width(screenWidth * 0.5f),
            colors = CardColors(
                MaterialTheme.colorScheme.secondary,
                Color.Unspecified,
                Color.Unspecified,
                Color.Unspecified
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(screenWidth * 0.05f)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("$area ")
                        withStyle(
                            style = SpanStyle(
                                fontSize = 24.sp
                            )
                        ) {
                            append("m")
                        }
                        withStyle(
                            style = SpanStyle(
                                baselineShift = BaselineShift.Superscript,
                                fontSize = 15.sp // Kích thước nhỏ hơn cho chỉ số
                            )
                        ) {
                            append("2")
                        }
                    },
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onSecondary,
                )
                Spacer(modifier = Modifier.height(screenWidth * 0.025f))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.area),
                        contentDescription = "area",
                        modifier = Modifier.height(screenWidth * 0.1f),
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                    Spacer(modifier = Modifier.width(screenWidth * 0.05f))
                    Text(
                        text = "Apartment\nArea",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSecondary,
                        textAlign = TextAlign.Center
                    )
                }

            }
        }
        Spacer(modifier = Modifier.width(screenWidth * 0.05f))
    }
}

@Preview(showBackground = true)
@Composable
private fun ApartmentInfoPagePreviewLightMode() {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    ApartmentManagerTheme {
        InfoPage(
            title = "Apartment Information",
            onBackClick = { },
            modifier = Modifier
        ) {
            NameAndAddress(name = "apartmentName", address = "address")
            RoomsAndArea(numberRooms = 32, area = 9999)
            InfoCardBar(
                painter1 = painterResource(id = R.drawable.owner),
                size1 = 0.08f,
                onClick = { }, //TODO
                title = "Owners (0)",
                icon2 = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                size2 = 0.1f,
                tint2 = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.height(screenWidth * 0.025f))
            InfoCardBar(
                painter1 = painterResource(id = R.drawable.manager),
                size1 = 0.08f,
                onClick = {},  //TODO
                title = "Managers (1)",
                icon2 = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                size2 = 0.1f,
                tint2 = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}
