package com.example.madcamp_week1_moonwon

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun BottomNavigationBar(selectedScreen: BottomNavItem, onScreenSelected: (BottomNavItem) -> Unit) {
    NavigationBar {
        NavigationBarItem(
            selected = selectedScreen == BottomNavItem.Contact,
            onClick = { onScreenSelected(BottomNavItem.Contact) },
            label = { Text("Contact") },
            icon = { Icon(Icons.Default.Home, contentDescription = "Contact") }
        )
        NavigationBarItem(
            selected = selectedScreen == BottomNavItem.Gallery,
            onClick = { onScreenSelected(BottomNavItem.Gallery) },
            label = { Text("Gallery") },
            icon = { Icon(Icons.Default.Favorite, contentDescription = "Gallery") }
        )
        NavigationBarItem(
            selected = selectedScreen == BottomNavItem.Custom,
            onClick = { onScreenSelected(BottomNavItem.Custom) },
            label = { Text("Custom") },
            icon = { Icon(Icons.Default.Settings, contentDescription = "Custom") }
        )
    }
}
