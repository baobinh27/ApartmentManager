package com.example.apartmentmanager

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

val db = FirebaseFirestore.getInstance()

suspend fun getRoomFromTenant(tenantID: String): String {
    val roomIDref = db.collection("tenants").document(tenantID)

    return try {
        val document = roomIDref.get().await()
        if (document.exists()) {
            Log.d("Firestore", "Document data: ${document.data}")
            document.getString("roomID").orEmpty()
        } else {
            Log.d("Firestore", "No such document")
            ""
        }
    } catch (exception: Exception) {
        Log.d("Firestore", "get failed with ", exception)
        ""
    }
}

suspend fun getRoomDetail(rID: String): List<Any> {
    val roomRef = db.collection("Room").document(rID)
    val apartmentRef = db.collection("apartmentInfo").document("general")

    var rCost = 0
    var rSize = 0
    var rStatus = 0
    var rEprice = 0
    var rWprice = 0

    try {
        val document = roomRef.get().await()
        if (document.exists()) {
            Log.d("Firestore", "Document data: ${document.data}")
            rCost = document.getLong("roomCost")?.toInt() ?: 0
            rSize = document.getLong("roomSize")?.toInt() ?: 0
            rStatus = document.getLong("roomStatus")?.toInt() ?: 0
        } else {
            Log.d("Firestore", "Document does not exist!")
        }
        val apartmentDocument = apartmentRef.get().await()
        if (apartmentDocument.exists()) {
            Log.d("Firestore", "Document data: ${apartmentDocument.data}")
            rEprice = apartmentDocument.getLong("powerUnit")?.toInt() ?: 0
            rWprice = apartmentDocument.getLong("waterUnit")?.toInt() ?: 0
        } else {
            Log.d("Firestore", "Document does not exist!")
        }
    } catch (exception: Exception) {
        Log.d("Firestore", "Error getting document: ${exception.message}")
    }
    return listOf(rCost, rSize, rStatus, rEprice, rWprice)
}


suspend fun getRoomCount(): Int {
    val collectionRef = db.collection("Room")

    return try {
        // Lấy tất cả tài liệu trong collection và trả về số tài liệu
        //val querySnapshot: QuerySnapshot = collectionRef.get().await()
        collectionRef.get().await().size()
    } catch (exception: Exception) {
        // Nếu có lỗi, trả về -1
        Log.d("Firestore", "Error getting document: ${exception.message}")
        -1
    }
}

suspend fun getApartmentInfo(): List<String> {
    val apartmentRef = db.collection("apartmentInfo").document("general")

    var apartmentName = ""
    var address = ""
    var area = 0
    var owner = ""
    var contact = ""

    try {
        val document = apartmentRef.get().await()
        if (document.exists()) {
            Log.d("Firestore", "Document data: ${document.data}")
            apartmentName = document.getString("name").orEmpty()
            address = document.getString("address").orEmpty()
            area = document.getLong("area")?.toInt() ?: 0
            owner = document.getString("owner").orEmpty()
            contact = document.getString("contact").orEmpty()
        } else {
            Log.d("Firestore", "No such document")
        }
    } catch (exception: Exception) {
        Log.d("Firestore", "get failed with ", exception)
    }
    return listOf(apartmentName, address, area.toString(), owner, contact)
}