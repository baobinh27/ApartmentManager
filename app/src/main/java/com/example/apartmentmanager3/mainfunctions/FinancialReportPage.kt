package com.example.apartmentmanager3.mainfunctions

import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.apartmentmanager3.ui.theme.ApartmentManager3Theme

//Function 4: Báo cáo tài chính
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinancialReportPage(
    modifier: Modifier,
    onFunctionChange: (Int) -> Unit
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val extraPadding = screenWidth * 0.05f
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Financial Report", color = MaterialTheme.colorScheme.onPrimary)
                },
                navigationIcon = {
                    IconButton(onClick = { onFunctionChange(0) }) {
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
            Column(
                modifier = modifier
                    .padding(
                        top = innerPadding.calculateTopPadding() + extraPadding,
                        bottom = 0.dp,
                        start = extraPadding,
                        end = extraPadding
                    )
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Text(text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla fermentum egestas dui sit amet scelerisque. Cras aliquet mollis mattis. Cras et mi enim. Nam aliquam urna eu condimentum commodo. Praesent ultrices ligula a feugiat scelerisque. Donec dictum, nulla at viverra euismod, erat sapien varius orci, condimentum hendrerit sapien mi vel enim. Donec dictum consectetur nisi quis molestie. Pellentesque ut metus urna. Nullam elementum purus quis pellentesque congue. Vivamus ipsum lorem, rhoncus eget porta ac, elementum id arcu. Vestibulum nec porta tellus, eu feugiat magna. Maecenas nec est lacinia, vulputate odio at, molestie dui.")
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun FinancialReportPagePreviewLightMode() {
    ApartmentManager3Theme {
        FinancialReportPage(modifier = Modifier, onFunctionChange = {})
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FinancialReportPagePreviewDarkMode() {
    ApartmentManager3Theme {
        FinancialReportPage(modifier = Modifier, onFunctionChange = {})
    }
}