package com.example.madcamp_week1_moonwon

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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

            composable(BottomNavItem.Gallery.route) {
                GalleryScreen()
            }
            composable(BottomNavItem.Custom.route) {
                CustomScreen()
            }
            composable("contact") {
                val context = LocalContext.current
                ContactScreenWithViewModel(context = context, navController = navController)
            }
            composable("custom") {
                CustomScreen() // CustomScreen에 연결된 경로
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




