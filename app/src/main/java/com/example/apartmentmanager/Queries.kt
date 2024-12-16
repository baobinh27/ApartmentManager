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

// Lấy tất cả báo cáo sự cố của một phòng:
suspend fun getReportsByRoom(roomID: String): List<Map<String, Any>> {
    val reportRef = db.collection("report").whereEqualTo("roomID", roomID)
    return try {
        val snapshot = reportRef.get().await()
        snapshot.documents.map { it.data ?: emptyMap() }
    } catch (e: Exception) {
        Log.d("Firestore", "Error fetching reports: ${e.message}")
        emptyList()
    }
}

// Kiểm tra hợp đồng của một cư dân:
suspend fun getContractByTenant(tenantID: String): Map<String, Any>? {
    val contractRef = db.collection("Contract").whereEqualTo("tenantID", tenantID)
    return try {
        val snapshot = contractRef.get().await()
        snapshot.documents.firstOrNull()?.data
    } catch (e: Exception) {
        Log.d("Firestore", "Error fetching contract: ${e.message}")
        null
    }
}

//Cập nhật trạng thái phòng khi hợp đồng hết hạn:
suspend fun updateRoomStatus(roomID: String, newStatus: Int) {
    val roomRef = db.collection("Room").document(roomID)
    try {
        roomRef.update("roomStatus", newStatus).await()
        Log.d("Firestore", "Room status updated successfully!")
    } catch (e: Exception) {
        Log.d("Firestore", "Error updating room status: ${e.message}")
    }
}

//trả về id của tất cả bản ghi trong report mà có trường reply trống
suspend fun getUnrepliedReportIDs(): List<String> {
    val reportRef = db.collection("report")
    return try {
        // Truy vấn các bản ghi có trường "reply" rỗng
        val snapshot = reportRef.whereEqualTo("reply", "").get().await()
        // Lấy danh sách ID của các tài liệu phù hợp
        snapshot.documents.map { it.id }
    } catch (e: Exception) {
        Log.d("Firestore", "Error fetching unreplied reports: ${e.message}")
        emptyList()
    }
}

//1 hàm đầu vào là id báo cáo, trả về ngày gửi báo cáo, phòng gửi, tiêu đề và nội dung báo cáo
data class ReportDetails(
    val reportDate: String,
    val roomID: String,
    val reportTitle: String,
    val reportContent: String
)

suspend fun getReportDetailsByID(reportID: String): ReportDetails? {
    val reportRef = db.collection("report").document(reportID)
    return try {
        // Lấy dữ liệu từ Firestore dựa trên reportID
        val document = reportRef.get().await()
        if (document.exists()) {
            // Lấy các trường thông tin từ tài liệu
            val reportDate = document.getDate("reportdate")?.toString() ?: "N/A"
            val roomID = document.getString("roomID").orEmpty()
            val reportTitle = document.getString("report").orEmpty()
            val reportContent = document.getString("reply").orEmpty()

            // Trả về dưới dạng ReportDetails
            ReportDetails(reportDate, roomID, reportTitle, reportContent)
        } else {
            Log.d("Firestore", "No report document with ID: $reportID")
            null
        }
    } catch (e: Exception) {
        Log.d("Firestore", "Error fetching report: ${e.message}")
        null
    }
}

//1 hàm nhận thông tin là id báo cáo, nội dung phản hồi báo cáo, thực hiện cập nhật trường reply của báo cáo có mã đó thành nội dung truyền vào
suspend fun updateReportReply(reportID: String, replyContent: String): Boolean {
    val reportRef = db.collection("report").document(reportID)
    return try {
        // Thực hiện cập nhật trường "reply"
        reportRef.update("reply", replyContent).await()
        Log.d("Firestore", "Report reply updated successfully!")
        true
    } catch (e: Exception) {
        Log.d("Firestore", "Error updating report reply: ${e.message}")
        false
    }
}
//nnn