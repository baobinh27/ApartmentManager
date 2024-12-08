package com.example.apartmentmanager.templates

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.apartmentmanager.R

//Khuôn mẫu các trang chức năng
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoPage(
    modifier: Modifier = Modifier,
    title: String,
    onBackClick: () -> Unit,
    painterBgLight: Painter = painterResource(R.drawable.room_background_4),
    painterBgDark: Painter = painterResource(R.drawable.room_background),
    content: @Composable ColumnScope.() -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val extraPadding = screenWidth * 0.05f
    val isNightMode = isSystemInDarkTheme()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = title, color = MaterialTheme.colorScheme.onPrimary) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(MaterialTheme.colorScheme.primary)
            )
        },
        content = { innerPadding ->
            Box() {
                if (painterBgLight != null && painterBgDark != null) {
                    Image(
                        painter = if (isNightMode) painterBgDark else painterBgLight,
                        contentDescription = "Background",
                        contentScale = ContentScale.Crop,
                        modifier = modifier.fillMaxSize()
                    )
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = Color.Black.copy(alpha = 0.2f)
                    ) {}
                }
                Column(
                    modifier = modifier
                        .padding(top = innerPadding.calculateTopPadding())
                        .fillMaxSize()
                        .background(Color.Transparent)
                        .verticalScroll(rememberScrollState()),
                    content = content
                )
            }

        }
    )
}
