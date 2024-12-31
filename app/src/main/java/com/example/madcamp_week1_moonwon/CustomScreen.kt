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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.compose.material3.ButtonDefaults
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CustomScreen(navController: NavController) {
    // 카드 위치 및 크기 정보
    val cardWidth = remember { mutableStateOf(150.dp) }
    val cardHeight = remember { mutableStateOf(200.dp) }
    val cardSpacing = 130.dp
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val centerX = screenWidth / 2
    val centerY = screenHeight / 2
    val initialY = centerY -  cardHeight.value / 2

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
    val isAnimationComplete = remember { mutableStateOf(false) } // 애니메이션 종료 여부
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.dark_bg),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth()
                .fillMaxHeight()
        )

        cardPositions.forEachIndexed { index, xPosition ->
            // 애니메이션
            val animatedOffsetX by animateDpAsState (
                targetValue = if (selectedCardIndex.value == index) centerX - 25.dp else xPosition,
                animationSpec = tween(durationMillis = 1000)
            )
            val animatedOffsetY by animateDpAsState (
                targetValue = if (selectedCardIndex.value == index || selectedCardIndex.value == null) initialY - 50.dp else -1000.dp,
                animationSpec = tween(durationMillis = 1000)
            )
            val rotationAngle by animateFloatAsState (
                targetValue = if (isCardFlipped.value && selectedCardIndex.value == index) 180f else 0f,
                animationSpec = tween(durationMillis = 2000)
            )
            val animatedWidth by animateDpAsState (
                targetValue = if (isAnimationComplete.value && selectedCardIndex.value == index) 200.dp else cardWidth.value,
                animationSpec = tween(durationMillis = 1500)
            )
            val animatedHeight by animateDpAsState(
                targetValue = if (isAnimationComplete.value && selectedCardIndex.value == index) 300.dp else cardHeight.value,
                animationSpec = tween(durationMillis = 1500)
            )
            LaunchedEffect(selectedCardIndex.value) {
                if(selectedCardIndex.value != null) {
                    delay(500)
                    isAnimationComplete.value = true
                }
            }

            Image(
                painter = painterResource(
                    id = if (isCardFlipped.value && selectedCardIndex.value == index && rotationAngle > 90f) {
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
                    .width(animatedWidth)
                    .height(animatedHeight)
                    .offset(x = animatedOffsetX - cardWidth.value / 2, y = animatedOffsetY)
                    .graphicsLayer (
                        rotationY = rotationAngle,
                        scaleX = -1f
                    )
                    .clickable {
                        selectedCardIndex.value = index
                    }
            )
        }
        val coroutineScope = rememberCoroutineScope()
        val flag1 = remember { mutableStateOf(false) } // "카드 확인하기" 버튼
        val flag2 = remember { mutableStateOf(false) } // "해석 보러가기" 버튼
        if(selectedCardIndex.value != null && !flag1.value) {
            Button(
                onClick = {
                    flag1.value = true
                    coroutineScope.launch {
                        delay(1000)
                        if (selectedCardIndex.value != null) {
                            isCardFlipped.value = true
                            coroutineScope.launch {
                                delay(2000)
                                flag2.value = true
                            }
                        }
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 70.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFC59ADE), // 보라색 버튼 배경
                    contentColor = Color.White // 버튼 텍스트 색
                )
            ) {
                Text(text = "카드 확인하기")
            }
        }
        if(flag2.value) {
            Button(
                onClick = {
                    selectedCardIndex.value?.let { index ->
                        val cardNumber = randomNumbers[index]
                        navController.navigate("explain_screen/$cardNumber")
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 70.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFC59ADE), // 보라색 버튼 배경
                    contentColor = Color.White // 버튼 텍스트 색
                )
            ) {
                Text(text = "해석 보러가기")
            }
        }

    }
}



