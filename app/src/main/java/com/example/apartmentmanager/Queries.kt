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
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

val db = FirebaseFirestore.getInstance()

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
data class User(
    val name: String,
    val phone: String,
    val age: Int,
    val hometown: String,
    val rID: String,
    val dateArrive: Long,  // Lưu trữ thời gian dưới dạng timestamp
    val dateLeave: Long,   // Lưu trữ thời gian dưới dạng timestamp
    val DOB: Long          // Lưu trữ ngày sinh dưới dạng timestamp
)
suspend fun findUserByName(name: String): List<Any>? {
    val db = FirebaseFirestore.getInstance()

    return try {
        val tenantsRef = db.collection("tenants")
        val documents = tenantsRef.whereEqualTo("name", name).get().await()
        if (documents.isEmpty) {
            // Nếu không tìm thấy người dùng với tên này
            null
        } else {
            val usersList = mutableListOf<Any>()

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
suspend fun findUserByID(name: String): List<Any>? {
    val db = FirebaseFirestore.getInstance()

    return try {
        val tenantsRef = db.collection("tenants")
        val documents = tenantsRef.whereEqualTo("name", name).get().await()
        if (documents.isEmpty) {
            // Nếu không tìm thấy người dùng với tên này
            null
        } else {
            val usersList = mutableListOf<Any>()

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
suspend fun DelteAccount(tenantID: String)
{
    var roomID=""
    roomID = getRoomFromTenant(tenantID)
    val tenantsRef = db.collection("tenants").document(tenantID)
    val zoneId = ZoneId.of("UTC+8")
    val zonedDateTime = ZonedDateTime.now(zoneId)
    val formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy 'at' h:mm:ss a z")
    val formattedTime = zonedDateTime.format(formatter)
    // tien hanh cap nhat ngay roi di
    tenantsRef.update("dateLeave", formattedTime)
        .addOnSuccessListener {
            // Success when updating
            println("The 'dateLeave' field has been successfully updated.")
        }
        .addOnFailureListener { exception ->
            // Handle error when updating
            println("Error updating the 'dateLeave' field: ${exception.message}")
        }
    //cap nhat roomID thanh null
    tenantsRef.update("roomID", null)
        .addOnSuccessListener {
            // Success when updating
            println("The 'roomID' field has been successfully updated.")
        }
        .addOnFailureListener { exception ->
            // Handle error when updating
            println("Error updating the 'dateLeave' field: ${exception.message}")
        }
    // giam so nguoi o phong
    val roomRef=db.collection("Room").document(roomID)
    var rStatus=0
    try {
        val document = roomRef.get().await()
        if (document.exists()) {
            Log.d("Firestore", "Document data: ${document.data}")

            rStatus = document.getLong("roomStatus")?.toInt() ?: 0

        } else {
            Log.d("Firestore", "No such document")
        }
    } catch (exception: Exception) {
        Log.d("Firestore", "get failed with ", exception) }
    roomRef.update("roomStatus", rStatus)
        .addOnSuccessListener {
            // Success when updating
            println("The 'roomStatus' field has been successfully updated.")
        }
        .addOnFailureListener { exception ->
            // Handle error when updating
            println("Error updating the 'dateLeave' field: ${exception.message}")
        }
    //xoa tai khoan khong kich hoat duoc
    val authRef=db.collection("authentication").document(tenantID)
    authRef.update("isActive", false)
        .addOnSuccessListener {
            // Success when updating
            println("The 'isActive' field has been successfully updated.")
        }
        .addOnFailureListener { exception ->
            // Handle error when updating
            println("Error updating the 'dateLeave' field: ${exception.message}")
        }
}
suspend fun authenForNewPassword(username: String, password: String): Boolean {
    // Gọi hàm isValidUser và trả về kết quả
    return isValidUser(username, password)
}
fun changepassword(username: String,newpass: String)
{
    val authRef=db.collection("authentication").document(username)
    authRef.update("password", newpass)
        .addOnSuccessListener {
            // Success when updating
            println("The 'password' field has been successfully updated.")
        }
        .addOnFailureListener { exception ->
            // Handle error when updating
            println("Error updating the 'dateLeave' field: ${exception.message}")
        }

}



fun createBillRecord(
    roomID: String,
    tmonth: String,
    year: String,
    elecConsumption: Int,
    waterConsumption: Int
) {
    val month = "T$tmonth" // Tháng ở dạng Txx

    // Tham chiếu đến document năm trong collection Bills
    val yearRef = db.collection("Bills").document(year)

    // Kiểm tra sự tồn tại của document năm (year)
    yearRef.get()
        .addOnSuccessListener { yearDocument ->
            if (yearDocument.exists()) {
                // Nếu document năm đã tồn tại, tiếp tục kiểm tra subcollection tháng
                val monthRef = yearRef.collection(month)

                // Kiểm tra sự tồn tại của subcollection tháng
                monthRef.get()
                    .addOnSuccessListener { monthDocuments ->
                        if (monthDocuments.isEmpty()) {
                            // Nếu subcollection tháng chưa có dữ liệu, tạo mới subcollection tháng
                            Log.d("Firebase", "Month $month does not exist, creating new collection for month.")

                            // Tạo document cho phòng trong subcollection tháng
                            val roomRef = monthRef.document(roomID)
                            roomRef.get()
                                .addOnSuccessListener { roomDocument ->
                                    if (!roomDocument.exists()) {
                                        // Nếu document phòng (roomID) chưa có, tạo mới document phòng
                                        val billData = hashMapOf(
                                            "elecConsumption" to elecConsumption,
                                            "waterConsumption" to waterConsumption,
                                            "roomID" to roomID
                                        )
                                        roomRef.set(billData)
                                            .addOnSuccessListener {
                                                Log.d("Firebase", "Bill for room $roomID in $month/$year has been created successfully!")
                                            }
                                            .addOnFailureListener { e ->
                                                Log.w("Firebase", "Error creating bill for room $roomID in $month/$year: ${e.message}")
                                            }
                                    } else {
                                        Log.d("Firebase", "Bill already exists for room $roomID in $month/$year.")
                                    }
                                }
                                .addOnFailureListener { e ->
                                    Log.w("Firebase", "Error checking room $roomID in month $month/$year: ${e.message}")
                                }
                        } else {
                            // Subcollection tháng đã tồn tại, kiểm tra và thêm phòng
                            val roomRef = monthRef.document(roomID)
                            roomRef.get()
                                .addOnSuccessListener { roomDocument ->
                                    if (!roomDocument.exists()) {
                                        // Tạo hoá đơn mới cho phòng
                                        val billData = hashMapOf(
                                            "elecConsumption" to elecConsumption,
                                            "waterConsumption" to waterConsumption,
                                            "roomID" to roomID
                                        )
                                        roomRef.set(billData)
                                            .addOnSuccessListener {
                                                Log.d("Firebase", "Bill for room $roomID in $month/$year has been created successfully!")
                                            }
                                            .addOnFailureListener { e ->
                                                Log.w("Firebase", "Error creating bill for room $roomID in $month/$year: ${e.message}")
                                            }
                                    } else {
                                        Log.d("Firebase", "Bill already exists for room $roomID in $month/$year.")
                                    }
                                }
                                .addOnFailureListener { e ->
                                    Log.w("Firebase", "Error checking room $roomID in month $month/$year: ${e.message}")
                                }
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.w("Firebase", "Error checking month $month in year $year: ${e.message}")
                    }
            } else {
                // Nếu document năm chưa tồn tại, tạo document năm mới và tiếp tục xử lý tháng và phòng
                val yearData = hashMapOf("year" to year)
                yearRef.set(yearData)
                    .addOnSuccessListener {
                        Log.d("Firebase", "Year document $year created successfully.")

                        // Sau khi tạo document năm, kiểm tra tháng và phòng
                        val monthRef = yearRef.collection(month)
                        val roomRef = monthRef.document(roomID)
                        val billData = hashMapOf(
                            "elecConsumption" to elecConsumption,
                            "waterConsumption" to waterConsumption,
                            "roomID" to roomID
                        )

                        roomRef.set(billData)
                            .addOnSuccessListener {
                                Log.d("Firebase", "Bill for room $roomID in $month/$year has been created successfully!")
                            }
                            .addOnFailureListener { e ->
                                Log.w("Firebase", "Error creating bill for room $roomID in $month/$year: ${e.message}")
                            }
                    }
                    .addOnFailureListener { e ->
                        Log.w("Firebase", "Error creating year document $year: ${e.message}")
                    }
            }
        }
        .addOnFailureListener { e ->
            Log.w("Firebase", "Error checking year document $year: ${e.message}")
        }
}


