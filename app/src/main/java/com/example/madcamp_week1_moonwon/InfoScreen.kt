package com.example.madcamp_week1_moonwon


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable

@Composable
fun InfoScreen(name: String, phone: String,imageUri: String,  navController: NavController) {

    // 배경 이미지
    Image(
        painter = painterResource(id = R.drawable.gradation_bg),
        contentDescription = "Background",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 90.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // 프로필 이미지 표시
            Card(
                shape = CircleShape,
                modifier = Modifier
                    .padding(bottom = 30.dp)
                    .size(120.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                // 네트워크 이미지 로드
                AsyncImage(
                    model = imageUri,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .padding(0.dp)
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Card(
                shape = RoundedCornerShape(16.dp), // 모서리를 둥글게 설정
                modifier = Modifier
                    .width(700.dp) // 가로 크기 직접 지정
                    .height(120.dp) // 세로 크기 직접 지정
                    .padding(16.dp), // 카드 외부 여백
                elevation = CardDefaults.cardElevation(8.dp), // 그림자 효과

            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    // 이름과 전화번호 표시
                    Text(
                        text = "$name",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold, // 볼드 스타일
                            color = Color.Black, // 까만색 텍스트
                            fontSize = 20.sp
                        ),

                        )
                    Text(
                        text = "$phone",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.Gray,
                            fontSize = 16.sp
                        ),

                        )
                }
            }

            /*Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp)
                    .height(130.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 전화 카드
                ActionCard(
                    icon = R.drawable.call, // 전화 아이콘
                    label = "Call",
                    onClick = {
                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
                        navController.context.startActivity(intent)
                    }
                )
                // 문자 카드
                ActionCard(
                    icon = R.drawable.message, // 문자 아이콘
                    label = "Message",
                    onClick = {
                        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:$phone"))
                        navController.context.startActivity(intent)
                    }
                )
                // 영상통화 카드
                ActionCard(
                    icon = R.drawable.video_call, // 영상통화 아이콘
                    label = "Video Call",
                    onClick = {
                        // 영상통화 Intent 설정
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("tel:$phone"))
                        navController.context.startActivity(intent)
                    }
                )
            }*/

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .height(80.dp),
                horizontalArrangement = Arrangement.SpaceEvenly, // 카드 간 간격을 균등하게 분배
                verticalAlignment = Alignment.CenterVertically // 세로 정렬
            ) {
                // 전화 카드
                ActionCard(
                    icon = R.drawable.call, // 전화 아이콘
                    label = "Call",
                    onClick = {
                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
                        navController.context.startActivity(intent)
                    },
                    modifier = Modifier.weight(1f) // Row의 공간을 균등 분배
                )
                // 문자 카드
                ActionCard(
                    icon = R.drawable.sms, // 문자 아이콘
                    label = "Message",
                    onClick = {
                        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:$phone"))
                        navController.context.startActivity(intent)
                    },
                    modifier = Modifier.weight(1f)
                )
                // 영상통화 카드
                ActionCard(
                    icon = R.drawable.video_call, // 영상통화 아이콘
                    label = "Video Call",
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("tel:$phone"))
                        navController.context.startActivity(intent)
                    },
                    modifier = Modifier.weight(1f)
                )
            }





            /* Row(
     modifier = Modifier
         .padding(10.dp)
         .height(130.dp),
     horizontalArrangement = Arrangement.SpaceEvenly, // 카드 간격을 균등하게 분배
     verticalAlignment = Alignment.CenterVertically // 세로 정렬
 ){
     Card(
         shape = RoundedCornerShape(16.dp), // 모서리를 둥글게 설정
         modifier = Modifier
             .weight(1f) // 각 카드가 Row의 1/3 공간을 차지
             .width(800.dp) // 가로 크기 직접 지정
             .height(90.dp) // 세로 크기 직접 지정
             .padding(10.dp), // 카드 외부 여백
         elevation = CardDefaults.cardElevation(8.dp), // 그림자 효과
     ) { }
     Card(
         shape = RoundedCornerShape(16.dp), // 모서리를 둥글게 설정
         modifier = Modifier
             .weight(1f) // 각 카드가 Row의 1/3 공간을 차지
             .width(800.dp) // 가로 크기 직접 지정
             .height(90.dp) // 세로 크기 직접 지정
             .padding(10.dp), // 카드 외부 여백
         elevation = CardDefaults.cardElevation(8.dp), // 그림자 효과
     ) { }
     Card(
         shape = RoundedCornerShape(16.dp), // 모서리를 둥글게 설정
         modifier = Modifier
             .weight(1f) // 각 카드가 Row의 1/3 공간을 차지
             .width(800.dp) // 가로 크기 직접 지정
             .height(90.dp) // 세로 크기 직접 지정
             .padding(10.dp), // 카드 외부 여백
         elevation = CardDefaults.cardElevation(8.dp), // 그림자 효과
     ) { }
 }*/

                    }
                }
            }


@Composable
fun ActionCard(icon: Int, label: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .padding(horizontal = 8.dp) // 카드 간 간격 설정
            .fillMaxHeight()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(8.dp), // 그림자 효과
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // 아이콘
            Image(
                painter = painterResource(id = icon),
                contentDescription = label,
                modifier = Modifier.size(36.dp) // 아이콘 크기
            )
            Spacer(modifier = Modifier.height(8.dp))
            // 텍스트
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                ),
                color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun InfoScreenPreview() {
    // 테스트용 더미 데이터
    val dummyName = "John Doe"
    val dummyPhone = "010-1234-5678"
    val dummyImageUri = "https://cdn.epnnews.com/news/photo/202009/5344_6506_3653.jpg" // 네트워크 이미지 URL

    // NavController를 미리 생성
    val dummyNavController = rememberNavController()

    InfoScreen(
        name = dummyName,
        phone = dummyPhone,
        imageUri = dummyImageUri,
        navController = dummyNavController
    )
}