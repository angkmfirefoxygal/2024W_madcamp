package com.example.madcamp_week1_moonwon



import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource


sealed class BottomNavItem(val route: String, val label: String, val iconResId: Int) {
    object Contact : BottomNavItem("contact", "Contact",  R.drawable.contact_page)
    object Gallery : BottomNavItem("gallery", "Gallery", R.drawable.cards_star)
    object Custom : BottomNavItem("custom", "DailyTarot",R.drawable.daily_spark)


    @Composable
    fun getIcon(): Painter {
        return painterResource(id = iconResId)
    }

}
/*

sealed class BottomNavItem(val route: String, val label: String, val icon: ImageVector) {
    object Contact : BottomNavItem("contact", "Contact", Icons.Filled.AccountBox)
    object Gallery : BottomNavItem("gallery", "Gallery", Icons.Filled.Favorite)
    object Custom : BottomNavItem("custom", "Custom", Icons.Filled.Menu)
}

*/
