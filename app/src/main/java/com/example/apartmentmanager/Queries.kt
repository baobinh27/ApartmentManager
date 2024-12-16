package com.example.apartmentmanager

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.google.firebase.Timestamp
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.util.Date
import kotlin.random.Random

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
    var roomAmount = 0
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
        roomAmount = getRoomCount()
    } catch (exception: Exception) {
        Log.d("Firestore", "get failed with ", exception)
    }

    return listOf(apartmentName, address, area.toString(), roomAmount.toString(), owner, contact)
}

suspend fun getRoomList(): List<String> {
    val roomsRef = db.collection("Room")
    val roomList = mutableListOf<String>()

    try {
        val documents = roomsRef.get().await()
        for (document in documents) {
            roomList.add(document.id)
        }
        Log.d("Firestore", "Fetched all room names successfully")
    } catch (exception: Exception) {
        Log.e("Firestore", "Error fetching room documents", exception)
    }

    return roomList
}

suspend fun getTenantInfo(tenantID: String): List<String> {
    val tenantsRef = db.collection("tenants").document(tenantID)

    var dateArrive = ""
    var dateLeave = ""
    var dateOfBirth = ""
    var phone = ""
    var name = ""
    var roomID = ""
    var hometown = ""

    try {
        val document = tenantsRef.get().await()
        if (document.exists()) {
            Log.d("Firestore", "Document data: ${document.data}")

            dateArrive = document.getTimestamp("dateArrive")?.toDate()?.toString().orEmpty()
            dateLeave = document.getTimestamp("dateLeave")?.toDate()?.toString().orEmpty()
            dateOfBirth = document.getTimestamp("dateOfBirth")?.toDate()?.toString().orEmpty()

            phone = document.getString("phone").orEmpty()
            name = document.getString("name").orEmpty()
            roomID = document.getString("roomID").orEmpty()
            hometown = document.getString("hometown").orEmpty()
        } else {
            Log.d("Firestore", "No such document")
        }
    } catch (exception: Exception) {
        Log.e("Firestore", "Error fetching document", exception)
    }

    return listOf(name, dateOfBirth, phone, dateArrive, dateLeave, roomID, hometown)
}

suspend fun getTenantsList(roomID: String): List<Pair<String, String>> {
    val tenantsRef = db.collection("tenants")

    val tenantsList = mutableListOf<Pair<String, String>>()

    try {
        val querySnapshot = tenantsRef.whereEqualTo("roomID", roomID).get().await()
        for (document in querySnapshot.documents) {
            val tenantId = document.id // Lấy mã cư dân (ID của document)
            val tenantName = document.getString("name") ?: "Unknown"
            tenantsList.add(Pair(tenantId, tenantName))
        }
        Log.d("Firestore", "Fetched tenant IDs and names: ${tenantsList.size}")
    } catch (exception: Exception) {
        Log.e("Firestore", "Error fetching documents", exception)
    }

    return tenantsList
}

fun generateRandomPassword(length: Int): String {
    val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    return (1..length)
        .map { chars[Random.nextInt(chars.length)] }
        .joinToString("")
}

suspend fun addAccount(
    name: String,
    DOB: Date,
    phone: String,
    hometown: String,
    room: String,
    dateAdded: Date,
    password: String = ""
): String {
    val tenantRef = db.collection("tenants")
    var isValid = true

    val tenantData = hashMapOf(
        "dateArrive" to Timestamp(dateAdded),
        "dateLeave" to null,
        "dateOfBirth" to Timestamp(DOB),
        "hometown" to hometown,
        "name" to name,
        "phone" to phone,
        "roomID" to room
    )
    // Nếu mật khẩu rỗng thì tạo mật khẩu ngẫu nhiên gồm 10 ký tự
    val passwordAdd = if (password == "") {generateRandomPassword(10)} else {password}
    val authData = hashMapOf(
        "isActive" to true,
        "username" to phone,
        "password" to passwordAdd
    )
    val countRef = db.collection("apartmentInfo").document("general")
    var count = -1;
    countRef.get().addOnSuccessListener { document ->
        count = document.getLong("idCount")?.toInt() ?: 0
    }.addOnFailureListener {
        Log.d("Firestore", "Error getting documents: ", it)
    }

    if (count == -1) {isValid = false}
    var tenantID = (count + 1).toString()
    for (i in 1..5 - (count + 1).toString().length) {
        tenantID = "0$tenantID"
    }
    tenantID = "T$tenantID$"

    tenantRef.document(tenantID)
        .set(tenantData)
        .addOnSuccessListener {
            Log.d("Firebase","Document $tenantID đã được thêm thành công!")
        }
        .addOnFailureListener { e ->
            Log.w("Firebase","Lỗi khi thêm document $tenantID: ${e.message}")
            isValid = false
        }
    db.collection("authentication").document(tenantID)
        .set(authData)
        .addOnSuccessListener {
            Log.d("Firebase","Document $tenantID đã được thêm thành công!")
        }
        .addOnFailureListener { e ->
            Log.w("Firebase","Lỗi khi thêm document $tenantID: ${e.message}")
            isValid = false
        }
    var roomStatus = 0
    db.collection("Room").document(room).get().addOnSuccessListener {
        roomStatus = it.getLong("roomStatus")?.toInt() ?: 0
    }.addOnFailureListener {
        Log.d("Firestore", "Error getting documents: ", it)
        isValid = false
    }
    db.collection("Room").document(room)
        .update("roomStatus", roomStatus + 1)
    countRef.update("idCount", count + 1)
    return if (isValid) {passwordAdd} else {"-1"}
}