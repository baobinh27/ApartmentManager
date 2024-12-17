package com.example.apartmentmanager.managerapp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apartmentmanager.ManagerApp
import com.example.apartmentmanager.R
import com.example.apartmentmanager.templates.InfoCardBar
import com.example.apartmentmanager.templates.InfoPage
import com.example.apartmentmanager.ui.theme.ApartmentManagerTheme
import com.example.apartmentmanager.ui.theme.DarkGreen
import com.example.apartmentmanager.ui.theme.LightGreen
import kotlinx.coroutines.delay
import java.text.NumberFormat
import java.util.Locale

@Composable
fun UpdateRentPage(
    onFunctionChange: (Int) -> Unit
) {
    val roomList = listOf("P101", "P102", "P103", "P104", "P105", "P201", "P202", "P203", "P204", "P205", "P301", "P302", "P303", "P304", "P305", "P401", "P402", "P403", "P404", "P405")
    var month by rememberSaveable { mutableStateOf("") }
    var rentNav by rememberSaveable { mutableStateOf(0) }

    AnimatedVisibility(
        visible = rentNav == 0,
        enter = slideInHorizontally(initialOffsetX = { -it }),
        exit = slideOutHorizontally(targetOffsetX = { -it })
    ) {
        InfoPage(
            title = "Update Rent",
            onBackClick = { onFunctionChange(0) }
        ) {
            //PowerAndWater(power = 3500, water = 40000)
            ChooseMonth(onValueChange = { month = it })

            Spacer(modifier = Modifier.height(LocalConfiguration.current.screenWidthDp.dp * 0.05f))
            for (room in roomList) {
                InfoCardBar(
                    painter1 = painterResource(R.drawable.coin),
                    size1 = 0.07f,
                    title = room,
                    onClick = {rentNav = 1}, //TODO
                    icon2 = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    size2 = 0.07f
                )
            }
        }
    }

    AnimatedVisibility(
        visible = rentNav == 1,
        enter = slideInHorizontally(initialOffsetX = { it }),
        exit = slideOutHorizontally(targetOffsetX = { it })
    ) {
        InputPage(onBackClick = {rentNav = 0})
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChooseMonth(
    onValueChange: (String) -> Unit = {}
) {

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    var options = listOf("8/2024", "9/2024", "10/2024", "11/2024", "12/2024")
    options = options.reversed()
    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedOption by rememberSaveable { mutableStateOf(options[0]) }


    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier
            .fillMaxWidth()
            .padding(screenWidth * 0.05f)
    ) {
        // TextField để hiển thị lựa chọn đã chọn
        TextField(
            value = "Period: $selectedOption",
            onValueChange = {},
            readOnly = true,
            shape = ShapeDefaults.Large,
            textStyle = MaterialTheme.typography.titleLarge,
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.calendar),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.width(screenWidth * 0.08f)
                )
            },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                focusedContainerColor = MaterialTheme.colorScheme.onTertiaryContainer,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
        )
        // DropdownMenu chứa danh sách các tùy chọn
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        selectedOption = option
                        onValueChange(option)
                        expanded = false
                    }
                )
            }
        }
    }
    //Spacer(modifier = Modifier.height(screenWidth * 0.05f))
}

@Composable
private fun InputPage(
    onBackClick: () -> Unit,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    var water by rememberSaveable { mutableStateOf("") }
    var electric by rememberSaveable { mutableStateOf("") }
    var showSuccessDialog by rememberSaveable { mutableStateOf(false) }
    var pending by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(showSuccessDialog) {
        if (showSuccessDialog) {
            delay(2000)
            showSuccessDialog = false
        }
    }

    LaunchedEffect(pending) {
        if (pending) {
            delay(752)
            pending = false
            showSuccessDialog = true
        }
    }

    Box{
        InfoPage(
            title = "Update Rent",
            onBackClick = onBackClick
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
                    RentBar("3500000")
                    WaterBar(water, onValueChange = { water = it })
                    ElectricBar(electric, onValueChange = { electric = it })
                    ServiceBar("200000")
                    Button(
                        enabled = water.isNotEmpty() && electric.isNotEmpty(),
                        onClick = {
                            pending = true },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        if (pending) {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        } else {
                            Text("Update")
                        }

                    }
                }
            }

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
                        text = "Bill updated!",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

            }
        }
    }
}

@Composable
private fun RentBar(
    value: String,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    Text(
        text = "Rent (fixed here)",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onBackground
    )
    Spacer(modifier = Modifier.height(screenWidth * 0.025f))
    TextField(
        value = value,
        onValueChange = {},
        readOnly = true,
        singleLine = true, shape = ShapeDefaults.Large,
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

@Composable
private fun WaterBar(
    value: String,
    onValueChange: (String) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    Text(
        text = "Units of water consumed",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onBackground
    )
    Spacer(modifier = Modifier.height(screenWidth * 0.025f))
    TextField(
        value = value,
        onValueChange = {
            if (it.length <= 10 && it.all { char -> char.isDigit() }) {
                onValueChange(it)
            }
        },
        singleLine = true, shape = ShapeDefaults.Large,
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            focusedContainerColor = MaterialTheme.colorScheme.onTertiaryContainer,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    )
    Spacer(modifier = Modifier.height(screenWidth * 0.05f))
}

@Composable
private fun ElectricBar(
    value: String,
    onValueChange: (String) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    Text(
        text = "Units of electricity consumed",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onBackground
    )
    Spacer(modifier = Modifier.height(screenWidth * 0.025f))
    TextField(
        value = value,
        onValueChange = {
            if (it.length <= 10 && it.all { char -> char.isDigit() }) {
                onValueChange(it)
            }
        },
        singleLine = true, shape = ShapeDefaults.Large,
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            focusedContainerColor = MaterialTheme.colorScheme.onTertiaryContainer,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    )
    Spacer(modifier = Modifier.height(screenWidth * 0.05f))
}

@Composable
private fun ServiceBar(
    value: String,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    Text(
        text = "Services (Internet, etc.)",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onBackground
    )
    Spacer(modifier = Modifier.height(screenWidth * 0.025f))
    TextField(
        value = value,
        onValueChange = {},
        readOnly = true,
        singleLine = true, shape = ShapeDefaults.Large,
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
fun ManagerAppPreviewLightMode() {
    ApartmentManagerTheme {
        UpdateRentPage(onFunctionChange = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ApartmentManagerTheme {
        InputPage(onBackClick = {})
    }
}
