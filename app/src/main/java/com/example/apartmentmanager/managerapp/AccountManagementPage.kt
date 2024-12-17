package com.example.apartmentmanager.managerapp

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.apartmentmanager.R
import com.example.apartmentmanager.templates.InfoCardBar
import com.example.apartmentmanager.templates.InfoPage
import com.example.apartmentmanager.ui.theme.ApartmentManagerTheme
import com.example.apartmentmanager.managerapp.accountmanagement.*
import com.example.apartmentmanager.tenantapp.secondarypage.ChangePassword

@Composable
fun AccountManagementPage(
    onFunctionChange: (Int) -> Unit
) {
    var secondaryFunction by rememberSaveable { mutableIntStateOf(0) }

    AnimatedVisibility(
        visible = secondaryFunction == 0,
        enter = slideInHorizontally(initialOffsetX = { -it }),
        exit = slideOutHorizontally(targetOffsetX = { -it }),
    ) {
        Navigation(changeSecondFun = {secondaryFunction = it}, onFunctionChange = onFunctionChange)
    }
    AnimatedVisibility(
        visible = secondaryFunction == 1,
        enter = slideInHorizontally(initialOffsetX = { it }),
        exit = slideOutHorizontally(targetOffsetX = { it })
    ) {
        CreateAccount(changeSecondaryFun = {secondaryFunction = it})
    }
    AnimatedVisibility(
        visible = secondaryFunction == 2,
        enter = slideInHorizontally(initialOffsetX = { it }),
        exit = slideOutHorizontally(targetOffsetX = { it })
    ) {
        ChangePassword(onFunctionChange = {secondaryFunction = it})
    }
}

@Composable
private fun Navigation(
    changeSecondFun: (Int) -> Unit,
    onFunctionChange: (Int) -> Unit
) {
    InfoPage(
        title = "Account Management",
        onBackClick = {onFunctionChange(0)}
    ) {
        InfoCardBar(
            icon1 = Icons.Default.AddCircle,
            size1 = 0.07f,
            tint1 = MaterialTheme.colorScheme.primary,
            title = "Create account",
            icon2 = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            size2 = 0.07f,
            tint2 = MaterialTheme.colorScheme.onSecondary,
            onClick = {changeSecondFun(1)}
        )

        InfoCardBar(
            painter1 = painterResource(R.drawable.key),
            size1 = 0.07f,
            title = "Change user password",
            icon2 = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            size2 = 0.07f,
            tint2 = MaterialTheme.colorScheme.onSecondary,
            onClick = {changeSecondFun(2)}
        )

        InfoCardBar(
            icon1 = Icons.Default.Delete,
            size1 = 0.07f,
            tint1 = MaterialTheme.colorScheme.onSecondary,
            title = "Delete account",
            icon2 = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            size2 = 0.07f,
            tint2 = MaterialTheme.colorScheme.onSecondary,
            onClick = {changeSecondFun(3)}
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ApartmentManagerTheme {
        AccountManagementPage {  }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview2() {
    ApartmentManagerTheme {
        AccountManagementPage {  }
    }
}