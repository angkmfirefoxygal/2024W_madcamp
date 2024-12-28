package com.example.madcamp_week1_moonwon

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController


@Composable
fun BottomNavigationBar(navController: NavController, bottomNavItems: List<BottomNavItem>) {
    // 현재 선택된 경로 확인
//  val currentRoute = navController.currentBackStackEntry?.destination?.route
    val currentRoute = navController.currentBackStackEntry?.destination?.route ?: ""

    NavigationBar {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    // 중복 클릭 방지 (이미 선택된 화면으로 이동하지 않음)
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            if (item.route == BottomNavItem.Contact.route) {
                                // Contact로 이동 시 초기화
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = false // StartDestination는 유지
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                label = { Text(item.label) },
                icon = { Icon(item.icon, contentDescription = item.label) }
            )
        }
    }
}
