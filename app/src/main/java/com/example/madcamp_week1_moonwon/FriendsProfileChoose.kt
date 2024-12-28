package com.example.madcamp_week1_moonwon

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// 데이터 클래스 (Contact와 충돌 방지를 위해 별도 클래스 선언)
data class FriendContact(val name: String, val phone: String, val isUser: Boolean)

// ViewModel: JSON 데이터를 로드하고, isUser=false인 데이터를 필터링
class FriendsProfileViewModel(context: Context) {
    val friends = loadFriends(context)

    private fun loadFriends(context: Context): List<FriendContact> {
        val jsonString = context.assets.open("contacts.json").bufferedReader().use { it.readText() }
        val listType = object : TypeToken<List<FriendContact>>() {}.type
        val allContacts: List<FriendContact> = Gson().fromJson(jsonString, listType)
        return allContacts.filter { !it.isUser } // isUser=false인 데이터만 필터링
    }
}

@Composable
fun FriendsProfileChooseScreen(context: Context, navController: NavController) {
    val viewModel = remember { FriendsProfileViewModel(context) }
    val friends = viewModel.friends

    Box(modifier = Modifier.fillMaxSize()) {
        // 배경 이미지
        Image(
            painter = painterResource(id = R.drawable.gradation_bg),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(modifier = Modifier.padding(16.dp)) {

            Spacer(modifier = Modifier.height(20.dp))
            //화면 상단 FriendsContact 이미지
            Image(
                painter = painterResource(id = R.drawable.friends_contact), // FriendsContact.png를 리소스에 추가하세요
                contentDescription = "Friends Contact Header",
                //contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // 친구 프로필을 LazyColumn으로 표시
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(friends) { friend ->
                    FriendProfileCard(friend = friend)
                }
            }
        }
    }
}


// FriendProfileCard: 친구 프로필 개별 카드
@Composable
fun FriendProfileCard(friend: FriendContact) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),

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
            // 프로필 이미지
            Image(
                painter = painterResource(id = R.drawable.profile_placeholder),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(90.dp)
                    .padding(end = 16.dp),
            )

            // 이름과 전화번호 텍스트
            Column {
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

// 프리뷰용 임시 데이터
val mockFriends = listOf(
    FriendContact(name = "김문원", phone = "010-9913-8511", isUser = false),
    FriendContact(name = "최현우", phone = "010-2925-1283", isUser = false),
    FriendContact(name = "손승완", phone = "010-2925-6574", isUser = false)
)

@Composable
fun FriendsProfileChoosePreview() {
    val navController = rememberNavController() // 프리뷰용 NavController
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
    ) {
        items(mockFriends) { friend ->
            FriendProfileCard(friend = friend)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFriendsProfileChooseScreen() {
    FriendsProfileChoosePreview()
}
