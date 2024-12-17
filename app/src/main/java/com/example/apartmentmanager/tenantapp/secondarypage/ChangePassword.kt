package com.example.apartmentmanager.tenantapp.secondarypage

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.apartmentmanager.R
import com.example.apartmentmanager.templates.InfoPage
import com.example.apartmentmanager.ui.theme.ApartmentManagerTheme
import com.example.apartmentmanager.ui.theme.DarkGreen
import com.example.apartmentmanager.ui.theme.LightGreen
import kotlinx.coroutines.delay

@Composable
fun ChangePassword(
    onFunctionChange: (Int) -> Unit
) {
    var oldPassword by rememberSaveable { mutableStateOf("") }
    var newPassword by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var showSuccessDialog by rememberSaveable { mutableStateOf(false) }
    var showOldPasswordError by rememberSaveable { mutableStateOf(false) }
    var showConfirmPasswordError by rememberSaveable { mutableStateOf(false) }

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    LaunchedEffect(showSuccessDialog) {
        if (showSuccessDialog) {
            oldPassword = ""
            newPassword = ""
            confirmPassword = ""
            delay(2000)
            showSuccessDialog = false
        }
    }
    LaunchedEffect(showOldPasswordError) {
        if (showOldPasswordError) {
            delay(2000)
            showOldPasswordError = false
        }
    }
    LaunchedEffect(showConfirmPasswordError) {
        if (showConfirmPasswordError) {
            delay(2000)
            showConfirmPasswordError = false
        }
    }

    Box {
        InfoPage(
            title = "Change Password",
            onBackClick = { onFunctionChange(6) },
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
                    OldPasswordBar(oldPassword, onValueChange = { oldPassword = it })
                    NewPasswordBar(newPassword, onValueChange = { newPassword = it })
                    ConfirmPasswordBar(confirmPassword, onValueChange = { confirmPassword = it })
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

        AnimatedVisibility(
            visible = showSuccessDialog,
            enter = slideInVertically(initialOffsetY = { -it }),
            exit = slideOutVertically(targetOffsetY = { -it }),
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = screenWidth * 0.05f)
                    .align(Alignment.Center),
                shape = RectangleShape,
                colors = CardColors(
                    containerColor = if (isSystemInDarkTheme()) DarkGreen else LightGreen,
                    Color.Unspecified,
                    Color.Unspecified,
                    Color.Unspecified
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
                            .scale(1.25f)
                            .width(screenWidth * 0.08f),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(screenWidth * 0.05f))
                    Text(
                        text = "Password changed successfully!",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
        AnimatedVisibility(
            visible = showOldPasswordError,
            enter = slideInVertically(initialOffsetY = { -it }),
            exit = slideOutVertically(targetOffsetY = { -it }),

        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = screenWidth * 0.05f)
                    .align(Alignment.Center),
                shape = RectangleShape,
                colors = CardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    Color.Unspecified,
                    Color.Unspecified,
                    Color.Unspecified
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
                            .scale(1.25f)
                            .width(screenWidth * 0.08f),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(screenWidth * 0.05f))
                    Text(
                        text = "Old password is incorrect!",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = showConfirmPasswordError,
            enter = slideInVertically(initialOffsetY = { -it }),
            exit = slideOutVertically(targetOffsetY = { -it }),

            ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = screenWidth * 0.05f)
                    .align(Alignment.Center),
                shape = RectangleShape,
                colors = CardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    Color.Unspecified,
                    Color.Unspecified,
                    Color.Unspecified
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
                            .scale(1.25f)
                            .width(screenWidth * 0.08f),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(screenWidth * 0.05f))
                    Text(
                        text = "Confirm password does not match!",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }

}

@Composable
private fun OldPasswordBar(
    oldPassword: String,
    onValueChange: (String) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    var showPassword by rememberSaveable { mutableStateOf(false) }
    Text(
        text = "Old password",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onBackground
    )
    Spacer(modifier = Modifier.height(screenWidth * 0.025f))
    Box {
        TextField(
            value = oldPassword,
            onValueChange = onValueChange,
            visualTransformation = if (!showPassword) PasswordVisualTransformation() else VisualTransformation.None,
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
        IconButton(
            onClick = { showPassword = !showPassword },
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Icon(
                painter = painterResource(if (showPassword) R.drawable.show_password else R.drawable.hide_password),
                contentDescription = "Show password",
            )
        }
    }

    Spacer(modifier = Modifier.height(screenWidth * 0.05f))
}

@Composable
private fun NewPasswordBar(
    newPassword: String,
    onValueChange: (String) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    var showPassword by rememberSaveable { mutableStateOf(false) }
    Text(
        text = "New password",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onBackground
    )
    Spacer(modifier = Modifier.height(screenWidth * 0.025f))
    Box{
        TextField(
            value = newPassword,
            onValueChange = onValueChange,
            visualTransformation = if (!showPassword) PasswordVisualTransformation() else VisualTransformation.None,
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
        IconButton(
            onClick = { showPassword = !showPassword },
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Icon(
                painter = painterResource(if (showPassword) R.drawable.show_password else R.drawable.hide_password),
                contentDescription = "Show password",
            )
        }
    }
    Spacer(modifier = Modifier.height(screenWidth * 0.05f))
}

@Composable
private fun ConfirmPasswordBar(
    confirmPassword: String,
    onValueChange: (String) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    var showPassword by rememberSaveable { mutableStateOf(false) }
    Text(
        text = "Confirm password",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onBackground
    )
    Spacer(modifier = Modifier.height(screenWidth * 0.025f))
    Box{
        TextField(
            value = confirmPassword,
            onValueChange = onValueChange,
            visualTransformation = if (!showPassword) PasswordVisualTransformation() else VisualTransformation.None,
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
        IconButton(
            onClick = { showPassword = !showPassword },
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Icon(
                painter = painterResource(if (showPassword) R.drawable.show_password else R.drawable.hide_password),
                contentDescription = "Show password",
            )
        }
    }
    Spacer(modifier = Modifier.height(screenWidth * 0.05f))
}

@Preview(showBackground = true)
@Composable
fun ChangePasswordPreviewLightMode() {
    ApartmentManagerTheme {
        ChangePassword(onFunctionChange = {})
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ChangePasswordPreviewDarkMode() {
    ApartmentManagerTheme {
        ChangePassword(onFunctionChange = {})
    }
}