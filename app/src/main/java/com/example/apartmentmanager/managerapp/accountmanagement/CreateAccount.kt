package com.example.apartmentmanager.managerapp.accountmanagement

import android.content.res.Configuration
import android.util.Log
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.apartmentmanager.R
import com.example.apartmentmanager.addAccount
import com.example.apartmentmanager.templates.InfoPage
import com.example.apartmentmanager.ui.theme.ApartmentManagerTheme
import com.example.apartmentmanager.ui.theme.DarkGreen
import com.example.apartmentmanager.ui.theme.LightGreen
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAccount(
    changeSecondaryFun: (Int) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val datePickerState = rememberDatePickerState()
    val formatter = SimpleDateFormat("dd/MM/yyyy")

    var name by rememberSaveable { mutableStateOf("") }
    var DOB by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var hometown by rememberSaveable { mutableStateOf("") }
    var roomID by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    var newID by rememberSaveable { mutableStateOf("") }
    var newPW by rememberSaveable { mutableStateOf("") }
    var newUUID by rememberSaveable { mutableStateOf("") }

    var setCreateRequest by rememberSaveable { mutableStateOf(false) }
    var addSuccess by rememberSaveable { mutableStateOf(false) }
    var addFail by rememberSaveable { mutableStateOf(false) }
    var showDetails by rememberSaveable { mutableStateOf(false) }

    val enabled =
        name.isNotEmpty() && DOB.isNotEmpty() && phone.isNotEmpty() && hometown.isNotEmpty() && roomID.isNotEmpty()

    var showCalendar by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(setCreateRequest) {
        if (setCreateRequest) {
            val today = formatter.parse(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))!!
            val IDandPW = addAccount(name, formatter.parse(DOB)!!, phone, hometown, roomID, today, password)
            if (IDandPW.first == "-1") {
                Log.d("Firestore", "Error adding account")
                addFail = true
            } else {
                Log.d("Firestore", "Account added successfully")
                newID = IDandPW.first
                newPW = IDandPW.second
                newUUID = phone
                addSuccess = true
                showDetails = true
                name = ""
                DOB = ""
                phone = ""
                hometown = ""
                roomID = ""
                password = ""
            }
            setCreateRequest = false
        }
    }

    LaunchedEffect(addSuccess) {
        if (addSuccess) {
            delay(2000)
            addSuccess = false
        }
    }
    LaunchedEffect(addFail) {
        if (addFail) {
            delay(2000)
            addFail = false
        }
    }

    Box {
        InfoPage(
            title = "Create account",
            onBackClick = { changeSecondaryFun(0) }
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

                    DOBBar(DOB, calendarToggle = { showCalendar = true })

                    //calendar dialog
                    if (showCalendar) {
                        DatePickerDialog(
                            onDismissRequest = { showCalendar = false },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        showCalendar = false
                                        DOB =
                                            convertMillisToDate(datePickerState.selectedDateMillis!!)
                                    }) {
                                    Text("OK")
                                }
                            },
                            dismissButton = {
                                OutlinedButton(onClick = { showCalendar = false }) {
                                    Text("Cancel")
                                }
                            }
                        ) {
                            DatePicker(
                                state = datePickerState,
                            )
                        }
                    }

                    PhoneBar(phone, onValueChange = { phone = it })

                    HomeTownBar(hometown, onValueChange = { hometown = it })

                    RoomBar(onValueChange = { roomID = it })

                    PasswordBar(password, onValueChange = { password = it })

                    if (showDetails) {
                        Text(
                            text = "Your ID is $newID",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(screenWidth * 0.025f))
                        Text(
                            text = "Your username is $newUUID and your password is $newPW",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    Spacer(modifier = Modifier.height(screenWidth * 0.05f))
                    Button(
                        enabled = enabled,
                        onClick = {
                            setCreateRequest = true
                            showDetails = false
                        },
                        modifier = Modifier.align(Alignment.End).width(screenWidth * 0.4f)
                    ) {
                        if (setCreateRequest) {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        } else {
                            Text("Add Account")
                        }

                    }
                }
            }
        }

        AnimatedVisibility(
            visible = addSuccess,
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
                        contentDescription = "Calendar",
                        modifier = Modifier
                            .scale(1.25f)
                            .width(screenWidth * 0.08f),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(screenWidth * 0.05f))
                    Text(
                        text = "Account added successfully!",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

            }
        }

        AnimatedVisibility(
            visible = addFail,
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
                        contentDescription = "Calendar",
                        modifier = Modifier
                            .scale(1.25f)
                            .width(screenWidth * 0.08f),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(screenWidth * 0.05f))
                    Text(
                        text = "Error adding account!",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

            }
        }
    }
}

private fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

@Composable
private fun NameBar(
    name: String,
    onValueChange: (String) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    Text(
        text = "Tenant's name",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onBackground
    )
    Spacer(modifier = Modifier.height(screenWidth * 0.025f))
    TextField(
        value = name, onValueChange = onValueChange, singleLine = true, shape = ShapeDefaults.Large,
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
fun DOBBar(
    DOB: String,
    calendarToggle: () -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    Text(
        text = "Date of birth (DD/MM/YYYY)",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onBackground
    )
    Spacer(modifier = Modifier.height(screenWidth * 0.025f))

    Box {
        TextField(
            value = DOB,
            onValueChange = { },
            readOnly = true,
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
            onClick = calendarToggle,
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Calendar",
                modifier = Modifier.scale(1.25f)
            )
        }
    }

    Spacer(modifier = Modifier.height(screenWidth * 0.05f))
}

@Composable
private fun PhoneBar(
    phone: String,
    onValueChange: (String) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    Text(
        text = "Phone number",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onBackground
    )
    Spacer(modifier = Modifier.height(screenWidth * 0.025f))
    TextField(
        value = phone,
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
private fun HomeTownBar(
    hometown: String,
    onValueChange: (String) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    Text(
        text = "Hometown",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onBackground
    )
    Spacer(modifier = Modifier.height(screenWidth * 0.025f))
    TextField(
        value = hometown,
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
    )
    Spacer(modifier = Modifier.height(screenWidth * 0.05f))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RoomBar(
    onValueChange: (String) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    var expanded by rememberSaveable { mutableStateOf(false) }
    val options = listOf(
        "P101",
        "P102",
        "P103",
        "P104",
        "P105",
        "P201",
        "P202",
        "P203",
        "P204",
        "P205",
        "P301",
        "P302",
        "P303",
        "P304",
        "P305",
        "P401",
        "P402",
        "P403",
        "P404",
        "P405"
    )
    var selectedOption by rememberSaveable { mutableStateOf("") }

    Text(
        text = "Register room",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onBackground
    )
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        // TextField để hiển thị lựa chọn đã chọn
        TextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            shape = ShapeDefaults.Large,
            //label = { Text("Select an option") },
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
    Spacer(modifier = Modifier.height(screenWidth * 0.05f))
}

@Composable
private fun PasswordBar(
    password: String,
    onValueChange: (String) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    Text(
        text = "Password (leave blank for random)",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onBackground
    )
    Spacer(modifier = Modifier.height(screenWidth * 0.025f))
    TextField(
        value = password,
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
    )
    Spacer(modifier = Modifier.height(screenWidth * 0.05f))
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ApartmentManagerTheme {
        CreateAccount { }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview2() {
    ApartmentManagerTheme {
        CreateAccount { }
    }
}