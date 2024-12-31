package com.example.madcamp_week1_moonwon

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import android.content.Context
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
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
    val isUser: Boolean,
    val imageUri: String // 이미지 URI 필드 추가
)

//json을 받아오는 viewModel을 받아오는 ...
@Composable
fun ContactScreenWithViewModel(context: Context, navController: NavHostController) {
    val viewModel = remember { ContactViewModel(context) }
    ContactScreen(viewModel = viewModel, navController = navController)
}


// ViewModel to handle data loading
open class ContactViewModel(context: Context) {

    open val contacts = loadContacts(context)

    private fun loadContacts(context: Context): List<Contact> {
        val jsonString = context.assets.open("contacts.json").bufferedReader().use { it.readText() }
        val listType = object : TypeToken<List<Contact>>() {}.type
        return Gson().fromJson(jsonString, listType)
    }
}


@Composable
fun ContactScreen(viewModel: ContactViewModel, navController: NavController) {
    val contacts = viewModel.contacts
    val user = contacts.firstOrNull { it.isUser } // 사용자 정보 가져오기
    val friends = contacts.filter { !it.isUser } // 친구 목록 가져오기

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
            .fillMaxWidth()
            .fillMaxHeight()
    ) {



        Column(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth()
                .fillMaxHeight()
        ) {


            // 친구 목록 (스크롤 가능)
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Friends Contact Header를 첫 번째 아이템으로 추가
                item {
                    Spacer(modifier = Modifier.height(25.dp))
                    Image(
                        painter = painterResource(id = R.drawable.contact),
                        contentDescription = "Friends Contact Header",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 5.dp)
                            .height(40.dp),

                    )
                }
                // MyProfileCard를 첫 번째 아이템으로 추가
                item {
                    user?.let {
                        MyProfileCard(contact = it, navController = navController)
                    }
                }

                items(friends) { friend ->
                    ContactCard(contact = friend, navController = navController)
                }


                // 맨 마지막에 빈 아이템 추가
                item {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp) // 80dp 높이의 빈 공간 추가
                    )
                }

            }

        }
    }
}






@Composable
fun MyProfileCard(contact: Contact, navController: NavController) {
    val currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd(E)", Locale.KOREA))

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
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



                // 프로필 이미지를 원형으로 크롭하고 센터 정렬
                Box(
                    modifier = Modifier
                        .size(70.dp) // 원형 크기
                        .clip(CircleShape) // 원형으로 자르기
                ) {
                    AsyncImage(
                        model = contact.imageUri,
                        contentDescription = "Profile Picture",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop // 센터 크롭
                    )
                }

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
fun ContactCard(contact: Contact, navController: NavController) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                // InfoScreen으로 이동하며 name, phone, imageUri를 전달
                //navController.navigate("info/${contact.name}/${contact.phone}/${contact.imageUri}")

                val route = "info/${Uri.encode(contact.name)}/${Uri.encode(contact.phone)}/${Uri.encode(contact.imageUri)}"
                navController.navigate(route)
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
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {


            // 이미지를 원형으로 크롭하고 센터 정렬
            Box(
                modifier = Modifier
                    .size(70.dp) // 원형 크기
                    .clip(CircleShape) // 원형으로 자르기
            ) {
                AsyncImage(
                    model = contact.imageUri,
                    contentDescription = "Profile Picture",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop // 센터 크롭
                )
            }


            Column (
                modifier = Modifier
                    .padding(start = 10.dp)
            ){
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



@Preview(showBackground = true, widthDp = 360, heightDp = 100)
@Composable
fun ContactCardPreview() {
    val dummyContact = Contact(
        name = "Friend Name",
        phone = "010-1234-5678",
        isUser = false,
        imageUri = "https://cdn.mhnse.com/news/photo/202410/341169_389007_1445.jpg"
    )
    val dummyNavController = rememberNavController()

    ContactCard(contact = dummyContact, navController = dummyNavController)
}

@Preview(showBackground = true, widthDp = 360, heightDp = 250)
@Composable
fun MyProfileCardPreview() {
    val dummyContact = Contact(
        name = "User Name",
        phone = "010-9876-5432",
        isUser = true,
        imageUri = "https://cdn.mhnse.com/news/photo/202410/341169_389007_1445.jpg"
    )
    val dummyNavController = rememberNavController()

    MyProfileCard(contact = dummyContact, navController = dummyNavController)
}

