package com.example.apartmentmanager

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.apartmentmanager.ui.theme.ApartmentManagerTheme
import com.google.firebase.firestore.FirebaseFirestore

//Giao diện trang đăng nhập
@Composable
fun LoginPage(
    modifier: Modifier = Modifier,
    onLoginClick: (Int, String) -> Unit
) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var loading by rememberSaveable { mutableStateOf(false) }
    val filled = username.isNotEmpty() && password.isNotEmpty()
    var showErrorDialog by rememberSaveable { mutableStateOf(false) }
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // Ảnh nền phía sau, chiếm toàn bộ màn hình
        Image(
            painter = painterResource(id = R.drawable.buildings_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Giao diện đăng nhập nằm ở giữa màn hình
        Card(
            modifier = Modifier
                .width(screenWidth * 0.9f)
                .align(Alignment.Center),
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
                Spacer(modifier = Modifier.height(screenWidth * 0.05f))
                // Tiêu đề đăng nhập
                Text(
                    text = "Login to Apartment",
                    modifier = Modifier,
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(screenWidth * 0.1f))

                // Trường nhập tên đăng nhập và mật khẩu
                UsernameBar(
                    username = username,
                    onUsernameChange = { username = it },
                    onClear = { username = "" }
                )
                Spacer(modifier = Modifier.height(screenWidth * 0.05f))
                PasswordBar(
                    password = password,
                    onPasswordChange = { password = it },
                    onClear = { password = "" }
                )
                Spacer(modifier = Modifier.height(screenWidth * 0.05f))

                // Các nút đăng nhập và đăng ký
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Nút đăng ký
                    OutlinedButton(
                        onClick = { },
                        modifier = Modifier.width(screenWidth * 0.25f),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                    ) {
                        Text(
                            text = "Sign up",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center
                        )
                    }

                    // Nút đăng nhập
                    Button(
                        onClick = {
                            loading = true
                            isValid(username, password) { role, userID ->
                                loading = false
                                when (role) {
                                    0 -> {
                                        showErrorDialog = true
                                    }

                                    else -> {
                                        onLoginClick(role, userID)
                                    }
                                }
                            }
                        },
                        modifier = Modifier.width(screenWidth * 0.3f),
                        // Chỉ nhấn được khi đã nhập cả username và password
                        enabled = filled
                    ) {
                        if (loading) {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        } else {
                            Text(
                                text = "Sign In",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }

                //Quên mật khẩu
                Text(
                    text = "Forgot password?",
                    modifier = Modifier.padding(top = 10.dp),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontFamily = FontFamily.SansSerif,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        //Thông báo nhập sai tên đăng nhập hoặc mật khẩu
        // Phần bên trong LaunchedEffect sẽ chạy dưới nền, bắt đầu từ khi có thông báo lỗi
        LaunchedEffect(showErrorDialog) {
            if (showErrorDialog) {
                //Đợi 2 giây trước khi ẩn thông báo lỗi
                kotlinx.coroutines.delay(2000)
                showErrorDialog = false
            }
        }
        AnimatedVisibility(
            visible = showErrorDialog,
            //Tạo hiệu ứng xuất hiện và biến mất
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it })
        ) {
            ElevatedCard(
                modifier = modifier
                    .width(screenWidth)
                    .padding(
                        start = screenWidth * 0.1f,
                        top = screenHeight * 0.8f,
                        end = screenWidth * 0.1f
                    ),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.7f))
            ) {
                IconButton(
                    onClick = { showErrorDialog = false },
                    modifier = modifier.align(Alignment.End)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close dialog",
                    )
                }
                Text(
                    text = "Username or password invalid!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error,
                    modifier = modifier.padding(horizontal = screenWidth * 0.05f)
                )
                Spacer(modifier = Modifier.height(screenWidth * 0.1f))
            }
        }
    }
}


