package com.example.madcamp_week1_moonwon


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
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
    var selectedScreen by remember { mutableStateOf(BottomNavItem.Contact) }


    Scaffold(

        //BottomBar 경로 설정
        bottomBar = {
            BottomNavigationBar(navController = navController, bottomNavItems = bottomNavItems)
        }
    ) { innerPadding ->
        //NavHost 추가 
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Contact.route, // 첫 화면은 ContactScreen
            modifier = Modifier.padding(innerPadding)
        ) {

            //하단 바 Gallery Button 으로 이동
            composable(BottomNavItem.Gallery.route) {
                GalleryScreen()
            }

            //하단 바 Custom 화면(오늘의 운세)으로 이동
            composable(BottomNavItem.Custom.route) {
                CustomScreen(navController)
            }

            //개인 프로필 클릭 시 Custom 화면(오늘의 운세) 로 이동
            composable("contact") {
                val context = LocalContext.current
                ContactScreenWithViewModel(context = context, navController = navController )
            }
            composable("custom") {
                CustomScreen(navController) // CustomScreen에 연결된 경로
            }

            //친구 프로필 클릭 시 친구의 Information 으로 이동
            composable(
                route = "info/{name}/{phone}", // 경로에 동적 인자를 추가
                arguments = listOf(
                    navArgument("name") { type = NavType.StringType },
                    navArgument("phone") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val name = backStackEntry.arguments?.getString("name") ?: "Unknown"
                val phone = backStackEntry.arguments?.getString("phone") ?: "Unknown"
                InfoScreen(name = name, phone = phone)
            }

            //친구에게 공유하기 화면 이동
            composable("friends_profile") {
                FriendsProfileChooseScreen()
            }



        }

    }
}




@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    Madcampweek1moonwonTheme {
        MainScreen()
    }
}




