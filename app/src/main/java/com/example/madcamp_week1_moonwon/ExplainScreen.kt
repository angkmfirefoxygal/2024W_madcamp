package com.example.madcamp_week1_moonwon

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.sp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import java.io.File
import java.io.FileOutputStream

@Composable
fun ExplainScreen(navController : NavController, cardNumber: String?) {
    //Button 관련 코드 - 작동 위해서 컴포저블 함수 외부로 이동함(딱히 중요 x)
    val context = LocalContext.current
    val activity = context as? android.app.Activity
    val capturedUri = remember { mutableStateOf<Uri?>(null) } // MutableState로 상태 관리

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.gradation_bg),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            // 카드 번호와 해석 텍스트 표시
            Text(
                text = "카드 번호: $cardNumber",
                style = androidx.compose.ui.text.TextStyle(fontSize = 24.sp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "이 카드는 당신의 운명을 말합니다!",
                style = androidx.compose.ui.text.TextStyle(fontSize = 18.sp)
            )
            Spacer(modifier = Modifier.height(32.dp))

        }
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
