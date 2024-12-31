package com.example.madcamp_week1_moonwon

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDirection.Companion.Content
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.madcamp_week1_moonwon.ui.theme.Madcampweek1moonwonTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Madcampweek1moonwonTheme {
                MainScreen()
            }
        }
    }
}

fun getSelectedNavItem(navController: NavController, bottomNavItems: List<BottomNavItem>): BottomNavItem {
    val currentRoute = navController.currentBackStackEntry?.destination?.route
    return bottomNavItems.find { it.route == currentRoute } ?: BottomNavItem.Contact
}


@Composable
fun MainScreen() {
    val bottomNavItems = listOf(BottomNavItem.Contact, BottomNavItem.Gallery, BottomNavItem.Custom)
    val navController = rememberNavController() // Tab3 이동 위해서 NavController 생성

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController, bottomNavItems = bottomNavItems)
        }
    ) { innerPadding ->
        // NavHost 추가
        NavHost(
            navController = navController,
            startDestination = "contact", // 시작 화면 설정
            modifier = Modifier.padding(innerPadding) // Padding 추가
        ) {

            // Contact 화면에 대한 route
            composable("contact") {
                val context = LocalContext.current
                ContactScreenWithViewModel(context = context, navController = navController)
            }

            // InfoScreen에 대한 route
            composable(
                route = "info/{name}/{phone}/{imageUri}",
                arguments = listOf(
                    navArgument("name") { type = NavType.StringType },
                    navArgument("phone") { type = NavType.StringType },
                    navArgument("imageUri") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val name = backStackEntry.arguments?.getString("name") ?: "Unknown"
                val phone = backStackEntry.arguments?.getString("phone") ?: "No Number"
                val imageUri = backStackEntry.arguments?.getString("imageUri") ?: ""

                InfoScreen(name = name, phone = phone, imageUri = imageUri, navController = navController)
            }

            // 하단 바 Contact 화면 이동
            composable(BottomNavItem.Contact.route) {
                val context = LocalContext.current
                ContactScreenWithViewModel(context = context, navController = navController)
            }

            // 하단 바 Gallery Button 이동
            composable(BottomNavItem.Gallery.route) {
                GalleryScreen()
            }

            // 하단 바 Custom 화면(오늘의 운세) 이동
            composable(BottomNavItem.Custom.route) {
                CustomScreen(navController)
            }

            composable("custom_screen") {
                CustomScreen(navController)
            }

            composable(
                route = "explain_screen/{cardNumber}",
                arguments = listOf(navArgument("cardNumber") { type = NavType.StringType })
            ) { backStackEntry ->
                val cardNumber = backStackEntry.arguments?.getString("cardNumber")
                ExplainScreen(navController = navController, cardNumber = cardNumber)
            }

            // 공유하기 버튼 클릭하고 친구 프로필 고르는 순간 메시지 창 열리도록
            composable("friends_profile/{imageUri}") { backStackEntry ->
                val imageUri = backStackEntry.arguments?.getString("imageUri")
                FriendsProfileChooseScreen(
                    navController = navController,
                    context = LocalContext.current,
                    imageUri = imageUri
                )
            }
        }
    }





}







@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    // 테스트용 MainScreen 호출
    MainScreen()
}



