package com.example.madcamp_week1_moonwon.utils


import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import android.view.View
import androidx.compose.ui.platform.ComposeView

@Composable
fun CaptureComposableContent(content: @Composable () -> Unit, onCapture: (Bitmap) -> Unit) {
    AndroidView(
        factory = { context ->
            ComposeView(context).apply {
                setContent {
                    content() // Composable 내용 렌더링
                }
            }
        },
        update = { view ->
            val bitmap = captureBitmap(view) // View 캡처
            onCapture(bitmap) // Bitmap 전달
        }
    )
}


object CaptureUtils {
    fun captureViewToBitmap(view: View): Bitmap {
        // View의 크기를 기반으로 Bitmap 생성
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }
}
