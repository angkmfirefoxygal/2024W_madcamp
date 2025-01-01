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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.draw.alpha


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
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.gradation_bg),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth()
                .fillMaxHeight()
        )

        Image(
            painter = painterResource(id = R.drawable.daily_taro),
            contentDescription = "Friends Contact Header",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 80.dp)
                .height(65.dp),

            )

        val flag1 = remember { mutableStateOf(false) }
        val flag2 = remember { mutableStateOf(false) }
        val flag3 = remember { mutableStateOf(false) }
        var alphaValue1 by remember { mutableStateOf(0f) }
        var alphaValue2 by remember { mutableStateOf(0f) }
        var alphaValue3 by remember { mutableStateOf(0f) }
        LaunchedEffect(Unit) { alphaValue1 = 1f }
        LaunchedEffect(flag1.value) {
            if(flag1.value == true) {
                alphaValue2 = 1f
            }
        }
        LaunchedEffect(flag2.value) {
            if(flag2.value == true) {
                alphaValue3 = 1f
            }
        }

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

            LaunchedEffect(selectedCardIndex.value) {
                if(selectedCardIndex.value != null) {
                    delay(2000)
                    flag1.value = true
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
                    .offset(x = animatedOffsetX - cardWidth.value / 2, y = animatedOffsetY - 50.dp)
                    .graphicsLayer (
                        rotationY = rotationAngle,
                        scaleX = -1f
                    )
                    .clickable {
                        if (selectedCardIndex.value == null) {
                            selectedCardIndex.value = index
                            coroutineScope.launch {
                                delay(5000)
                                flag3.value = true
                            }
                        } else {
                            coroutineScope.launch {
                                delay(500)
                                if (selectedCardIndex.value != null) {
                                    isCardFlipped.value = true
                                    flag1.value = false
                                    coroutineScope.launch {
                                        delay(2000)
                                        flag2.value = true
                                    }
                                }
                            }
                        }
                    }
            )
        }
        val customFont = FontFamily(
            Font(R.font.font_three) // 추가한 폰트 이름과 동일
        )
        if(selectedCardIndex.value == null) {
            Text(
                text = "카드를 뽑아 오늘의 운세를 점쳐보세요!",
                modifier = Modifier
                    .align(Alignment.BottomCenter) // 화면 아래쪽 가운데 정렬
                    .padding(bottom = 85.dp)      // 아래쪽 여백 설정
                    .alpha(
                        animateFloatAsState(
                            targetValue = alphaValue1,
                            animationSpec = tween(durationMillis = 2500)
                        ).value
                    ),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontFamily = customFont,
                    color = Color(0xFF432109),
                    fontSize = 50.sp
                )
            )
        } else {
            if(!flag2.value && flag1.value) {
                Text(
                    text = "카드를 클릭해\n결과를 확인하세요!",
                    modifier = Modifier
                        .align(Alignment.BottomCenter) // 화면 아래쪽 가운데 정렬
                        .padding(bottom = 70.dp)      // 아래쪽 여백 설정
                        .alpha(
                            animateFloatAsState(
                                targetValue = alphaValue2,
                                animationSpec = tween(durationMillis = 2000)
                            ).value
                        ),
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontFamily = customFont,
                        color = Color(0xFF432109),
                        fontSize = 40.sp
                    )
                )
            } else if (flag2.value) {
                selectedCardIndex.value?.let { index ->
                    val num = randomNumbers[index]
                    Text(
                        text = when(num) {
                            0 -> "${num}. 광대\n(The Fool)"
                            1 -> "${num}. 마법사\n(The Magician)"
                            2 -> "${num}. 여사제\n(The High Priestess)"
                            3 -> "${num}. 여왕\n(The Empress)"
                            4 -> "${num}. 황제\n(The Emperor)"
                            5 -> "${num}. 교황\n(The Hierophant)"
                            6 -> "${num}. 연인\n(The Lovers)"
                            7 -> "${num}. 전차\n(The Chariot)"
                            8 -> "${num}. 힘\n(Strength)"
                            9 -> "${num}. 은둔자\n(The Hermit)"
                            10 -> "${num}. 운명의 수레바퀴\n(Wheel of Fortune)"
                            11 -> "${num}. 정의\n(Justice)"
                            12 -> "${num}. 매달린 사람\n(The Hanged Man)"
                            13 -> "${num}. 죽음\n(Death)"
                            14 -> "${num}. 절제\n(The Temperance)"
                            15 -> "${num}. 악마\n(The Devil)"
                            16 -> "${num}. 탑\n(The Tower)"
                            17 -> "${num}. 별\n(The Star)"
                            18 -> "${num}. 달\n(The Moon)"
                            19 -> "${num}. 태양\n(The Sun)"
                            20 -> "${num}. 심판\n(Judgement)"
                            else -> "${num}. 세계\n(The World)"
                        },
                        modifier = Modifier
                            .align(Alignment.BottomCenter) // 화면 아래쪽 가운데 정렬
                            .padding(bottom = 100.dp)      // 아래쪽 여백 설정
                            .alpha(
                                animateFloatAsState(
                                    targetValue = alphaValue3,
                                    animationSpec = tween(durationMillis = 2000)
                                ).value
                            ),
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontFamily = customFont,
                            color = Color(0xFF432109),
                            fontSize = 40.sp
                        )
                    )
                }
                Button(
                    onClick = {
                        selectedCardIndex.value?.let { index ->
                            val cardNumber = randomNumbers[index]
                            navController.navigate("explain_screen/$cardNumber")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, bottom = 20.dp, end = 16.dp) // 여백 추가
                        .align(Alignment.BottomCenter)
                        .alpha(
                            animateFloatAsState(
                                targetValue = alphaValue3,
                                animationSpec = tween(durationMillis = 2000)
                            ).value
                        ),
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
}



