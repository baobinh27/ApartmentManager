package com.example.apartmentmanager.managerapp.accountmanagement

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.CalendarLocale
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.apartmentmanager.templates.InfoPage
import com.example.apartmentmanager.ui.theme.ApartmentManagerTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAccount(
    modifier: Modifier = Modifier,
    changeSecondaryFun: (Int) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val datePickerState = rememberDatePickerState()

    var name by rememberSaveable { mutableStateOf("") }
    var DOB by rememberSaveable { mutableStateOf("") }

    var showCalendar by rememberSaveable { mutableStateOf(false) }

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
                if (showCalendar) {
                    DatePickerDialog(
                        onDismissRequest = { showCalendar = false },
                        confirmButton = {
                            Button(
                                onClick = {
                                    showCalendar = false
                                    DOB = convertMillisToDate(datePickerState.selectedDateMillis!!)
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


                Spacer(modifier = Modifier.height(screenWidth * 0.05f))
                Button(
                    onClick = {},
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Add Account")
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
    )
    Spacer(modifier = Modifier.height(screenWidth * 0.05f))
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//private fun DOBBar(
//    DOB: String,
//    onValueChange: (String) -> Unit
//) {
//    val context = LocalContext.current
//    val calendar = Calendar.getInstance()
//    val year = calendar.get(Calendar.YEAR)
//    val month = calendar.get(Calendar.MONTH)
//    val day = calendar.get(Calendar.DAY_OF_MONTH)
//    var selectedDate by rememberSaveable { mutableStateOf("$day/${month + 1}/$year") }
//
//    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
//    // NestedScrollConnection tùy chỉnh để ngăn cuộn bên ngoài
//    val noOpNestedScrollConnection = object : NestedScrollConnection {}
//
//    Text(
//        text = "Date of birth",
//        style = MaterialTheme.typography.titleMedium,
//        color = MaterialTheme.colorScheme.onBackground
//    )
//    Spacer(modifier = Modifier.height(screenWidth * 0.025f))
//    TextField(
//        value = DOB,
//        onValueChange = { },
//        singleLine = true,
//        readOnly = true,
//        shape = ShapeDefaults.Large,
//        modifier = Modifier.fillMaxWidth().clickable{},
//        colors = TextFieldDefaults.colors(
//            unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
//            focusedContainerColor = MaterialTheme.colorScheme.onTertiaryContainer,
//            unfocusedIndicatorColor = Color.Transparent,
//            focusedIndicatorColor = Color.Transparent
//        ),
//    )
//    Spacer(modifier = Modifier.height(screenWidth * 0.05f))
//
//    AndroidView(
//        factory = { context ->
//            android.widget.DatePicker(context).apply {
//                init(year, month, day) { _, selectedYear, selectedMonth, selectedDay ->
//                    selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
//                    onValueChange(selectedDate)
//                }
//            }
//        },
//        modifier = Modifier.wrapContentSize().nestedScroll(noOpNestedScrollConnection)
//    )
//}

@OptIn(ExperimentalMaterial3Api::class)
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

    Box() {
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

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ApartmentManagerTheme {
        CreateAccount() { }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview2() {
    ApartmentManagerTheme {
        CreateAccount() { }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun Preview3() {
    ApartmentManagerTheme {
        val screenWidth = LocalConfiguration.current.screenWidthDp.dp
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Red)//.padding(screenWidth * 0.05f)
            ) {
                DatePicker(
                    state = DatePickerState(locale = CalendarLocale.US)
                )
            }

        }
    }
}