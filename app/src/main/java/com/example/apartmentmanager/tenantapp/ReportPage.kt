package com.example.apartmentmanager.tenantapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.apartmentmanager.templates.InfoPage
import com.example.apartmentmanager.ui.theme.ApartmentManagerTheme

//Function 5: Gửi báo cáo
@Composable
fun ReportPage(
    tenantID: String,
    onFunctionChange: (Int) -> Unit
) {
    InfoPage(
        onBackClick = { onFunctionChange(0) },
        title = "Send Report"
    ) {
        var title by rememberSaveable { mutableStateOf("") }
        var content by rememberSaveable { mutableStateOf("") }

        val screenWidth = LocalConfiguration.current.screenWidthDp.dp
        Spacer(modifier = Modifier.height(screenWidth * 0.05f))
        Card(
            modifier = Modifier
                .width(screenWidth * 0.9f)
                .align(Alignment.CenterHorizontally),
            colors = CardColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f),
                Color.Unspecified,
                Color.Unspecified,
                Color.Unspecified
            )
        ) {
            Column(
                modifier = Modifier.padding(screenWidth * 0.05f)
            ) {
                Text(
                    text = "Report title",
                    style = MaterialTheme.typography.titleMedium,
                    //modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(screenWidth * 0.025f))
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    singleLine = true,
                    shape = ShapeDefaults.Large,
                    modifier = Modifier
                        .fillMaxWidth(),
                        //.height(60.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        focusedContainerColor = MaterialTheme.colorScheme.onTertiaryContainer,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    ),
                )
                Spacer(modifier = Modifier.height(screenWidth * 0.05f))
                Text(
                    text = "Description",
                    style = MaterialTheme.typography.titleMedium,
                    //modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(screenWidth * 0.025f))
                TextField(
                    value = content,
                    onValueChange = { content = it },
                    shape = ShapeDefaults.Large,
                    modifier = Modifier
                        .fillMaxWidth()
                        //.height(400.dp)
                        .wrapContentHeight(),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        focusedContainerColor = MaterialTheme.colorScheme.onTertiaryContainer,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    ),
                )
                Spacer(modifier = Modifier.height(screenWidth * 0.05f))
                Button(
                    onClick = {},
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Send")
                }
            }
        }
        Spacer(modifier = Modifier.height(screenWidth * 0.05f))
    }
}

@Composable
private fun ReportTitle(
    title: String,
    onValueChange: (String) -> Unit
) {
    Text("Report Title:")
    TextField(
        value = title,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        singleLine = true
    )
}

@Preview(showBackground = true)
@Composable
fun ReportPagePreviewLightMode() {
    ApartmentManagerTheme {
        ReportPage(tenantID = "T00001", onFunctionChange = {})
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ReportPagePreviewDarkMode() {
    ApartmentManagerTheme {
        ReportPage(tenantID = "T00001", onFunctionChange = {})
    }
}