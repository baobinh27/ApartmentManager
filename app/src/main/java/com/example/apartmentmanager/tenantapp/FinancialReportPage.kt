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
import java.text.NumberFormat
import java.util.Locale

//Function 4: Báo cáo tài chính
@Composable
fun FinancialReportPage(
    tenantID: String,
    onFunctionChange: (Int) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    InfoPage(
        title = "Financial Report",
        onBackClick = { onFunctionChange(0) },
    ) {
        // Dữ liệu mẫu
        val month = listOf(11, 10, 9)
        val elec = listOf(115, 175, 131)
        val water = listOf(3, 4, 4)
        val services = 200000
        val rent = 3500000
        for (i in 0..2) {
            RentMonthInfo(
                month = "${month[i]}/2024",
                elec = elec[i],
                water = water[i],
                services = services,
                rent = rent
            )
        }
        Spacer(modifier = Modifier.height(screenWidth * 0.05f))
    }
}

@Composable
private fun RentMonthInfo(
    month: String,
    elec: Int,
    water: Int,
    services: Int,
    rent: Int
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    var showDetails by rememberSaveable { mutableStateOf(false) }
    val total = rent + services + elec*3500 + water*38000
    val numberFormat = NumberFormat.getNumberInstance(Locale("en", "US"))


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
                contentDescription = "Monthly Rent",
                modifier = Modifier.height(screenWidth * 0.1f)
            )
            Spacer(modifier = Modifier.width(screenWidth * 0.025f))
            Column() {
                Text(
                    text = "Period: $month",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondary
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "${numberFormat.format(total)}đ",
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
                            text = "${numberFormat.format(rent)}đ",
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
                            text = "$water units,",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                        Spacer(modifier = Modifier.width(screenWidth * 0.025f))
                        Text(
                            text = "${numberFormat.format(water * 38000)}đ",
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
                            text = "$elec units,",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                        Spacer(modifier = Modifier.width(screenWidth * 0.025f))
                        Text(
                            text = "${numberFormat.format(elec * 3500)}đ",
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
                            text = "${numberFormat.format(services)}đ",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FinancialReportPagePreviewLightMode() {
    ApartmentManagerTheme {
        FinancialReportPage(tenantID = "T00001", onFunctionChange = {})
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FinancialReportPagePreviewDarkMode() {
    ApartmentManagerTheme {
        FinancialReportPage(tenantID = "T00001", onFunctionChange = {})
    }
}