package com.example.apartmentmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.apartmentmanager.ui.theme.ApartmentManagerTheme

//App bắt đầu tại đây
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ApartmentManagerTheme {
                MainNavigation(modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

//Hàm này quyết định xem trang đăng nhập hay giao diện chính của app sẽ được sử dụng
@Composable
fun MainNavigation(
    modifier: Modifier = Modifier
) {
    //Biến này là một trạng thái (state) để cho app biết sẽ xuất hiện phần tử nào
    var onLogin by rememberSaveable { mutableStateOf(true) }
    Surface (
        modifier = modifier
    ) {
        //Mặc định xuất hiện trang đăng nhập
        if (onLogin) {
            //Truyền vào một hàm, hàm này sẽ làm thay đổi state bên trên, làm cho hàm này được chạy lại và xuất hiện menu chính
            //onLoginClick sẽ được đặt trong nút Sign in
            LoginPage(onLoginClick = { onLogin = !onLogin })
        } else {
            //Hàm onLogOut sẽ được đặt trong nút đăng xuất để quay lại màn hình đăng nhập
            MainApp(onLogOut = {onLogin = !onLogin})
        }
    }
}

//Hàm này chỉ dùng để xem trước giao diện mà không cần chạy lại app
@Preview(showBackground = true)
@Composable
fun MainPreview() {
    ApartmentManagerTheme {
        MainNavigation(modifier = Modifier.fillMaxWidth())
    }
}