package com.example.madcamp_week1_moonwon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomNavItem(
    val label: String,
    val icon: ImageVector
) {
    Contact("Contact", Icons.Filled.Home),    // 기본 아이콘: Home
    Gallery("Gallery", Icons.Filled.Favorite), // 기본 아이콘: Favorite
    Custom("Custom", Icons.Filled.Settings)   // 기본 아이콘: Settings
}


