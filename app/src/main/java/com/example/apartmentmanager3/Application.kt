package com.example.apartmentmanager3

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
import com.example.apartmentmanager3.ui.theme.ApartmentManager3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ApartmentManager3Theme {
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
    ApartmentManager3Theme {
        MainNavigation(modifier = Modifier.fillMaxWidth())
    }
}