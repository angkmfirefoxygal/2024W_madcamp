package com.example.madcamp_week1_moonwon

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController


@Composable

fun BottomNavigationBar(navController: NavController, bottomNavItems: List<BottomNavItem>) {
    val currentRoute = navController.currentBackStackEntry?.destination?.route ?: ""

    Surface(
        color = Color(0xFF390E5B), // 네비게이션 바 배경색
            modifier = Modifier
            .fillMaxWidth()
            .height(100.dp) // 높이 설정
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            bottomNavItems.forEach { item ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable {
                            if (currentRoute != item.route) {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = if (currentRoute == item.route) Color.White else Color.Gray, // 선택된 탭 색상
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = item.label,
                        color = if (currentRoute == item.route) Color.White else Color.Gray, // 선택된 탭 색상
                        fontSize = 10.sp // 텍스트 크기 설정
                    )
                }
            }
        }
    }
}

/*fun BottomNavigationBar(navController: NavController, bottomNavItems: List<BottomNavItem>) {

    val currentRoute = navController.currentBackStackEntry?.destination?.route ?: ""

    // 둥근 모서리를 포함한 BottomNavigationBar
   Surface(
        color = Color(0xFF390E5B), // 네비게이션 바 배경색
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp), // 둥근 모서리
        shadowElevation = 8.dp, // 그림자 효과
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp) // 높이 설정
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            bottomNavItems.forEach { item ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable {
                            if (currentRoute != item.route) {
                                navController.navigate(item.route) {
                                    launchSingleTop = true
                                }
                            }
                        }
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = if (currentRoute == item.route) Color.White else Color.Gray, // 선택된 탭 색상
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = item.label,
                        color = if (currentRoute == item.route) Color.White else Color.Gray, // 선택된 탭 색상
                        fontSize = 10.sp // 텍스트 크기 설정
                    )
                }
            }
        }
    }

}*/

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