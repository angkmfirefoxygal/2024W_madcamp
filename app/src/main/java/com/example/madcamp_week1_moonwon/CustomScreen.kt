package com.example.madcamp_week1_moonwon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun CustomScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        // 배경 이미지
        Image(
            painter = painterResource(id = R.drawable.gradation_bg),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // "친구에게 공유하기" 버튼, 나중에 변수로 사용자 운세 확인 후 공유할 수 있도록 조절 할 예정
        Button(
            onClick = {
                // FriendsProfileChoose로 이동
                navController.navigate("friends_profile")
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

@Preview(showBackground = true)
@Composable
fun CustomScreenPreview() {
    val fakeNavController = rememberNavController() // 더미 NavController
    CustomScreen(navController = fakeNavController)
}


