package com.example.apartmentmanager.tenantapp

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apartmentmanager.R
import com.example.apartmentmanager.templates.InfoCardBar
import com.example.apartmentmanager.templates.InfoPage
import com.example.apartmentmanager.ui.theme.ApartmentManagerTheme
import com.google.firebase.firestore.FirebaseFirestore

//Function 1: Thông tin chung cư
@Composable
fun ApartmentInfoPage(
    modifier: Modifier,
    onFunctionChange: (Int) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val db = FirebaseFirestore.getInstance()
    val apartmentRef = db.collection("apartmentInfo").document("general")
    val roomRef = db.collection("Room")
    val authRef = db.collection("authentication")

    // Tạo MutableState để lưu trữ dữ liệu và cập nhật khi có thay đổi
    var apartmentName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var owner by remember { mutableStateOf("") }
    var contactInformation by remember { mutableStateOf("") }
    var numberRooms by remember { mutableIntStateOf(0) }
    var area by remember { mutableIntStateOf(0) }
    var ownerCount by remember { mutableIntStateOf(0) }
    var managerCount by remember { mutableIntStateOf(0) }

    var failed by remember { mutableStateOf(false) }
    var doneLoading by remember { mutableStateOf(false) }

    // Biến trạng thái để theo dõi từng tác vụ Firebase
    var apartmentInfoLoaded by remember { mutableStateOf(false) }
    var roomsLoaded by remember { mutableStateOf(false) }
    var authLoaded by remember { mutableStateOf(false) }
    // Lấy dữ liệu từ Firestore
    // Ở đây sử dụng LaunchedEffect để thực hiện tác vụ chỉ một lần khi Composable được khởi tạo
    LaunchedEffect(Unit) {
        apartmentRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("Firestore", "Document data: ${document.data}")
                    apartmentName = document.getString("name").orEmpty()
                    address = document.getString("address").orEmpty()
                    owner = document.getString("owner").orEmpty()
                    contactInformation = document.getString("contact").orEmpty()
                    area = document.getLong("area")?.toInt() ?: 0
                } else {
                    Log.d("Firestore", "No such document")
                    failed = true
                }
                apartmentInfoLoaded = true
            }
            .addOnFailureListener { exception ->
                Log.d("Firestore", "get failed with ", exception)
                failed = true
                apartmentInfoLoaded = true
            }
        roomRef.get()
            .addOnSuccessListener { documents ->
                numberRooms = documents.size()
                roomsLoaded = true
            }
            .addOnFailureListener { exception ->
                Log.d("Firestore", "get failed with ", exception)
                failed = true
                roomsLoaded = true
            }
        authRef.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val id = document.id
                    if (id[0] == 'O') {
                        ownerCount++
                    } else if (id[0] == 'M') {
                        managerCount++
                    }
                }
                authLoaded = true
            }
            .addOnFailureListener { exception ->
                Log.d("Firestore", "get failed with ", exception)
                failed = true
                authLoaded = true
            }
    }

    // Kiểm tra tất cả các tác vụ đã hoàn thành, sẵn sàng hiển thị hay chưa
    LaunchedEffect(apartmentInfoLoaded, roomsLoaded, authLoaded) {
        doneLoading = apartmentInfoLoaded && roomsLoaded && authLoaded && !failed
    }

    if (!doneLoading) {
        InfoPage(
            title = "Apartment Information",
            onBackClick = { onFunctionChange(0) },
            modifier = modifier
        ) {
            if (failed) {
                FailedLoadingScreen(screenHeight, screenWidth)
            } else {
                LoadingScreen(screenWidth)
            }
        }
    } else {
        InfoPage(
            title = "Apartment Information",
            onBackClick = { onFunctionChange(0) },
            modifier = modifier
        ) {
            NameAndAddress(name = apartmentName, address = address)
            RoomsAndArea(numberRooms = numberRooms, area = area)
            InfoCardBar(
                painter1 = painterResource(id = R.drawable.owner),
                size1 = 0.08f,
                onClick = { }, //TODO
                title = "Owners ($ownerCount)",
                icon2 = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                size2 = 0.1f,
                tint2 = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.height(screenWidth * 0.025f))
            InfoCardBar(
                painter1 = painterResource(id = R.drawable.manager),
                size1 = 0.08f,
                onClick = {},  //TODO
                title = "Managers ($managerCount)",
                icon2 = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                size2 = 0.1f,
                tint2 = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}

@Composable
private fun LoadingScreen(screenWidth: Dp) {
    Card(
        modifier = Modifier
            .padding(
                top = 300.dp,
                start = screenWidth * 0.25f,
                end = screenWidth * 0.25f
            )
            .width(screenWidth * 0.5f)
            .height(screenWidth * 0.5f),
        colors = CardColors(
            MaterialTheme.colorScheme.secondary,
            Color.Unspecified,
            Color.Unspecified,
            Color.Unspecified
        )
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.CenterHorizontally)
                .padding(screenWidth * 0.125f)
        )
    }
}

@Composable
private fun FailedLoadingScreen(
    screenHeight: Dp,
    screenWidth: Dp
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight * 0.9f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.warning),
            contentDescription = "Warning",
            modifier = Modifier
                .width(screenWidth * 0.5f)
                .height(screenWidth * 0.5f),
            tint = Color.Unspecified
        )
        Text(
            text = "Something went wrong.",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyLarge
        )
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
            .padding(screenWidth * 0.05f)
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
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

    }
}

@Composable
fun RoomsAndArea(
    numberRooms: Int,
    area: Int
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            modifier = Modifier
                .width(screenWidth * 0.4f)
                .padding(screenWidth * 0.05f),
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
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            modifier = Modifier
                .width(screenWidth * 0.6f)
                .padding(screenWidth * 0.05f),
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
    }
}

// Bản xem trước với dữ liệu giả
@Preview(showBackground = true)
@Composable
fun ApartmentInfoPreview() {
    ApartmentManagerTheme {
        InfoPage(
            title = "Apartment Information",
            onBackClick = { },
            modifier = Modifier
        ) {
            val screenWidth = LocalConfiguration.current.screenWidthDp.dp
            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                ),
                modifier = Modifier
                    .padding(screenWidth * 0.05f)
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
                            text = "A Weirdly Big Cool Awesome Fabulous Luxurious Apartment",
                            style = MaterialTheme.typography.headlineLarge,
                            color = MaterialTheme.colorScheme.onSecondary,
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )
                    Text(
                        text = "Address: No.1, X St, Y City, Z Province, ABCD",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

            }

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                ElevatedCard(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    ),
                    modifier = Modifier
                        .width(screenWidth * 0.4f)
                        .padding(screenWidth * 0.05f),
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
                            text = "785",
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
                ElevatedCard(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    ),
                    modifier = Modifier
                        .width(screenWidth * 0.6f)
                        .padding(screenWidth * 0.05f),
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
                                append("9999 ")
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
                                modifier = Modifier.height(screenWidth * 0.1f)
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
            }

            InfoCardBar(
                painter1 = painterResource(id = R.drawable.owner),
                size1 = 0.08f,
                onClick = {},
                title = "Owners (1)",
                icon2 = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                size2 = 0.1f
            )
            Spacer(modifier = Modifier.height(screenWidth * 0.025f))
            InfoCardBar(
                painter1 = painterResource(id = R.drawable.manager),
                size1 = 0.08f,
                onClick = {},
                title = "Managers (2)",
                icon2 = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                size2 = 0.1f
            )
        }
    }
}
