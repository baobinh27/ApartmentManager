package com.example.apartmentmanager

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
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
import com.example.apartmentmanager.templates.InfoPage
import com.example.apartmentmanager.ui.theme.ApartmentManagerTheme

@Composable
fun GettingStarted(
    onBackClick: () -> Unit,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    var name by rememberSaveable { mutableStateOf("ABCD") }
    InfoPage(
        title = "What is Apartment Manager?",
        onBackClick = onBackClick,
        painterBgLight = painterResource(id = R.drawable.buildings_background),
        painterBgDark = painterResource(id = R.drawable.buildings_background)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth().padding(screenWidth * 0.05f)
                .align(Alignment.CenterHorizontally),
            colors = CardColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                Color.Unspecified,
                Color.Unspecified,
                Color.Unspecified
            )
        ) {
            Column(
                modifier = Modifier.padding(screenWidth * 0.05f)
            ) {
                Text(
                    text = "This is an application for managing an apartments, specifically $name Apartment.",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(screenWidth * 0.05f))
                Text(
                    text = "You get your account by registering with your personal information from the managers of this apartment.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(screenWidth * 0.05f))
                Text(
                    text = "After getting your account, you can use it to check your rent, and notify the managers if something go wrong.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(screenWidth * 0.05f))
                Text(
                    text = "Your account also help us to better manage the apartment and the tenant's information.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun GettingStartedPreviewLightMode() {
    ApartmentManagerTheme {
        GettingStarted(onBackClick = {})

    }
}