//Trường nhập tên đăng nhập
@Composable
private fun UsernameBar(
    username: String,
    modifier: Modifier = Modifier,
    onUsernameChange: (String) -> Unit,
    onClear: () -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.AccountBox,
            contentDescription = "Username",
            modifier = Modifier
                .padding(end = 10.dp)
                .height(screenWidth * 0.08f)
                .width(screenWidth * 0.08f),
            tint = MaterialTheme.colorScheme.onSecondary
        )
        Box(modifier = modifier.fillMaxWidth()) {
            TextField(
                shape = ShapeDefaults.Large,
                value = username,
                onValueChange = onUsernameChange,
                placeholder = { Text("Username") },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    focusedContainerColor = MaterialTheme.colorScheme.onTertiaryContainer,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),

                )
            IconButton(
                enabled = username.isNotEmpty(),
                onClick = onClear,
                modifier = modifier.align(Alignment.CenterEnd).padding(end = screenWidth * 0.02f)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = if (username.isNotEmpty()) MaterialTheme.colorScheme.onSecondary else Color.Transparent
                )
            }
        }

    }

}


//Trường nhập mật khẩu
@Composable
private fun PasswordBar(
    password: String,
    modifier: Modifier = Modifier,
    onPasswordChange: (String) -> Unit,
    onClear: () -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    var showPassword by rememberSaveable { mutableStateOf(false) }
    Row(
        modifier = Modifier.width(screenWidth * 0.8f),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Lock,
            contentDescription = "Password",
            modifier = Modifier
                .padding(end = 10.dp)
                .height(screenWidth * 0.08f)
                .width(screenWidth * 0.08f),
            tint = MaterialTheme.colorScheme.onSecondary
        )
        Box(modifier = modifier.fillMaxWidth()) {
            TextField(
                shape = ShapeDefaults.Large,
                value = password,
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                onValueChange = onPasswordChange,
                placeholder = { Text("Password") },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    focusedContainerColor = MaterialTheme.colorScheme.onTertiaryContainer,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
            )
            Row(
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                IconButton(
                    enabled = password.isNotEmpty(),
                    onClick = onClear,
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = if (password.isNotEmpty()) MaterialTheme.colorScheme.onSecondary else Color.Transparent
                    )
                }
                IconButton(
                    onClick = { showPassword = !showPassword },
                    modifier = modifier.padding(end = screenWidth * 0.02f)
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (showPassword) R.drawable.show_password else R.drawable.hide_password
                        ),
                        contentDescription = "Show password",
                        tint = MaterialTheme.colorScheme.onTertiary,
                    )
                }
            }

        }
        Spacer(modifier = Modifier.width(screenWidth * 0.04f))
        // Nút hiển thị mật khẩu


    }

}

// Kiểm tra tên đăng nhập và mật khẩu có hợp lệ hay không
fun isValid(username: String, password: String, onResult: (Int, String) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    val userRef = db.collection("authentication")
    var userID = ""

    userRef.get()
        .addOnSuccessListener { result ->
            var isValid = false
            for (document in result) {
                if (document.getString("username") == username &&
                    document.getString("password") == password &&
                    document.getBoolean("isActive") == true
                ) {
                    isValid = true
                    userID = document.id
                    break
                }
            }
            if (userID.isNotEmpty()) {
                when (userID[0]) {
                    'T' -> onResult(1, userID)
                    'M' -> onResult(2, userID)
                    'O' -> onResult(3, userID)
                    else -> onResult(0, "") // Trả về 0 nếu không hợp lệ
                }
            } else {
                onResult(0, "") // Không tìm thấy tài khoản phù hợp
            }
        }
        .addOnFailureListener { exception ->
            // Thông báo lỗi ra logcat nếu có
            Log.d("Firestore", "Error getting documents: ", exception)
            onResult(0, "") // Trả về false nếu có lỗi xảy ra
        }
}


@Preview(showBackground = true)
@Composable
fun LoginPreviewLightMode() {
    ApartmentManagerTheme {
        LoginPage(onLoginClick = { _, _ -> })
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoginPreviewDarkMode() {
    ApartmentManagerTheme {
        LoginPage(onLoginClick = { _, _ -> })
    }
}