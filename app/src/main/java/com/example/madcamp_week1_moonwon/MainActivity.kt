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
                BottomNavItem.Contact -> ContactScreen()
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

@Composable
fun ContactScreen() {
    Text(text = "Contact Screen", modifier = Modifier.fillMaxSize())
}

@Composable
fun GalleryScreen() {
    Text(text = "Gallery Screen", modifier = Modifier.fillMaxSize())
}

@Composable
fun CustomScreen() {
    Text(text = "Custom Screen", modifier = Modifier.fillMaxSize())
}


