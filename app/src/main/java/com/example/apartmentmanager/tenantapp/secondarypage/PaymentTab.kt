package com.example.apartmentmanager.tenantapp.secondarypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.apartmentmanager.R
import com.example.apartmentmanager.templates.InfoPage
import com.example.apartmentmanager.ui.theme.ApartmentManagerTheme

@Composable
fun PaymentTab(
    changeSecondary: (Int) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    InfoPage(
        title = "Payment",
        onBackClick = { changeSecondary(0) },
    ) {
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
                modifier = Modifier.padding(screenWidth * 0.05f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.rickrolling_qr_code),
                    contentDescription = "QR Code",
                    modifier = Modifier
                        .height(screenWidth * 0.8f)
                        .width(screenWidth * 0.8f)
                )
                Spacer(modifier = Modifier.height(screenWidth * 0.05f))
                Text(
                    text = "Payment QR Code",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(screenWidth * 0.025f))
                Text(
                    text = "If you can't scan the code, consider bank transfer to the following account:",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(screenWidth * 0.025f))
                Text(
                    text = "Bank: Vietcombank",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Account: 1016637332",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Name: LIEU BAO BAO",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                //Spacer(modifier = Modifier.height(screenWidth * 0.025f))
                Text(
                    text = "Content: <Your room> rent payment for <Period>"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PaymentTabPreview() {
    ApartmentManagerTheme {
        PaymentTab(changeSecondary = {})
    }
}