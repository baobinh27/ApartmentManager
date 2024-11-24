package com.example.apartmentmanager.tenantapp

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.apartmentmanager.templates.InfoPage
import com.example.apartmentmanager.ui.theme.ApartmentManagerTheme
import com.google.firebase.firestore.FirebaseFirestore

//Function 2: Thông tin phòng
@Composable
fun RoomInfoPage(
    modifier: Modifier,
    onFunctionChange: (Int) -> Unit
) {
    val db = FirebaseFirestore.getInstance()
    val apartmentRef = db.collection("Room").document("P101")

    var roomCost by remember { mutableStateOf("") }
    var roomSize by remember { mutableStateOf("") }
    var roomStatus by remember { mutableStateOf("") }
    var roomID by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        apartmentRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("Firestore", "Document data: ${document.data}")
                    roomCost = document.getString("roomCost").orEmpty()
                    roomSize = document.getString("roomSize").orEmpty()
                    roomStatus = document.getString("roomStatus").orEmpty()
                    roomID = document.id
                } else {
                    Log.d("Firestore", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Firestore", "get failed with ", exception)
            }
    }
    InfoPage(
        title = "Room Information",
        onBackClick = { onFunctionChange(0) },
        modifier = modifier
    ) {
        Text(text = "Room Number: $roomID")
        Text(text = "Floor:")
        Text(text = "Area: $roomSize")
        Text(text = "Rent: $roomCost")
        Text(text = "Deposit:")
        Text(text = "Status: $roomStatus")
        Text(text = "Description:")
        Text(text = "Note:")
        Text(text = "Last Updated:")
    }
}

@Preview(showBackground = true)
@Composable
fun RoomInfoPagePreviewLightMode() {
    ApartmentManagerTheme {
        RoomInfoPage(modifier = Modifier, onFunctionChange = {})
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun RoomInfoPagePreviewDarkMode() {
    ApartmentManagerTheme {
        RoomInfoPage(modifier = Modifier, onFunctionChange = {})
    }
}