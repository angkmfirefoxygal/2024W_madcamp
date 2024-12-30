package com.example.madcamp_week1_moonwon

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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

    // 스케일 값 관리
    //var scale by remember { mutableStateOf(1f) }
    //val columnCount by remember { derivedStateOf { (5 - scale).coerceIn(2f, 4f).toInt() } } // 최소 2열, 최대 4열



    Box(modifier = Modifier.fillMaxSize()) {
        // 배경 이미지
        Image(
            painter = painterResource(id = R.drawable.gradation_bg),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )

        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(20.dp))

            // 상단 타이틀 이미지
            Image(
                painter = painterResource(id = R.drawable.gallery),
                contentDescription = "Gallery Title",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            )

            // LazyVerticalGrid로 갤러리 구성
            LazyVerticalGrid(
                columns = GridCells.Fixed(4), // 4열로 고정
                modifier = Modifier
                    .padding(horizontal = 3.dp) // 좌우 여백
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(4.5.dp), // 행 간격 줄이기
                horizontalArrangement = Arrangement.spacedBy(0.5.dp) // 열 간격 줄이기
            ) {
                items(tarotImages) { imageRes ->
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = "Tarot Card",
                        modifier = Modifier
                            .padding(0.5.dp)
                            .size(160.dp) // 이미지 크기 조정
                            .clickable {
                                navController.navigate("detail/$imageRes")
                            }
                    )
                }
            }
        }
    }

   /* Box(modifier = Modifier.fillMaxSize()) {
        // 배경 이미지
        Image(
            painter = painterResource(id = R.drawable.gradation_bg),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )

        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(20.dp))

            // 상단 타이틀 이미지
            Image(
                painter = painterResource(id = R.drawable.gallery),
                contentDescription = "Gallery Title",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )

            // LazyVerticalGrid로 갤러리 구성
            LazyVerticalGrid(
                columns = GridCells.Fixed(columnCount),
                state = gridState,
                modifier = gestureModifier // 제스처 감지 모디파이어 적용
                    .padding(8.dp)
                    .fillMaxSize()
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
    }*/
}
