package com.example.apartmentmanager3.mainfunctions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.twotone.*
import androidx.compose.material.icons.automirrored.*
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.apartmentmanager3.templates.InfoCard
import com.example.apartmentmanager3.ui.theme.ApartmentManager3Theme


//Function 0: Trang chủ
@Composable
@OptIn(ExperimentalLayoutApi::class)
fun HomePage(
    modifier: Modifier,
    onLogOut: () -> Unit,
    onFunctionChange: (Int) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
    ) {
        item {
            HeaderPane(modifier, screenHeight, onLogOut)
        }

        item {
            FlowRow(
                modifier = Modifier.padding(top = screenWidth * 0.05f)
            ) {
                ApartmentInfoCard(onFunctionChange)
                RoomInfoCard(onFunctionChange)
                RentStatusCard(onFunctionChange)
                FinancialReportCard(onFunctionChange)
                ModifyInfoCard(onFunctionChange)
                SendReportCard(onFunctionChange)
                SettingsCard(onFunctionChange)
            }

        }
    }
}

@Composable
fun HeaderPane(
    modifier: Modifier,
    screenHeight: Dp,
    onLogOut: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(screenHeight * 0.4f)
            .background(color = MaterialTheme.colorScheme.primary),
    ) {
        IconButton(
            onClick = onLogOut,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.dp)
                .scale(1.5f),
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ExitToApp,
                contentDescription = "Log out",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

        Column(
            modifier = Modifier
                .padding(start = 30.dp)
                .align(Alignment.CenterStart)
        ) {
            Text(
                text = "My Apartment",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = "lorem ipsum.",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        Icon(
            imageVector = Icons.Default.Home,
            contentDescription = "Home",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .scale(3.5f)
                .padding(50.dp),
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}



//Các thẻ chức năng
@Composable
fun ApartmentInfoCard(onFunctionChange: (Int) -> Unit) {
    InfoCard(
        icon = Icons.TwoTone.Info,
        title = "Apartment Information",
        onFunctionChange = onFunctionChange,
        functionId = 1
    )
}

@Composable
fun RoomInfoCard(onFunctionChange: (Int) -> Unit) {
    InfoCard(
        icon = Icons.TwoTone.AccountBox,
        title = "Room Information",
        onFunctionChange = onFunctionChange,
        functionId = 2
    )
}

@Composable
fun RentStatusCard(onFunctionChange: (Int) -> Unit) {
    InfoCard(
        icon = Icons.TwoTone.Create,
        title = "Rent Status",
        onFunctionChange = onFunctionChange,
        functionId = 3
    )
}

@Composable
fun FinancialReportCard(onFunctionChange: (Int) -> Unit) {
    InfoCard(
        icon = Icons.TwoTone.DateRange,
        title = "Financial Report",
        onFunctionChange = onFunctionChange,
        functionId = 4
    )
}

@Composable
fun ModifyInfoCard(onFunctionChange: (Int) -> Unit) {
    InfoCard(
        icon = Icons.TwoTone.Edit,
        title = "Modify Room Information",
        onFunctionChange = onFunctionChange,
        functionId = 5
    )
}

@Composable
fun SendReportCard(onFunctionChange: (Int) -> Unit) {
    InfoCard(
        icon = Icons.TwoTone.Warning,
        title = "Send Report",
        onFunctionChange = onFunctionChange,
        functionId = 6
    )
}

@Composable
fun SettingsCard(onFunctionChange: (Int) -> Unit) {
    InfoCard(
        icon = Icons.TwoTone.Settings,
        title = "Settings",
        onFunctionChange = onFunctionChange,
        functionId = 7
    )
}


@Preview(showBackground = true)
@Composable
fun HomePagePreviewLightMode() {
    ApartmentManager3Theme {
        HomePage(modifier = Modifier, onLogOut = {}, onFunctionChange = {})

    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomePagePreviewDarkMode() {
    ApartmentManager3Theme {
        HomePage(modifier = Modifier, onLogOut = {}, onFunctionChange = {})
    }
}