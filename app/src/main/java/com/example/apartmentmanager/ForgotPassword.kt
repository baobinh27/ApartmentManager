package com.example.apartmentmanager

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.apartmentmanager.templates.InfoPage
import com.example.apartmentmanager.ui.theme.ApartmentManagerTheme

@Composable
fun ForgotPassword(
    onBackClick: () -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    var phoneNumber by rememberSaveable { mutableStateOf("") }

    InfoPage(
        title = "Forgot Password",
        onBackClick = onBackClick,
        painterBgLight = painterResource(id = R.drawable.buildings_background),
        painterBgDark = painterResource(id = R.drawable.buildings_background)
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
                modifier = Modifier.padding(screenWidth * 0.05f)
            ) {
                PhoneNumberBar(phoneNumber, onValueChange = { phoneNumber = it })
                Spacer(modifier = Modifier.height(screenWidth * 0.05f))
                Button(
                    onClick = {},
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Confirm")
                }
            }
        }
        Spacer(modifier = Modifier.height(screenWidth * 0.05f))
    }
}

@Composable
private fun PhoneNumberBar(
    phoneNumber: String,
    onValueChange: (String) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    Text(
        text = "Enter your phone number",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onBackground
    )
    Spacer(modifier = Modifier.height(screenWidth * 0.025f))
    TextField(
        value = phoneNumber,
        onValueChange = onValueChange,
        visualTransformation = PasswordVisualTransformation(),
        singleLine = true,
        shape = ShapeDefaults.Large,
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            focusedContainerColor = MaterialTheme.colorScheme.onTertiaryContainer,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
    )
    Spacer(modifier = Modifier.height(screenWidth * 0.05f))
}

@Preview(showBackground = true)
@Composable
fun ForgotPasswordPreviewLightMode() {
    ApartmentManagerTheme { ForgotPassword(onBackClick = {}) }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ForgotPasswordPreviewDarkMode() {
    ApartmentManagerTheme { ForgotPassword(onBackClick = {}) }
}