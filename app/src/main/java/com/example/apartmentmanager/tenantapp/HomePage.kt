package com.example.apartmentmanager.tenantapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.twotone.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.apartmentmanager.R
import com.example.apartmentmanager.templates.InfoCard
import com.example.apartmentmanager.templates.InfoCardBar
import com.example.apartmentmanager.ui.theme.ApartmentManagerTheme


//Function 0: Trang chủ
@Composable
@OptIn(ExperimentalLayoutApi::class)
fun HomePage(
    modifier: Modifier,
    onLogOut: () -> Unit,
    onFunctionChange: (Int) -> Unit
) {
    val scrollState = rememberScrollState()
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val isNightMode = isSystemInDarkTheme()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Ảnh nền phía sau, chiếm toàn bộ màn hình
        Image(
            painter = painterResource(if (isNightMode) R.drawable.room_background else R.drawable.room_background_4),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black.copy(alpha = 0.2f)
        ) {}

        // Phần nội dung chính
        Column(
            modifier = modifier
                .fillMaxSize().verticalScroll(state = scrollState)
        ) {
            Spacer(modifier = modifier.height(screenWidth * 0.05f))
            HeaderPane(modifier, onFunctionChange)

            FlowRow(
                modifier = Modifier.padding(top = screenWidth * 0.05f)
            ) {
                //Các thẻ chức năng xuất hiện trong menu
                ApartmentInfoCard(onFunctionChange)
                RoomInfoCard(onFunctionChange)
                RentStatusCard(onFunctionChange)
                FinancialReportCard(onFunctionChange)
                SendReportCard(onFunctionChange)
            }

            SettingBar(onFunctionChange)
            LogOutBar(onLogOut)
            Spacer(modifier = modifier.height(20.dp))
        }
    }

}

//25% màn hình bên trên của menu để hiện các thông tin chính
@Composable
fun HeaderPane(
    modifier: Modifier,
    onFunctionChange: (Int) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(screenHeight * 0.25f).clip(ShapeDefaults.ExtraLarge)
            .background(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f))
    ) {
        IconButton(
            onClick = { onFunctionChange(7) },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.dp)
                .scale(1f),
        ) {
            Icon(
                painter = painterResource(R.drawable.notifications),
                contentDescription = "Notifications",
                tint = Color.Unspecified,
            )
        }
        Column(
            modifier = Modifier
                .padding(start = 30.dp, top = 30.dp)
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
            painter = painterResource(R.drawable.apartment2),
            contentDescription = "Home",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .width(screenWidth * 0.35f)
                .scale(1f)
                .padding(end = 20.dp, top = 30.dp),
            tint = Color.Unspecified
        )
    }
}


//Các thẻ chức năng
@Composable
fun ApartmentInfoCard(onFunctionChange: (Int) -> Unit) {
    InfoCard(
        icon = null,
        painter = painterResource(R.drawable.apartment),
        tint = Color.Unspecified,
        scale = 1f,
        title = "Apartment Information",
        onClick = { onFunctionChange(1) }
    )
}

@Composable
fun RoomInfoCard(onFunctionChange: (Int) -> Unit) {
    InfoCard(
        icon = null,
        painter = painterResource(R.drawable.room),
        tint = Color.Unspecified,
        scale = 1f,
        title = "Room Information",
        onClick = { onFunctionChange(2) }
    )
}

@Composable
fun RentStatusCard(onFunctionChange: (Int) -> Unit) {
    InfoCard(
        //icon = Icons.Default.Info,
        icon = null,
        painter = painterResource(R.drawable.coin),
        //painter = null,
        tint = Color.Unspecified,
        scale = 1f,
        title = "Rent Status",
        onClick = { onFunctionChange(3) }
    )
}

@Composable
fun FinancialReportCard(onFunctionChange: (Int) -> Unit) {
    InfoCard(
        icon = null,
        painter = painterResource(R.drawable.financial_report),
        tint = Color.Unspecified,
        scale = 1f,
        title = "Financial Report",
        onClick = { onFunctionChange(4) }
    )
}

//@Composable
//fun ModifyInfoCard(onFunctionChange: (Int) -> Unit) {
//    InfoCard(
//        icon = null,
//        painter = painterResource(R.drawable.modify),
//        tint = Color.Unspecified,
//        scale = 1f,
//        title = "Modify Room Information",
//        onClick = {onFunctionChange(5)}
//    )
//}

@Composable
fun SendReportCard(onFunctionChange: (Int) -> Unit) {
    InfoCard(
        icon = null,
        painter = painterResource(R.drawable.warning),
        tint = Color.Unspecified,
        scale = 1f,
        title = "Send Report",
        onClick = { onFunctionChange(5) }
    )
}

@Composable
fun SettingBar(
    onFunctionChange: (Int) -> Unit
) {
    InfoCardBar(
        icon1 = Icons.TwoTone.Settings,
        tint1 = MaterialTheme.colorScheme.onSecondary,
        title = "Settings",
        size1 = 0.07f,
        onClick = { onFunctionChange(6) }
    )
}

@Composable
fun LogOutBar(
    onLogOut: () -> Unit
) {
    InfoCardBar(
        icon1 = Icons.AutoMirrored.Default.ExitToApp,
        tint1 = MaterialTheme.colorScheme.onPrimary,
        size1 = 0.07f,
        textColor = MaterialTheme.colorScheme.onPrimary,
        title = "Log out",
        onClick = onLogOut,
        cardColor = MaterialTheme.colorScheme.inversePrimary
    )
}


@Preview(showBackground = true)
@Composable
fun HomePagePreviewLightMode() {
    ApartmentManagerTheme {
        HomePage(modifier = Modifier, onLogOut = {}, onFunctionChange = {})

    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomePagePreviewDarkMode() {
    ApartmentManagerTheme {
        HomePage(modifier = Modifier, onLogOut = {}, onFunctionChange = {})
    }
}