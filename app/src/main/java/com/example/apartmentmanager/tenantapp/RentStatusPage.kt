package com.example.apartmentmanager.tenantapp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.apartmentmanager.R
import com.example.apartmentmanager.templates.InfoPage
import com.example.apartmentmanager.templates.ItemList
import com.example.apartmentmanager.ui.theme.ApartmentManagerTheme

//Function 3: Trang thanh toán
@Composable
fun RentStatusPage(
    modifier: Modifier,
    onFunctionChange: (Int) -> Unit
) {
    InfoPage(
        title = "Rent Status",
        onBackClick = { onFunctionChange(0) },
    ) {
        RoomNumber("P.1215")
        RentPeriod("10/2024")
        RentInfo()
        PaymentButton()
        ReportButton(onFunctionChange = onFunctionChange)
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
private fun RentPeriod(
    period: String
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
        Row(
            modifier = Modifier.padding(screenWidth * 0.05f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.calendar),
                contentDescription = "Period",
                modifier = Modifier.height(screenWidth * 0.1f)
            )
            Spacer(modifier = Modifier.width(screenWidth * 0.025f))
            Text(
                text = "Period",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = period,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}

@Composable
private fun RentInfo(
    ElecComsumption: Int = 0,
    waterComsumption: Int = 0,
    InternetFee: Int = 0,
    Service: Int = 0,
    total: String = "5,000,000đ",
    rentStatus: List<Int> = listOf(0, 0, 0, 0)
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    var showDetails by rememberSaveable { mutableStateOf(false) }

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
        ),
        onClick = { showDetails = !showDetails }
    ) {
        Row(
            modifier = Modifier.padding(screenWidth * 0.05f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.coin),
                contentDescription = "Amount to pay",
                modifier = Modifier.height(screenWidth * 0.1f)
            )
            Spacer(modifier = Modifier.width(screenWidth * 0.025f))
            Column() {
                Text(
                    text = "Amount to pay",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondary
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = total,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = if (!showDetails) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                contentDescription = if (!showDetails) "Show Details" else "Hide Details",
                modifier = Modifier.height(screenWidth * 0.1f),
                tint = MaterialTheme.colorScheme.onSecondary
            )
        }
        // Chi tiết thanh toán
        AnimatedVisibility(
            visible = showDetails,
            enter = androidx.compose.animation.expandVertically(),
            exit = androidx.compose.animation.shrinkVertically()
        ) {
            Column() {
                ItemList() {
                    Row(
                        modifier = Modifier.padding(
                            horizontal = screenWidth * 0.05f,
                            vertical = screenWidth * 0.025f
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Rent",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "4,500,000đ",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                }
                ItemList() {
                    Row(
                        modifier = Modifier.padding(
                            horizontal = screenWidth * 0.05f,
                            vertical = screenWidth * 0.025f
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Water",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "2 units,",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                        Spacer(modifier = Modifier.width(screenWidth * 0.025f))
                        Text(
                            text = "${waterComsumption},000đ",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                }

                ItemList() {
                    Row(
                        modifier = Modifier.padding(
                            horizontal = screenWidth * 0.05f,
                            vertical = screenWidth * 0.025f
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Electricity",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "100 units,",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                        Spacer(modifier = Modifier.width(screenWidth * 0.025f))
                        Text(
                            text = "${ElecComsumption},000đ",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                }

                ItemList() {
                    Row(
                        modifier = Modifier.padding(
                            horizontal = screenWidth * 0.05f,
                            vertical = screenWidth * 0.025f
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Services",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "${Service + InternetFee},000đ",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                }

                ItemList() {
                    Row(
                        modifier = Modifier.padding(
                            horizontal = screenWidth * 0.05f,
                            vertical = screenWidth * 0.025f
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Previous Periods",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "0đ",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                }

                ItemList() {
                    Row(
                        modifier = Modifier.padding(
                            horizontal = screenWidth * 0.05f,
                            vertical = screenWidth * 0.025f
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Paid Amount",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "-0đ",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                }
            }

        }


    }
}

@Composable
private fun PaymentButton() {
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
            MaterialTheme.colorScheme.primary,
            Color.Unspecified,
            Color.Unspecified,
            Color.Unspecified
        ),
        onClick = {}
    ) {
        Row(
            modifier = Modifier.padding(screenWidth * 0.05f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.coin),
                contentDescription = "Period",
                modifier = Modifier.height(screenWidth * 0.1f)
            )
            Spacer(modifier = Modifier.width(screenWidth * 0.025f))
            Text(
                text = "Pay now",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                contentDescription = "Period",
                modifier = Modifier.height(screenWidth * 0.1f),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
private fun ReportButton(
    onFunctionChange: (Int) -> Unit = {}
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
            MaterialTheme.colorScheme.errorContainer,
            Color.Unspecified,
            Color.Unspecified,
            Color.Unspecified
        ),
        onClick = { onFunctionChange(5) }
    ) {
        Row(
            modifier = Modifier.padding(screenWidth * 0.05f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.warning),
                contentDescription = "Report error",
                modifier = Modifier.height(screenWidth * 0.1f)
            )
            Spacer(modifier = Modifier.width(screenWidth * 0.025f))
            Column() {
                Text(
                    text = "Inaccurate billing?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
                Text(
                    text = "Send Report Here",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.error
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                contentDescription = "Period",
                modifier = Modifier.height(screenWidth * 0.1f),
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RentStatusPagePreviewLightMode() {
    ApartmentManagerTheme {
        RentStatusPage(modifier = Modifier, onFunctionChange = {})
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun RentStatusPagePreviewDarkMode() {
    ApartmentManagerTheme {
        RentStatusPage(modifier = Modifier, onFunctionChange = {})
    }
}