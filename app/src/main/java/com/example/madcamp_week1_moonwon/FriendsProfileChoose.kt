package com.example.madcamp_week1_moonwon

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import coil.compose.AsyncImage
import androidx.navigation.compose.rememberNavController // for rememberNavController
import androidx.compose.ui.platform.LocalContext // for LocalContext



// 데이터 클래스 (Contact와 충돌 방지를 위해 별도 클래스 선언)
data class FriendContact(val name: String, val phone: String, val isUser: Boolean,val imageUri: String )

// ViewModel: JSON 데이터를 로드하고, isUser=false인 데이터를 필터링
open class FriendsProfileViewModel(context: Context) {
    open val friends = loadFriends(context)

    fun loadFriends(context: Context): List<FriendContact> {
        val jsonString = context.assets.open("contacts.json").bufferedReader().use { it.readText() }
        val listType = object : TypeToken<List<FriendContact>>() {}.type
        val allContacts: List<FriendContact> = Gson().fromJson(jsonString, listType)
        return allContacts.filter { !it.isUser } // isUser=false인 데이터만 필터링
    }
}

@Composable
fun FriendsProfileChooseScreen(context: Context, navController: NavController,imageUri: String?) {
    val viewModel = remember { FriendsProfileViewModel(context) }
    val friends = viewModel.friends

    // 배경 이미지
    Image(
        painter = painterResource(id = R.drawable.gradation_bg),
        contentDescription = "Background",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .fillMaxWidth()
    )
    Box(modifier = Modifier
        .fillMaxSize()
        .fillMaxWidth()
        .fillMaxHeight()
    ) {



        Column(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth()
                .fillMaxHeight()
            ) {




            // 친구 프로필을 LazyColumn으로 표시
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxSize(),



                //verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {


                // Spacer와 Image를 LazyColumn의 첫 번째 아이템으로 추가
                item {
                    Spacer(modifier = Modifier.height(25.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.friends_contact),
                            contentDescription = "Friends Contact Header",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(33.dp)
                        )
                    }
                }

                items(friends) { friend ->
                    FriendProfileCard(
                        friend = friend,
                        context = context,
                        imageUri = imageUri)
                }
            }
        }
    }
}


// FriendProfileCard: 친구 프로필 개별 카드
@Composable
fun FriendProfileCard(friend: FriendContact,context: Context, imageUri: String?) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .height(80.dp)
            .clickable {
                sendToMessenger(context, friend.phone, imageUri) // 친구의 번호를 메신저로 전달
            },

            elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp, // 기본 elevation
            pressedElevation = 8.dp, // 눌렸을 때 elevation
            focusedElevation = 6.dp  // 포커스 상태의 elevation
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFCE8) // 배경색을 #FFFCE8로 설정
        )

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // 이미지를 원형으로 크롭하고 센터 정렬
            Box(
                modifier = Modifier
                    .size(50.dp) // 원형 크기
                    .clip(CircleShape) // 원형으로 자르기
            ) {
                AsyncImage(
                    model = friend.imageUri,
                    contentDescription = "Profile Picture",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop // 센터 크롭
                )
            }


            // 이름과 전화번호 텍스트
            Column(
                modifier = Modifier
                    .padding(start = 10.dp)
            ) {
                BasicText(
                    text = friend.name,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                )
                BasicText(
                    text = friend.phone,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                )
            }
        }
    }
}


//친구 폰번호 메신저 전달 함수
fun sendToMessenger(context: Context, phoneNumber: String, imageUri: String?) {

    val intent = Intent(Intent.ACTION_SEND).apply {
        data = Uri.parse("smsto:$phoneNumber") // 'smsto:' 스키마를 사용해 메시지 앱으로 이동
        type = "image/*" // 첨부 파일의 MIME 타입을 이미지로 설정
        putExtra(Intent.EXTRA_TEXT, "내 오늘의 운세를 공유할게!") // MMS 내용
        putExtra(Intent.EXTRA_STREAM, Uri.parse(imageUri)) // 이미지 첨부
        putExtra("address", phoneNumber) // 수신자 전화번호
    }
    context.startActivity(intent)
}




val mockFriends = listOf(
    FriendContact(
        name = "김문원",
        phone = "010-9913-8511",
        isUser = false,
        imageUri = "https://via.placeholder.com/150" // Placeholder 이미지
    ),
    FriendContact(
        name = "최현우",
        phone = "010-2925-1283",
        isUser = false,
        imageUri = "https://via.placeholder.com/150" // Placeholder 이미지
    ),
    FriendContact(
        name = "손승완",
        phone = "010-2925-6574",
        isUser = false,
        imageUri = "https://via.placeholder.com/150" // Placeholder 이미지
    )
)
@Preview(showBackground = true)
@Composable
fun PreviewFriendProfileCard() {
    FriendProfileCard(
        friend = FriendContact(
            name = "김문원",
            phone = "010-9913-8511",
            isUser = false,
            imageUri = "https://via.placeholder.com/150"
        ),
        context = LocalContext.current,
        imageUri = "https://via.placeholder.com/150"
    )
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun PreviewFriendsProfileChooseScreen() {
    FriendsProfileChooseScreen(
        context = LocalContext.current,
        navController = rememberNavController(),
        imageUri = "https://via.placeholder.com/150"
    )
}

