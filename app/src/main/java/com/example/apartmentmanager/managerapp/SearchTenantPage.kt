package com.example.apartmentmanager.managerapp

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.apartmentmanager.R
import com.example.apartmentmanager.findUserByName
import com.example.apartmentmanager.getTenantInfo
import com.example.apartmentmanager.templates.InfoPage
import com.example.apartmentmanager.templates.ItemList
import com.example.apartmentmanager.ui.theme.ApartmentManagerTheme

@Composable
fun SearchTenantPage(
    modifier: Modifier = Modifier,
    onFunctionChange: (Int) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    var name by rememberSaveable { mutableStateOf("") }
    var list = remember { mutableStateMapOf<String, String>() }
    var searched by rememberSaveable { mutableStateOf(false) }

    var isID by rememberSaveable { mutableStateOf(false) }
    var isName by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(isID, isName) {
        if (isID) {
            list.clear()
            list[name] = getTenantInfo(name)[0]
            if (list[name] == "") list.clear()
        }
        Log.d("Firebase", "list found: $list")
        isID = false
    }
    LaunchedEffect(isName) {
        if (isName) {
            list.clear()
            val result = findUserByName(name)
            if (result != null) {
                for (id in result) {
                    list[id] = getTenantInfo(id)[0]
                }
            } else {
                list.clear()
            }
        }
        Log.d("Firebase", "list found: $list")
        isName = false
    }

    InfoPage(
        title = "Search Tenant",
        onBackClick = { onFunctionChange(0) }
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
                NameBar(name, onValueChange = { name = it })
                Spacer(modifier = Modifier.height(screenWidth * 0.05f))
                Button(
                    onClick = {
                        searched = true
                        if (isTenantID(name)) {
                            isID = true
                            isName = false
                        } else {
                            isID = false
                            isName = true
                        }
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Search")
                }
                if (list.isEmpty() && searched) {
                    Spacer(modifier = Modifier.height(screenWidth * 0.05f))
                    ItemList() {
                        Column(
                            modifier = Modifier.padding(screenWidth * 0.025f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.warning),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.height(screenWidth * 0.2f)
                            )
                            Text("No tenant found")
                        }

                    }
                } else if (list.isNotEmpty() && searched) {
                    Spacer(modifier = Modifier.height(screenWidth * 0.05f))
                    for (id in list) {
                        ItemList() {
                            Row(
                                modifier = Modifier.padding(screenWidth * 0.025f)
                            ) {
                                Text(id.key)
                                Spacer(modifier = Modifier.weight(1f))
                                Text(id.value)
                            }
                        }
                    }
                }
            }
        }
    }
}

fun isTenantID(id: String): Boolean {
    if (id.length > 6) {return false}
    if (id.length < 6) {return false}
    if ((id[0] == 'T' || id[0] == 'M' || id[0] == 'O') && id[1].isDigit() && id[2].isDigit() && id[3].isDigit() && id[4].isDigit() && id[5].isDigit()) {return true}
    return false
}

@Composable
private fun NameBar(
    name: String,
    onValueChange: (String) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    Text(
        text = "Enter tenant's name or ID",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onBackground
    )
    Spacer(modifier = Modifier.height(screenWidth * 0.025f))
    TextField(
        value = name,
        onValueChange = onValueChange,
        singleLine = true,
        shape = ShapeDefaults.Large,
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            focusedContainerColor = MaterialTheme.colorScheme.onTertiaryContainer,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions.Default
    )
    Spacer(modifier = Modifier.height(screenWidth * 0.05f))
}

@Preview(showBackground = true)
@Composable
fun SearchTenantPagePreviewLightMode() {
    ApartmentManagerTheme {
        SearchTenantPage(modifier = Modifier, onFunctionChange = {})
    }
}