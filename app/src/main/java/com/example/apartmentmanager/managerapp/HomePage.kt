package com.example.apartmentmanager.managerapp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.twotone.*
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.delay


//Function 0: Trang chủ
@Composable
@OptIn(ExperimentalLayoutApi::class)
fun HomePage(
    modifier: Modifier,
    onLogOut: () -> Unit,
    lastFunction: Int,
    onFunctionChange: (Int) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val isNightMode = isSystemInDarkTheme()
    val scrollState = rememberScrollState()

    val enterFromTop = slideInVertically(initialOffsetY = { it })
    val exitToTop = slideOutVertically(targetOffsetY = { -it })

    var showLoginStatus by rememberSaveable { mutableStateOf(true) }
    var hasShowedLoginStatus by rememberSaveable { mutableStateOf(false) }
    var showLogoutDialog by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = modifier.fillMaxSize(),
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
        Column(
            modifier = modifier
                .fillMaxSize().verticalScroll(state = scrollState)
        ) {
            HeaderPane(modifier, onFunctionChange)

            FlowRow(
                modifier = Modifier.padding(top = screenWidth * 0.05f)
            ) {
                ApartmentInfoCard(onFunctionChange)
                RoomInfoCard(onFunctionChange)
                SearchTenantCard(onFunctionChange)
                UpdateRentCard(onFunctionChange)
                ReportListCard(onFunctionChange)
                AccountManagementCard(onFunctionChange)
            }
            SettingBar(onFunctionChange)
            LogOutBar(onClick = {showLogoutDialog = true})
            Spacer(modifier = modifier.height(20.dp))
        }

        LaunchedEffect(showLoginStatus, hasShowedLoginStatus) {
            if (!hasShowedLoginStatus && showLoginStatus) {
                delay(2000)
                showLoginStatus = false
                hasShowedLoginStatus = true
            }
        }

        AnimatedVisibility(
            visible = showLoginStatus && (lastFunction == -1) && !hasShowedLoginStatus,
            modifier = Modifier.align(Alignment.TopCenter),
            enter = enterFromTop,
            exit = exitToTop
        ) {
            LoginDialog(onClick = { showLoginStatus = false })
        }

        AnimatedVisibility(
            visible = showLogoutDialog,
            modifier = Modifier.align(Alignment.TopCenter),
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            LogoutDialog(onLogOut, onDismiss = {showLogoutDialog = false})
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
            .height(screenHeight * 0.25f)
            .clip(ShapeDefaults.ExtraLarge)
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

@Composable
private fun LoginDialog(onClick: () -> Unit) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = screenWidth * 0.05f),
        shape = ShapeDefaults.Large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f)
        )
    ) {
        Row(
            modifier = Modifier.padding(screenWidth * 0.05f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.info),
                contentDescription = null,
                modifier = Modifier
                    .height(screenWidth * 0.1f)
                    .scale(1.2f),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(screenWidth * 0.05f))
            Text(
                text = "Logged in as Manager",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = onClick,
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.scale(1.5f)
                )
            }

        }

    }
}

@Composable
private fun LogoutDialog(
    onLogOut: () -> Unit,
    onDismiss: () -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Text(
                text = "Log out",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clickable { onLogOut() }
                    .padding(horizontal = screenWidth * 0.025f)
            )
        },
        dismissButton = {
            Text(
                text = "Cancel",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clickable { onDismiss() }
                    .padding(horizontal = screenWidth * 0.025f)
            )
        },
        title = {
            Text(
                text = "Confirm Log Out",
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Spacer(modifier = Modifier.height(screenWidth * 0.1f))
            Text(
                text = "Are you sure you want to log out?",
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.bodyMedium
            )
        },
    )
}

@Composable
private fun ApartmentInfoCard(onFunctionChange: (Int) -> Unit) {
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
private fun RoomInfoCard(onFunctionChange: (Int) -> Unit) {
    InfoCard(
        icon = null,
        painter = painterResource(R.drawable.room),
        tint = Color.Unspecified,
        scale = 1f,
        title = "Rooms Information",
        onClick = { onFunctionChange(2) }
    )
}

@Composable
private fun SearchTenantCard(onFunctionChange: (Int) -> Unit) {
    InfoCard(
        icon = null,
        painter = painterResource(R.drawable.search),
        tint = Color.Unspecified,
        scale = 1f,
        title = "Search Tenant",
        onClick = { onFunctionChange(3) }
    )
}

@Composable
private fun UpdateRentCard(onFunctionChange: (Int) -> Unit) {
    InfoCard(
        icon = null,
        painter = painterResource(R.drawable.coin),
        tint = Color.Unspecified,
        scale = 1f,
        title = "Update Rent",
        onClick = { onFunctionChange(4) }
    )
}

@Composable
private fun ReportListCard(onFunctionChange: (Int) -> Unit) {
    InfoCard(
        icon = null,
        painter = painterResource(R.drawable.warning),
        tint = Color.Unspecified,
        scale = 1f,
        title = "Report List",
        onClick = { onFunctionChange(5) }
    )
}

@Composable
private fun AccountManagementCard(onFunctionChange: (Int) -> Unit) {
    InfoCard(
        icon = null,
        painter = painterResource(R.drawable.people),
        tint = Color.Unspecified,
        scale = 1f,
        title = "Account Management",
        onClick = { onFunctionChange(6) }
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
        onClick = { onFunctionChange(7) }
    )
}

@Composable
fun LogOutBar(
    onClick: () -> Unit
) {
    InfoCardBar(
        icon1 = Icons.AutoMirrored.Default.ExitToApp,
        tint1 = MaterialTheme.colorScheme.onPrimary,
        size1 = 0.07f,
        textColor = MaterialTheme.colorScheme.onPrimary,
        title = "Log out",
        onClick = onClick,
        cardColor = MaterialTheme.colorScheme.inversePrimary
    )
}

@Preview(showBackground = true)
@Composable
fun HomePagePreviewLightMode() {
    ApartmentManagerTheme {
        HomePage(modifier = Modifier, onLogOut = {}, onFunctionChange = {}, lastFunction = -1)

    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomePagePreviewDarkMode() {
    ApartmentManagerTheme {
        HomePage(modifier = Modifier, onLogOut = {}, onFunctionChange = {}, lastFunction = -1)
    }
}