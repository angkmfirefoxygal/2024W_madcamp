package com.example.madcamp_week1_moonwon

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import java.io.File
import java.io.FileOutputStream

@Composable
fun CustomScreen() {
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

    // 이미지
fun CustomScreen(navController: NavController) {

    //Button 관련 코드 - 작동 위해서 컴포저블 함수 외부로 이동함(딱히 중요 x)
    val context = LocalContext.current
    val activity = context as? android.app.Activity
    val capturedUri = remember { mutableStateOf<Uri?>(null) } // MutableState로 상태 관리
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.gradation_bg),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // <친구에게 공유하기> 버튼
        Button(
            onClick = {
                val rootView = activity?.window?.decorView?.findViewById<android.view.View>(android.R.id.content)

                rootView?.let { view ->
                    // 화면 캡처를 수행
                    val bitmap = captureViewAsBitmap(view)
                    val uri = bitmap?.let { saveBitmapToCache(context, it) } // 비트맵을 캐시에 저장 후 URI 생성
                    capturedUri.value = uri

                    // 캡처된 URI와 함께 다음 화면으로 이동
                    uri?.let {
                        navController.navigate("friends_profile/${Uri.encode(it.toString())}")
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, bottom = 30.dp, end = 16.dp) // 여백 추가
                .align(Alignment.BottomCenter), // 화면 하단 중앙 정렬
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFC59ADE), // 보라색 버튼 배경
                contentColor = Color.White // 버튼 텍스트 색
            )
        ) {
            Text("친구에게 공유하기!")
        }
    }
}


fun captureViewAsBitmap(view: android.view.View): Bitmap? {
    return try {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        bitmap
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun saveBitmapToCache(context: Context, bitmap: Bitmap): Uri? {
    return try {
        val file = File(context.cacheDir, "captured_image.png")
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}


@Preview(showBackground = true)
@Composable
fun CustomScreenPreview() {
    val fakeNavController = rememberNavController() // 더미 NavController
    CustomScreen(navController = fakeNavController)
}

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
                    .padding(bottom = 75.dp)
            ) {
                Text(text = "카드 확인하기")
            }

            Button(
                onClick = {
                    if(selectedCardIndex.value != null) {
                        isCardFlipped.value = !isCardFlipped.value
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 25.dp)
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
