package com.example.apartmentmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.apartmentmanager.ui.theme.ApartmentManagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ApartmentManagerTheme {
                MainNavigation(modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
fun MainNavigation(
    modifier: Modifier = Modifier
) {
    var onLogin by rememberSaveable { mutableStateOf(true) }
    Surface (
        modifier = modifier
    ) {
        if (onLogin) {
            LoginPage(onLoginClick = { onLogin = !onLogin })
        } else {
            MainApp(onLogOut = {onLogin = !onLogin})
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    ApartmentManagerTheme {
        MainNavigation(modifier = Modifier.fillMaxWidth())
    }
}