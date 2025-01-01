package com.example.madcamp_week1_moonwon


import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController


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
                //icon = { Icon(item.icon, contentDescription = item.label)
                icon = {
                    Icon(
                        painter = item.getIcon(),
                        contentDescription = item.label,
                        modifier = Modifier
                            .size(25.dp) // 아이콘 크기 조정
                            .padding(top = 4.dp) // 아이콘의 상단에 패딩 추가

                    )
                }
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewBottomNavigationBar() {
    // 테스트용 NavController
    val navController = rememberNavController()

    // Sealed class 하위 클래스를 사용하여 리스트 정의
    val bottomNavItems = listOf(
        BottomNavItem.Contact,
        BottomNavItem.Gallery,
        BottomNavItem.Custom
    )

    BottomNavigationBar(navController = navController, bottomNavItems = bottomNavItems)
}