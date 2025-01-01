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
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
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

    //val friends = contacts.filter { !it.isUser } // 친구 목록 가져오기

    //Search 관련 변수 선언
    var isSearchActive by remember { mutableStateOf(false) } // 검색 활성화 여부
    // 검색 상태 변수 선언
    var searchQuery by remember { mutableStateOf("") } // 상태를 저장하는 변수

    val FriendFilteredContacts = friends.filter { contact ->
        contact.name.contains(searchQuery, ignoreCase = true) // 검색어와 비교

    }

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

            ) {




            // 친구 프로필을 LazyColumn으로 표시
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxSize(),



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

                // search box 밑 공간 추가
                item {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                    )
                }

                //FriendSearchBox 추가
                item {
                    FriendSearchBox (
                        searchQuery = searchQuery,
                        onQueryChanged = { searchQuery = it }, // 상태 업데이트
                        onFocusChanged = { isSearchActive = it } // 검색창 포커스 상태 업데이트
                    )

                }

                // search box 밑 공간 추가
                item {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(15.dp)
                    )
                }

                // 검색 상태에 따라 동적으로 친구 목록 표시
                if (isSearchActive && searchQuery.isNotBlank()) {
                    // 검색 활성화 및 검색어가 입력된 경우
                    items(FriendFilteredContacts) { friend ->
                        FriendProfileCard(
                            friend = friend,
                            context = context,
                            imageUri = imageUri
                        )
                    }
                } else {
                    // 검색 비활성화 또는 검색어가 비어있는 경우
                    items(friends) { friend ->
                        FriendProfileCard(
                            friend = friend,
                            context = context,
                            imageUri = imageUri
                        )
                    }
                }

                // 맨 아래에 빈 공간 추가
                item {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                    )
                }


                /*items(friends) { friend ->
                    FriendProfileCard(
                        friend = friend,
                        context = context,
                        imageUri = imageUri)
                }*/
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class) // ExperimentalMaterial3Api를 사용 허용
@Composable
fun FriendSearchBox(searchQuery: String, onQueryChanged: (String) -> Unit, onFocusChanged: (Boolean) -> Unit) {
    Box(
        modifier = Modifier
            .height(55.dp)
            //.fillMaxWidth()
            .padding(horizontal = 16.dp)
            //.padding(bottom = 5.dp)
            .clip(RoundedCornerShape(50.dp))
            .background(Color(0xFFFFFCE8)),// 배경색 설정
        //.shadow(8.dp, RoundedCornerShape(50.dp)), // 그림자 효과
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            // 검색 아이콘
            Icon(
                painter = painterResource(id = R.drawable.search_icon), // Drawable 아이콘
                contentDescription = "Search Icon",
                tint = Color.Gray,
                modifier = Modifier
                    .padding(start=10.dp, top = 3.dp)
                    .size(22.dp)
            )



            // 검색 입력란
            TextField(
                value = searchQuery,
                onValueChange = { onQueryChanged(it) },
                placeholder = {
                    Text(
                        fontSize = 13.sp,
                        text = "운세를 공유할 친구를 검색하세요",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.Gray
                            //Color(0xFFD9D9D9) // Placeholder 색상
                        )
                    )
                },
                singleLine = true,
                colors = androidx.compose.material3.TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    cursorColor = Color.Gray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        onFocusChanged(focusState.isFocused) // 포커스 상태 업데이트
                    }
            )
        }
    }
}


// FriendProfileCard: 친구 프로필 개별 카드
/*@Composable
fun FriendProfileCard(friend: FriendContact,context: Context, imageUri: String?) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(vertical = 5.dp)
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
}*/

@Composable
fun FriendProfileCard(friend: FriendContact, context: Context, imageUri: String?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clickable {
                sendToMessenger(context, friend.phone, imageUri) // 친구의 번호를 메신저로 전달
            },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
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
                    .fillMaxWidth()
                    .padding(start = 10.dp)
            ) {
                BasicText(
                    text = friend.name,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight(550), // SemiBold보다 약간 더 얇게 설정
                        fontSize = 18.sp,
                        color = Color(0xFF635A5A) // 글자 색상 변경
                    )
                )
                BasicText(
                    text = friend.phone,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 14.sp,
                        color = Color(0xFF635A5A) // 글자 색상 변경
                    )
                )
            }
        }

        // 구분선 (아래)
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 3.dp)
                .padding(start = 60.dp)
                .height(1.dp)
                .background(Color(0xFFFFFCE8)) // 구분선 색상
        )
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

