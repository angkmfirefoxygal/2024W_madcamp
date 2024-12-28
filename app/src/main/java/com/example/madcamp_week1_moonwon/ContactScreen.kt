package com.example.madcamp_week1_moonwon

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale



// Data class for contact information
//data class Contact(val name: String, val phone: String)

data class Contact(
    val name: String,
    val phone: String,
    val isUser: Boolean // 사용자 여부를 구분하는 필드 추가
)

@Composable
fun ContactScreen(viewModel: ContactViewModel, navController: NavController) {
    val contacts = viewModel.contacts
    val user = contacts.firstOrNull { it.isUser } // 사용자 정보 가져오기
    val friends = contacts.filter { !it.isUser } // 친구 목록 가져오기

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

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            //화면 상단 FriendsContact 이미지
                Image(
                    painter = painterResource(id = R.drawable.contact), // FriendsContact.png를 리소스에 추가하세요
                    contentDescription = "Friends Contact Header",
                    //contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .align(Alignment.Start)
                )

            Spacer(modifier = Modifier.height(10.dp))

            // 상단 고정된 내 프로필
            user?.let {
                MyProfileCard(contact = it, navController = navController)
            }

            // 친구 목록 (스크롤 가능)
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(friends) { friend ->
                    ContactCard(contact = friend, navController = navController)
                }
            }
        }
    }
}



@Composable
fun ContactScreenWithViewModel(context: Context, navController: NavController) {
    val viewModel = remember { ContactViewModel(context) }
    ContactScreen(viewModel = viewModel, navController = navController)
}

// ViewModel to handle data loading
class ContactViewModel(context: Context) {

    val contacts = loadContacts(context)


    private fun loadContacts(context: Context): List<Contact> {
        val jsonString = context.assets.open("contacts.json").bufferedReader().use { it.readText() }
        val listType = object : TypeToken<List<Contact>>() {}.type
        return Gson().fromJson(jsonString, listType)
    }
}


@Composable
fun MyProfileCard(contact: Contact, navController: NavController) {
    val currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd(E)", Locale.KOREA))

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFCE8))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // 날짜 - 왼쪽 정렬
            Text(
                text = currentDate,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Gray
                ),
                modifier = Modifier.padding(start = 10.dp,bottom = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 프로필 이미지
                Image(
                    painter = painterResource(id = R.drawable.profile_placeholder),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(90.dp),
                        //.padding(end = 20.dp),
                    contentScale = ContentScale.Crop
                )

                // 이름과 번호
                Column(
                    modifier = Modifier
                        .padding(start = 16.dp)
                ) {
                    BasicText(
                        text = contact.name,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    BasicText(
                        text = contact.phone,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                    )
                }
            }

            // "오늘의 운세 확인하러 가기" 버튼
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { navController.navigate("custom") }, // CustomScreen으로 이동
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFC59ADE), // 보라색 버튼
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp) // 버튼 높이 조정
            ) {
                Text(
                    text = "오늘의 운세 확인하러 가기",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}



@Composable
fun ContactCard(contact: Contact,  navController: NavController) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                // InfoScreen으로 이동하며 이름과 번호를 전달
                navController.navigate("info/${contact.name}/${contact.phone}")
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
            Image(
                painter = painterResource(id = R.drawable.profile_placeholder),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(90.dp)
                    .padding(end = 16.dp),

            )
            Column {
                BasicText(
                    text = contact.name,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                )
                BasicText(
                    text = contact.phone,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                )
            }
        }
    }
}



//여기서 부터는 프리뷰 코드!
@Preview(showBackground = true)
@Composable
fun ContactCardPreview() {
    val mockContact = Contact("김문원", "010-9913-8511", true)
    ContactCard(contact = mockContact, navController = rememberNavController())
}

@Preview(showBackground = true, name = "My Profile Card Preview")
@Composable
fun MyProfileCardPreview() {
    // Mock 데이터
    val mockContact = Contact(name = "손승완", "010-0000-0000", false)

    // Mock NavController
    val mockNavController = rememberNavController()

    // MyProfileCard 호출
    MyProfileCard(contact = mockContact, navController = mockNavController)
}
