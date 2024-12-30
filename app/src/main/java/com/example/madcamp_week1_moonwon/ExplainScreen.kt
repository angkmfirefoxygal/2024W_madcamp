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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import java.io.File
import java.io.FileOutputStream
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign


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
            painter = painterResource(id = R.drawable.star_bg),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            // 카드 이미지
            cardNumber?.let { number ->
                val context = LocalContext.current
                val cardImageRes = context.resources.getIdentifier(
                    "tarot_$number",
                    "drawable",
                    context.packageName
                )
                Image(
                    painter = painterResource(id = cardImageRes),
                    contentDescription = "Tarot Card $number",
                    modifier = Modifier
                        .width(200.dp)
                        .height(300.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            // 카드 번호와 해석 텍스트 표시
            val customFont = FontFamily(
                Font(R.font.font_two) // 추가한 폰트 이름과 동일
            )
            Text(
                text = when (cardNumber) {
                    "0" -> "새로운 시작, 모험, 순수"
                    "1" -> "가능성, 창의력, 능력"
                    "2" -> "직관, 지혜, 비밀"
                    "3" -> "풍요, 창조, 사랑"
                    "4" -> "권위, 안정, 책임감"
                    "5" -> "전통, 가르침, 규칙"
                    "6" -> "사랑, 관계, 선택"
                    "7" -> "결단력, 승리, 추진력"
                    "8" -> "내면의 힘, 인내, 용기"
                    "9" -> "고독, 성찰, 지혜"
                    "10" -> "변화, 기회, 행운"
                    "11" -> "공정함, 균형, 결단"
                    "12" -> "희생, 새로운 관점, 기다림"
                    "13" -> "변화, 끝, 새로운 시작"
                    "14" -> "균형, 조화, 인내"
                    "15" -> "유혹, 집착, 억압"
                    "16" -> "충격, 변화, 깨달음"
                    "17" -> "희망, 영감, 치유"
                    "18" -> "혼란, 직관, 숨겨진 감정"
                    "19" -> "성공, 긍정, 활력"
                    "20" -> "재생, 평가, 부활"
                    else -> "완성, 성취, 통합"
                },
                modifier = Modifier.padding(16.dp),
                style = TextStyle(
                    fontFamily = customFont,
                    color = Color.White,
                    fontSize = 44.sp
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = when (cardNumber) {
                    "0" -> "오늘은 새로운 도전을 시작하기 좋은 날입니다. 호기심을 가지고 용감하게 앞으로 나아가세요!"
                    "1" -> "당신의 능력을 믿으세요. 오늘은 목표를 달성할 수 있는 날입니다."
                    "2" -> "당신의 내면의 목소리를 따르세요. 중요한 결정을 앞두고 있다면 신중하게 생각하세요."
                    "3" -> "오늘은 풍요롭고 따뜻한 에너지가 가득한 날입니다. 주변 사람들과 시간을 보내세요."
                    "4" -> "오늘은 리더십을 발휘해야 할 일이 생길 것입니다. 책임감 있게 행동하세요."
                    "5" -> "전통과 규칙을 존중하며 행동하세요. 믿을 만한 조언을 받을 수 있습니다."
                    "6" -> "오늘은 사랑과 관계에서 중요한 선택을 해야 할 수 있습니다. 진심을 따르세요!"
                    "7" -> "목표를 향해 흔들림 없이 전진하세요. 당신의 결단력이 성공을 가져올 것입니다."
                    "8" -> "인내와 부드러움이 오늘의 키워드입니다. 강한 의지로 어려움을 극복하세요!"
                    "9" -> "혼자만의 시간이 필요합니다. 내면의 목소리에 귀 기울여 보세요."
                    "10" -> "예상치 못한 변화가 다가옵니다. 기회를 잘 활용하면 좋은 일이 생길 것입니다."
                    "11" -> "오늘은 공정하고 객관적인 판단이 필요합니다. 선택에 책임을 다하세요."
                    "12" -> "잠시 멈춰서 생황을 새롭게 바라보세요. 기다림이 해결책을 가져다줄 것입니다."
                    "13" -> "무언가가 끝나고 새로운 시작이 열릴 날입니다. 변화에 열린 마음을 가지세요!"
                    "14" -> "오늘은 균형과 조화를 유지해야 합니다. 서두르지 말고 천천히 진행하세요."
                    "15" -> "오늘은 지나친 욕망에 주의하세요. 자신을 돌아보고 자유로워지세요."
                    "16" -> "예상치 못한 일이 생길 수 있습니다. 긍정적으로 받아들이고 성장의 기회로 삼으세요."
                    "17" -> "오늘은 희망이 가득한 날입니다. 새로운 아이디어나 기회가 떠오를 것입니다."
                    "18" -> "감정이 흔들릴 수 있는 날입니다. 직관을 믿고 신중히 행동하세요."
                    "19" -> "밝은 에너지가 가득한 날입니다. 모든 일이 순조롭게 풀릴 것입니다."
                    "20" -> "과거의 결정을 돌아보고 새로운 시작을 준비하세요. 기회가 찾아옵니다."
                    else -> "모든 일이 순조롭게 마무리될 것입니다. 성취감을 느끼며 보람찬 하루를 보내세요!"
                },
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontFamily = customFont,
                    color = Color.White,
                    fontSize = 30.sp
                )
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
