package com.example.madcamp_week1_moonwon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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


@Composable
fun MainScreen() {
    var selectedScreen by remember { mutableStateOf(BottomNavItem.Contact) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedScreen = selectedScreen,
                onScreenSelected = { selectedScreen = it }
            )
        }
    ) { innerPadding ->
        // innerPadding을 Modifier에 적용
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedScreen) {
                BottomNavItem.Contact -> {
                    // Context를 전달하여 ContactScreen 호출
                    val context = LocalContext.current
                    ContactScreenWithViewModel(context)
                }
                BottomNavItem.Gallery -> GalleryScreen()
                BottomNavItem.Custom -> CustomScreen()
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




