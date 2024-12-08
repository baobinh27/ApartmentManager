package com.example.apartmentmanager.tenantapp

import android.content.res.Configuration
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
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apartmentmanager.R
import com.example.apartmentmanager.templates.FailedLoadingScreen
import com.example.apartmentmanager.templates.InfoCardBar
import com.example.apartmentmanager.templates.InfoPage
import com.example.apartmentmanager.templates.LoadingScreen
import com.example.apartmentmanager.ui.theme.ApartmentManagerTheme
import com.google.firebase.firestore.FirebaseFirestore
import java.text.NumberFormat
import java.util.Locale

//Function 2: Thông tin phòng
@Composable
fun RoomInfoPage(
    modifier: Modifier,
    onFunctionChange: (Int) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val db = FirebaseFirestore.getInstance()
    val apartmentRef = db.collection("Room").document("P101")

    var rent by remember { mutableIntStateOf(0) }
    var area by remember { mutableIntStateOf(0) }
    var status by remember { mutableIntStateOf(0) }
    var roomID by remember { mutableStateOf("") }
    var water by remember { mutableIntStateOf(0) }
    var electricity by remember { mutableIntStateOf(0) }

    var failed by remember { mutableStateOf(false) }
    var doneLoading by remember { mutableStateOf(false) }

    var roomInfoLoaded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        apartmentRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("Firestore", "Document data: ${document.data}")
                    rent = document.getLong("roomCost")?.toInt() ?: 0
                    area = document.getLong("roomSize")?.toInt() ?: 0
                    status = document.getLong("status")?.toInt() ?: 0
                    roomID = document.id
                } else {
                    failed = true
                    Log.d("Firestore", "No such document")
                }
                roomInfoLoaded = true
            }
            .addOnFailureListener { exception ->
                Log.d("Firestore", "get failed with ", exception)
                failed = true
                roomInfoLoaded = true
            }
    }

    LaunchedEffect(roomInfoLoaded) {
        doneLoading = roomInfoLoaded && !failed
    }

    InfoPage(
        title = "Room Information",
        onBackClick = { onFunctionChange(0) },
    ) {
        if (!doneLoading) {
            if (!failed) {
                LoadingScreen()
            } else {
                FailedLoadingScreen()
            }
        } else {
            RoomNumber(roomID)
            AreaAndRent(area = area, rent = rent)
            PowerAndWater(power = 3500, water = 40000)

            InfoCardBar(
                painter1 = painterResource(R.drawable.people),
                title = "See Room Members",
                onClick = {}, //TODO
                size1 = 0.08f,
                icon2 = Icons.Default.KeyboardArrowDown,
                size2 = 0.08f,
                tint2 = MaterialTheme.colorScheme.onSecondary
            )
        }

    }
}

@Composable
private fun RoomNumber(
    roomID: String
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        modifier = Modifier
            .padding(top = screenWidth * 0.05f, start = screenWidth * 0.05f, end = screenWidth * 0.05f)
            .fillMaxWidth(),
        colors = CardColors(
            MaterialTheme.colorScheme.secondary,
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
                painter = painterResource(R.drawable.key),
                contentDescription = "Room Number",
                modifier = Modifier.height(screenWidth * 0.1f)
            )
            Spacer(modifier = Modifier.width(screenWidth * 0.025f))
            Text(
                text = "Room No.",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = roomID,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}

@Composable
private fun PowerAndWater(
    power: Int,
    water: Int,
) {
    val powerFormatted = NumberFormat.getNumberInstance(Locale.US).format(power)
    val waterFormatted = NumberFormat.getNumberInstance(Locale.US).format(water)
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = screenWidth * 0.05f)
    ) {
        Spacer(modifier = Modifier.width(screenWidth * 0.05f))
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            modifier = Modifier
                .width(screenWidth * 0.4f),
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
                        append(powerFormatted)
                        withStyle(
                            style = SpanStyle(
                                fontSize = 18.sp
                            )
                        ) {
                            append("đ")
                        }
                    },
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSecondary
                )
                Text(
                    text = "per unit",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSecondary
                )
                Spacer(modifier = Modifier.height(screenWidth * 0.025f))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.lightning),
                        contentDescription = "Monthly Rent",
                        modifier = Modifier.height(screenWidth * 0.08f)
                    )
                    Text(
                        text = "Electricity Price",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(screenWidth * 0.05f))
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            modifier = Modifier
                .width(screenWidth * 0.45f),
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
                        append(waterFormatted)
                        withStyle(
                            style = SpanStyle(
                                fontSize = 18.sp
                            )
                        ) {
                            append("đ")
                        }
                    },
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSecondary
                )
                Text(
                    text = "per cubic meter",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSecondary
                )
                Spacer(modifier = Modifier.height(screenWidth * 0.025f))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.water),
                        contentDescription = "Monthly Rent",
                        modifier = Modifier.height(screenWidth * 0.1f)
                    )
                    Text(
                        text = "Water\nPrice",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }

            }
        }
        Spacer(modifier = Modifier.width(screenWidth * 0.05f))
    }
}

@Composable
private fun AreaAndRent(
    area: Int,
    rent: Int
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val formattedRent = NumberFormat.getNumberInstance(Locale.US).format(rent)
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = screenWidth * 0.05f)
    ) {
        Spacer(modifier = Modifier.width(screenWidth * 0.05f))
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            modifier = Modifier
                .width(screenWidth * 0.3f),
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
                        append("$area")
                        withStyle(
                            style = SpanStyle(
                                fontSize = 18.sp
                            )
                        ) {
                            append(" m")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontSize = 12.sp,
                                baselineShift = BaselineShift(0.3f)
                            )
                        ) {
                            append("2")
                        }
                    },
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSecondary
                )
                Spacer(modifier = Modifier.height(screenWidth * 0.025f))
                Text(
                    text = "Room Area",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
        Spacer(modifier = Modifier.width(screenWidth * 0.05f))
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            modifier = Modifier
                .width(screenWidth * 0.55f),
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
                        append(formattedRent)
                        withStyle(
                            style = SpanStyle(
                                fontSize = 18.sp
                            )
                        ) {
                            append("đ")
                        }
                    },
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSecondary
                )
                Spacer(modifier = Modifier.height(screenWidth * 0.025f))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.coin),
                        contentDescription = "Monthly Rent",
                        modifier = Modifier.height(screenWidth * 0.1f),
                    )
                    Text(
                        text = "Monthly\nRent",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }

            }
        }
        Spacer(modifier = Modifier.width(screenWidth * 0.05f))
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun RoomInfoPagePreview() {
    ApartmentManagerTheme {
        InfoPage(
            title = "Room Information",
            onBackClick = {},
        ) {
            RoomNumber("P.1215")
            AreaAndRent(area = 206, rent = 45000000)
            PowerAndWater(power = 3500, water = 40000)
            InfoCardBar(
                painter1 = painterResource(R.drawable.people),
                title = "See Room Members (2)",
                onClick = {}, //TODO
                size1 = 0.08f,
                icon2 = Icons.Default.KeyboardArrowDown,
                size2 = 0.08f
            )
        }
    }
}