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
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import kotlin.random.Random

val db = FirebaseFirestore.getInstance()
//lấy mã phòng từ mã cư dân
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
//lấy thông tin phòng từ mã phòng
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
//đếm số phòng
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
//lấy thông tin chung cư
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
//lấy danh sách quản lý
suspend fun getManagerInfo(): List<List<String>> {
    val managersRef = db.collection("Manager")
    val managerList = mutableListOf<List<String>>()

    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    try {
        val documents = managersRef.get().await()
        for (document in documents) {
            val id = document.id
            val name = document.getString("Name").orEmpty()
            val dateOfBirth = document.getTimestamp("dateOfBirth")?.toDate()?.toInstant()
                ?.atZone(ZoneId.systemDefault())?.toLocalDate()?.format(dateFormatter).orEmpty()
            val phone = document.getString("sdt").orEmpty()

            managerList.add(listOf(id, name, dateOfBirth, phone))
        }
        Log.d("Firestore", "Fetched all manager info successfully")
    } catch (exception: Exception) {
        Log.e("Firestore", "Error fetching manager documents", exception)
    }

    return managerList
}
//lấy danh sách tất cả phòng
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
//lấy thông tin cư dân từ mã cư dân
suspend fun getTenantInfo(tenantID: String): List<String> {
    val tenantsRef = db.collection("tenants").document(tenantID)

    var dateArrive = ""
    var dateLeave = ""
    var dateOfBirth = ""
    var phone = ""
    var name = ""
    var roomID = ""
    var hometown = ""

    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    try {
        val document = tenantsRef.get().await()
        if (document.exists()) {
            Log.d("Firestore", "Document data: ${document.data}")

            dateArrive = document.getTimestamp("dateArrive")?.toDate()?.toInstant()
                ?.atZone(ZoneId.systemDefault())?.toLocalDate()?.format(dateFormatter).orEmpty()
            dateLeave = document.getTimestamp("dateLeave")?.toDate()?.toInstant()
                ?.atZone(ZoneId.systemDefault())?.toLocalDate()?.format(dateFormatter).orEmpty()
            dateOfBirth = document.getTimestamp("dateOfBirth")?.toDate()?.toInstant()
                ?.atZone(ZoneId.systemDefault())?.toLocalDate()?.format(dateFormatter).orEmpty()

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
    if (dateLeave == "") {dateLeave = "N/A"}
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

suspend fun findUserByName(name: String): List<String>? {
    val db = FirebaseFirestore.getInstance()

    return try {
        val tenantsRef = db.collection("tenants")
        val documents = tenantsRef.whereEqualTo("name", name).get().await()
        if (documents.isEmpty) {
            // Nếu không tìm thấy người dùng với tên này
            null
        } else {
            val usersList = mutableListOf<String>()

            // Duyệt qua tất cả các document trong collection "tenants"
            for (userDoc in documents) {
                // Tạo đối tượng User từ dữ liệu trong document
                val gname=userDoc.getString("name") ?: ""
                if(gname==name)
                {
                    val id =userDoc.id
                    // Thêm người dùng vào danh sách
                    usersList.add(id)
                }

            }
            usersList
        }
    } catch (e: Exception) {
        // Xử lý lỗi nếu không lấy được dữ liệu
        println("Error getting user data: ${e.message}")
        null
    }
}

suspend fun getBillInfo(month: Int, year: Int): List<List<Int>> {
    val apartmentRef = db.collection("apartmentInfo").document("general")
    var waterunit = 0
    var electunit = 0

    val billInfoList: MutableList<MutableList<Int>> = mutableListOf()
    val roomIdList = getRoomList();
    var elecComsumption = 0;
    var internet = 0;
    var services = 0;
    var waterComsumption = 0;
    var roomcost = 0;
    var totalBill = 0;
    var waterBill = 0;
    var electBill = 0;
    try {
        val document = apartmentRef.get().await()
        if (document.exists()) {
            Log.d("Firestore", "Document data: ${document.data}")
            waterunit = document.getLong("waterUnit")?.toInt() ?: 0
            electunit = document.getLong("powerUnit")?.toInt() ?: 0

        } else {
            Log.d("Firestore", "No such document")
        }
    } catch (exception: Exception) {
        Log.d("Firestore", "get failed with ", exception)
    }

    try{
        for (roomId in roomIdList) {
            val path = "$year/T$month/${roomId}"
            val billInfo = db.collection("Bill").document(path).get().await()
            val roomInfo = db.collection("Room").document(roomId).get().await()
            if(billInfo.exists() && roomInfo.exists())
            {
                roomcost = roomInfo.getLong("roomCost")?.toInt() ?: 0
                elecComsumption = billInfo.getLong("ElecComsumption")?.toInt() ?: 0
                internet = billInfo.getLong("Internet")?.toInt() ?: 0
                services = billInfo.getLong("Service")?.toInt() ?: 0
                waterComsumption = billInfo.getLong("WaterComsumption")?.toInt() ?: 0

                waterBill = waterunit * waterComsumption
                electBill = electunit * elecComsumption
                totalBill = roomcost + waterBill + electBill + internet + services
                billInfoList.add(mutableListOf(roomcost, waterComsumption, waterBill, elecComsumption, electBill, services, totalBill))
            }
            else
            {
                Log.d("Firestore", "No such document")
            }
        }

    } catch (exception: Exception) {
        Log.d("Firestore", "get failed with ", exception) // Trả về danh sách rỗng nếu có lỗi
    }
    return billInfoList;
}

fun generateRandomPassword(length: Int): String {
    val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    return (1..length)
        .map { chars[Random.nextInt(chars.length)] }
        .joinToString("")
}

//suspend fun addAccount(
//    name: String,
//    DOB: Date,
//    phone: String,
//    hometown: String,
//    room: String,
//    dateAdded: Date,
//    password: String = ""
//): Pair<String, String> {
//    val tenantRef = db.collection("tenants")
//    var isValid = true
//
//    val tenantData = hashMapOf(
//        "dateArrive" to Timestamp(dateAdded),
//        "dateLeave" to null,
//        "dateOfBirth" to Timestamp(DOB),
//        "hometown" to hometown,
//        "name" to name,
//        "phone" to phone,
//        "roomID" to room
//    )
//    // Nếu mật khẩu rỗng thì tạo mật khẩu ngẫu nhiên gồm 10 ký tự
//    val passwordAdd = if (password == "") {generateRandomPassword(10)} else {password}
//    val authData = hashMapOf(
//        "isActive" to true,
//        "username" to phone,
//        "password" to passwordAdd
//    )
//    val countRef = db.collection("apartmentInfo").document("general")
//    var count = -1;
//    var returnID = ""
//    countRef.get().addOnSuccessListener { document ->
//        count = document.getLong("idCount")?.toInt() ?: 0
//
//
//        if (count == -1) {isValid = false}
//        var tenantID = (count + 1).toString()
//        for (i in 1..5 - (count + 1).toString().length) {
//            tenantID = "0$tenantID"
//        }
//        tenantID = "T$tenantID"
//        returnID = tenantID
//        tenantRef.document(tenantID)
//            .set(tenantData)
//            .addOnSuccessListener {
//                Log.d("Firebase","Document $tenantID đã được thêm thành công!")
//            }
//            .addOnFailureListener { e ->
//                Log.w("Firebase","Lỗi khi thêm document $tenantID: ${e.message}")
//                isValid = false
//            }
//        db.collection("authentication").document(tenantID)
//            .set(authData)
//            .addOnSuccessListener {
//                Log.d("Firebase","Document $tenantID đã được thêm thành công!")
//            }
//            .addOnFailureListener { e ->
//                Log.w("Firebase","Lỗi khi thêm document $tenantID: ${e.message}")
//                isValid = false
//            }
//        db.collection("Room").document(room).get().addOnSuccessListener {
//            val roomStatus = it.getLong("roomStatus")?.toInt() ?: 0
//
//            db.collection("Room").document(room)
//                .update("roomStatus", roomStatus + 1)
//
//        }.addOnFailureListener {
//            Log.d("Firestore", "Error getting documents: ", it)
//            isValid = false
//        }
//
//        countRef.update("idCount", count + 1)
//    }.addOnFailureListener {
//        Log.d("Firestore", "Error getting documents: ", it)
//    }
//    return if (isValid) {Pair(returnID, passwordAdd)} else {Pair("-1", passwordAdd)}
//}

suspend fun addAccount(
    name: String,
    DOB: Date,
    phone: String,
    hometown: String,
    room: String,
    dateAdded: Date,
    password: String = ""
): Pair<String, String> {
    val tenantRef = db.collection("tenants")
    val countRef = db.collection("apartmentInfo").document("general")

    return try {
        // Lấy count bằng await
        val document = countRef.get().await()
        val count = document.getLong("idCount")?.toInt() ?: -1
        if (count == -1) return Pair("-1", password)

        // Tạo tenantID
        var tenantID = (count + 1).toString().padStart(5, '0')
        tenantID = "T$tenantID"

        // Dữ liệu tenant và auth
        val passwordAdd = if (password.isEmpty()) generateRandomPassword(10) else password
        val tenantData = hashMapOf(
            "dateArrive" to Timestamp(dateAdded),
            "dateLeave" to null,
            "dateOfBirth" to Timestamp(DOB),
            "hometown" to hometown,
            "name" to name,
            "phone" to phone,
            "roomID" to room
        )
        val authData = hashMapOf(
            "isActive" to true,
            "username" to phone,
            "password" to passwordAdd
        )

        // Thêm dữ liệu vào Firestore
        tenantRef.document(tenantID).set(tenantData).await()
        db.collection("authentication").document(tenantID).set(authData).await()

        // Cập nhật roomStatus
        val roomDoc = db.collection("Room").document(room).get().await()
        val roomStatus = roomDoc.getLong("roomStatus")?.toInt() ?: 0
        db.collection("Room").document(room).update("roomStatus", roomStatus + 1).await()

        // Cập nhật idCount
        countRef.update("idCount", count + 1).await()

        Pair(tenantID, passwordAdd) // Trả về ID và password
    } catch (e: Exception) {
        Log.d("Firestore", "Error: ${e.message}")
        Pair("-1", password)
    }
}


suspend fun isValidUser(username: String, password: String): Boolean {
    val db = FirebaseFirestore.getInstance()
    val userRef = db.collection("authentication")
    return try {
        // Thực hiện truy vấn Firestore và chờ kết quả
        val result = userRef.get().await()
        // Kiểm tra tài liệu trong Firestore
        for (document in result) {
            if (document.getString("username") == username &&
                document.getString("password") == password &&
                document.getBoolean("isActive") == true
            ) {
                return true // Trả về true nếu tìm thấy tài khoản hợp lệ
            }
        }
        false // Trả về false nếu không tìm thấy tài khoản hợp lệ
    } catch (e: Exception) {
        // Xử lý lỗi nếu có
        e.printStackTrace()
        false
    }
}