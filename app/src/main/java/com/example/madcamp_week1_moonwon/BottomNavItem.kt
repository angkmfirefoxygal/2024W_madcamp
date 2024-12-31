package com.example.madcamp_week1_moonwon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector



sealed class BottomNavItem(val route: String, val label: String, val icon: ImageVector) {
    object Contact : BottomNavItem("contact", "Contact", Icons.Filled.AccountBox)
    object Gallery : BottomNavItem("gallery", "Gallery", Icons.Filled.Favorite)
    object Custom : BottomNavItem("custom", "Custom", Icons.Filled.Menu)
}

