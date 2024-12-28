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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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



// Data class for contact information
data class Contact(val name: String, val phone: String)

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
fun ContactScreenWithViewModel(context: Context, navController: NavController) {
    val viewModel = remember { ContactViewModel(context) }
    ContactScreen(viewModel = viewModel, navController = navController)
}



@Composable
fun ContactScreen(viewModel: ContactViewModel, navController: NavController) {
    val contacts = viewModel.contacts
    Box(
        modifier = Modifier
            .fillMaxSize()
    ){

        Image(
            painter = painterResource(id = R.drawable.gradation_bg), // PNG 파일의 리소스 ID
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(contacts) { contact ->
                ContactCard(contact = contact, navController = navController)
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
                navController.navigate("custom") // CustomScreen 경로로 이동
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
//                contentScale = ContentScale.Crop
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
    val mockContact = Contact("김문원", "010-9913-8511")
    ContactCard(contact = mockContact, navController = rememberNavController())
}



