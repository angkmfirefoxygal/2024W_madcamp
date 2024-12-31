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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
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


    //화면 최대로 채우도록
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    // 이미지 리소스를 가져와서 ImageBitmap으로 변환
    val backgroundBitmap: ImageBitmap = ImageBitmap.imageResource(id = R.drawable.dark_bg)

    Box(modifier = Modifier
        .size(screenWidth, screenHeight) // 화면 크기를 명시적으로 설정
        .fillMaxSize()) {

        // 배경 이미지
        Image(
            painter = painterResource(id = R.drawable.gradation_bg),
            contentDescription = "Background",
            contentScale = ContentScale.Crop, // 화면 전체를 채우도록 설정
            modifier = Modifier.matchParentSize() // Box 크기에 맞추기
        )

        val tarotImages = (0..21).map { id ->
            R.drawable::class.java.getField("tarot_$id").getInt(null)
        }

        LazyColumn(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxSize()
        ) {


            // 상단 이미지
            item {
                Image(
                    painter = painterResource(id = R.drawable.gallery),
                    contentDescription = "Gallery title",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .padding(top = 10.dp)
                )
            }

            // 4열 그리드 구성
            val chunkedImages = tarotImages.chunked(4) // 4개의 항목씩 묶음
            items(chunkedImages) { rowImages ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    rowImages.forEach { imageRes ->
                        Box(
                            modifier = Modifier
                                .weight(1f) // 각 카드가 동일한 너비를 가짐
                                //.aspectRatio(1f) // 정사각형 이미지
                                .clickable {
                                    navController.navigate("detail/$imageRes")
                                }
                                .padding(2.dp)
                        ) {
                            Image(
                                painter = painterResource(id = imageRes),
                                contentDescription = "Tarot Card",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }

                    // 부족한 열을 채우기 위해 빈 공간 추가
                    repeat(4 - rowImages.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }

            // 남은 빈 공간 채우기
            item {
                Spacer(modifier = Modifier.height(56.dp)) // 마지막 공간 조정
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun PreviewGalleryGrid(){
    // 테스트용 NavController
    val navController = rememberNavController()

    GalleryGrid(navController = navController)
}

