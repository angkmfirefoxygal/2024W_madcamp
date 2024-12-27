package com.example.madcamp_week1_moonwon

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun GalleryScreen() {
    // GalleryScreen에서 사용할 NavController 생성
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "galleryGrid"
    ) {
        // 기본 갤러리 화면
        composable("galleryGrid") {
            GalleryGrid(navController)
        }
        // 상세 화면
        composable("detail/{imageRes}") { backStackEntry ->
            val imageRes = backStackEntry.arguments?.getString("imageRes")?.toInt()
            imageRes?.let {
                DetailScreen(navController = navController, imageRes = it)
            }
        }
    }
}

@Composable
fun GalleryGrid(navController: NavHostController) {
    val tarotImages = (0..21).map { id ->
        R.drawable::class.java.getField("tarot_$id").getInt(null)
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(tarotImages) { imageRes ->
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Tarot Card",
                modifier = Modifier
                    .padding(4.dp)
                    .size(240.dp)
                    .clickable {
                        navController.navigate("detail/$imageRes")
                    }
            )
        }
    }
}
