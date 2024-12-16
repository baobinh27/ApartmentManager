package com.example.apartmentmanager.managerapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.apartmentmanager.findUserByID
import com.example.apartmentmanager.findUserByName
import com.example.apartmentmanager.templates.InfoPage
import com.example.apartmentmanager.ui.theme.ApartmentManagerTheme
import kotlinx.coroutines.launch

@Composable
fun SearchTenantPage(
    modifier: Modifier = Modifier,
    onFunctionChange: (Int) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    var name by rememberSaveable { mutableStateOf("") }
    var tenantID by rememberSaveable { mutableStateOf(listOf<String>()) } // Sử dụng List<String> thay vì List<Any>
    var isSearching1=false //searching1 dung de hien thi id khi truyen vao ten hoac ten khi truyen vao id
    val coroutineScope = rememberCoroutineScope()

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
                        if(name.first().isDigit()&&name.isNotEmpty())
                        coroutineScope.launch {
                            val result = findUserByName(name) // Tìm người dùng theo tên
                            tenantID = (result?.map { it.toString() }
                                ?: emptyList())// Lấy danh sách rID, nếu không có kết quả thì trả về danh sách rỗng
                            isSearching1=true
                        }
                        else if(name.isNotEmpty())
                        {
                            coroutineScope.launch {
                                val result = findUserByID(name) // Tìm người dùng theo tên
                                tenantID = (result?.map { it.toString() }
                                    ?: emptyList())// Lấy danh sách rID, nếu không có kết quả thì trả về danh sách rỗng
                                isSearching1=true
                            }
                        }
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Search")
                }


                // Khoảng cách giữa nút và danh sách
                Spacer(modifier = Modifier.height(screenWidth * 0.05f))
                if (isSearching1)   {
                    if (tenantID.isNotEmpty()) {
                        LazyColumn(modifier = Modifier.fillMaxWidth()) {
                            items(tenantID) { tenantID ->
                                // Mỗi tenantID hiển thị dưới dạng một Text
                                Text(
                                    text = tenantID,
                                    modifier = Modifier.padding(8.dp) // Thêm padding cho mỗi item
                                )
                            }
                        }
                    } else {
                        Text("No tenants found") // Nếu không có tenantID nào
                    }
                }
                else {
                    Text("Searching...") // Hiển thị khi đang tìm kiếm
                }

            }
        }
    }
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
        visualTransformation = PasswordVisualTransformation(),
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
fun SearchTenantPagePreviewLightMode() {
    ApartmentManagerTheme {
        SearchTenantPage(modifier = Modifier, onFunctionChange = {})
    }
}