package com.example.apartmentmanager.managerapp

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
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apartmentmanager.ManagerApp
import com.example.apartmentmanager.R
import com.example.apartmentmanager.templates.InfoCardBar
import com.example.apartmentmanager.templates.InfoPage
import com.example.apartmentmanager.ui.theme.ApartmentManagerTheme
import java.text.NumberFormat
import java.util.Locale

@Composable
fun UpdateRentPage(
    modifier: Modifier = Modifier,
    onFunctionChange: (Int) -> Unit
) {
    val roomList = listOf("P101", "P102", "P103", "P104", "P105", "P106", "P107", "P108", "P109", "P110", "P111", "P112", "P113", "P114", "P115", "P116", "P117", "P118", "P119", "P120")
    InfoPage(
        title = "Update Rent",
        onBackClick = { onFunctionChange(0) }
    ) {
        //PowerAndWater(power = 3500, water = 40000)

        Spacer(modifier = Modifier.height(LocalConfiguration.current.screenWidthDp.dp * 0.05f))
        for (room in roomList) {
            InfoCardBar(
                painter1 = painterResource(R.drawable.coin),
                size1 = 0.07f,
                title = room,
                onClick = {}, //TODO
                icon2 = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                size2 = 0.07f
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

@Preview(showBackground = true)
@Composable
fun ManagerAppPreviewLightMode() {
    ApartmentManagerTheme {
        UpdateRentPage(modifier = Modifier, onFunctionChange = {})
    }
}
