package com.example.madcamp_week1_moonwon

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController

@Composable
fun CustomScreen(navController: NavController) {
    // 카드 위치 및 크기 정보
    val cardWidth = 100.dp
    val cardHeight = 150.dp
    val cardSpacing = 100.dp
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val centerX = screenWidth / 2
    val centerY = screenHeight / 2
    val initialY = 50.dp

    // 저장할 정보
    val cardPositions = remember {
        listOf(
            centerX - cardSpacing,
            centerX,
            centerX + cardSpacing
        )
    }
    val randomNumbers = remember { (0..21).shuffled().take(3) } // 랜덤 숫자
    val selectedCardIndex = remember { mutableStateOf<Int?>(null) } // 클릭한 카드 인덱스
    val isCardFlipped = remember { mutableStateOf(false) } // 카드 회전 여부
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.gradation_bg),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Image (
            painter = painterResource(id = R.drawable.select_card),
            contentDescription = "Select Card",
            modifier = Modifier
                .width(cardWidth - 10.dp)
                .height(cardHeight - 10.dp)
                .offset(x = centerX - cardWidth / 2 + 5.dp, y = centerY - cardHeight / 2 + 5.dp)
        )

        cardPositions.forEachIndexed { index, xPosition ->
            val animatedOffsetX by animateDpAsState (
                targetValue = if (selectedCardIndex.value == index) centerX else xPosition,
                animationSpec = tween(durationMillis = 500)
            )
            val animatedOffsetY by animateDpAsState (
                targetValue = if (selectedCardIndex.value == index) centerY - cardHeight / 2 else initialY,
                animationSpec = tween(durationMillis = 500)
            )
            val rotationAngle by animateFloatAsState (
                targetValue = if (isCardFlipped.value && selectedCardIndex.value == index) 180f else 0f,
                animationSpec = tween(durationMillis = 500)
            )
            Image(
                painter = painterResource(
                    id = if (isCardFlipped.value && selectedCardIndex.value == index) {
                        val cardNumber = randomNumbers[index]
                        val context = LocalContext.current
                        context.resources.getIdentifier(
                            "tarot_$cardNumber",
                            "drawable",
                            context.packageName
                        )
                    } else {
                        R.drawable.tarot_back
                    }
                ),
                contentDescription = "Card $index",
                modifier = Modifier
                    .width(cardWidth)
                    .height(cardHeight)
                    .offset(x = animatedOffsetX - cardWidth / 2, y = animatedOffsetY)
                    .graphicsLayer (
                        rotationY = rotationAngle,
                        scaleX = if(rotationAngle > 90f) -1f else 1f
                    )
                    .clickable {
                        selectedCardIndex.value = index
                    }
            )
        }
        if(selectedCardIndex.value !=null) {
            Button(
                onClick = {
                    if(selectedCardIndex.value != null) {
                        isCardFlipped.value = !isCardFlipped.value
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 95.dp)
            ) {
                Text(text = "카드 확인하기")
            }

            Button(
                onClick = {
                    selectedCardIndex.value?.let { index ->
                        val cardNumber = randomNumbers[index]
                        navController.navigate("explain_screen/$cardNumber")
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 45.dp)
            ) {
                Text(text = "해석 보기")
            }
        }

        Text(
            text = "Selected Card: ${
                selectedCardIndex.value?.let { randomNumbers[it] } ?: "None"
            }",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 0.dp)
        )


    }
}



