package com.example.madcamp_week1_moonwon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun DetailScreen(navController: NavController, imageRes: Int) {
    // 타로 카드 설명 매핑
    val tarotDescriptions = mapOf(
        R.drawable.tarot_0 to "\n광대(The Fool)\n" +
                "타로 카드의 첫 번째이며, '새로운 출발'을 의미한다.\n" +
                "당신은 자아를 찾기 위하여 모험을 떠나고 싶어한다.\n\n" +
                "카드 속 젊은이의 발 끝에는 낭떠러지가 있다.\n" +
                "그것을 아는지 모르는지 발을 막 내디디려 하고 있다.\n"+
                "결과에 관계없이 어떠한 모험을 감수해서라도\n도전하고자 하는 열정과 의지가 엿보인다.\n\n" +
                "배낭은 아주 작지만(가진 것은 비록 없지만), 순수함을 나타내는 흰장미를 손에 쥐고 있다. " +
                "아직 세상을 \n잘 모르지만, 당신의 뒤에는 밝은 태양이 비추고 있다.\n" +
                "따라서 당신이 언덕에서 굴러 떨어지더라도 큰 부상은 당하지 않을 것이다.\n\n" +
                "보이지 않는 힘이 당신을 보호하고 있기 때문이다. \n" +
                "당신 옆엔 반려견도 있다.\n당신은 혼자가 아니다.\n\n" +
                "당신이 만약 이 카드를 만난다면, 지금 생각하고 있는\n어떤 일을 과감하게 도전하라는 암시로 받아들여도\n좋을 것이다. " +
                "그러나 무모한 도전보다는, 철저한 준비를\n통하여 당신이 인지하고 있지 못하는 주변의 위험요소를\n하나씩 찾아서 제거해 나가지 않으면 안된다.",
        R.drawable.tarot_1 to "\n마법사 (The Magician)\n" +
                "타로 카드의 두 번째, 마법사는 영혼의 세계와\n인간 세계와의 다리 역할을 하는 존재이다.\n" +
                "오른손에는 하늘 높이 올려진 지팡이가 있으며,왼손은\n땅을 가리킨다. " +
                "마법사는 영적인 힘의 전달자이며\n신과 인간의 중재자이기도 하다.\n\n" +
                "머리에는 영원의 상징이 있으며, 허리에는 자신의 꼬리를 물고 있는 뱀이 있다.\n" +
                "마법사의 책상 위에는 자연의 4가지 기본 요소인 흙, 바람, 불, 물을 상징하는 물건들이 있다.\n" +
                "마법사의 예복은 흰색이며(순수함), 외투는 붉은 색이다(세속적인 경험과 지식).\n" +
                "또 아래쪽 화단에 핀 꽃들을 보면, 흰 백합과 가시 달린 장미가 섞여있다.\n" +
                "마법사는 대립되는 사물 사이의 중재자이다.\n\n" +
                "순수함과 경험, 영혼과 물질, 하늘과 땅 등과 같이 혼합체를 의미한다.\n" +
                "그는 광대와는 달리, 무경험자가 아니다. 그는 진실의 스승이다.\n" +
                "당신이 타로에서 이 마법사를 만났다면, 당신은 당신의 길을 찾을 수 있도록 돕는 훌륭한 조력자를 만날 수 있음을 암시한다.\n" +
                "혹은 당신 스스로의 경험 속에서 해답을 찾을 수 있을 것이다.\n\n" +
                "당신이 만약 새로운 일을 시작하는 단계라면 좋은 인연을 만난다는 징조이기도 하다.\n" +
                "그러나, 타인의 도움보다는 당신의 발전을 위해 스스로 더 노력하지 않으면 안된다.",


        // 나머지 카드를 여기에 추가하세요
        R.drawable.tarot_21 to "이 카드는 완성과 성공을 상징합니다."
    )

    // 선택된 카드의 설명
    val description = tarotDescriptions[imageRes] ?: "카드 설명이 없습니다."

    // 뒤로 가기 버튼
    Box(modifier = Modifier.fillMaxSize()) {
        // 타로 카드 이미지와 설명
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()), // 스크롤 가능하게 설정
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Selected Tarot Card",
                modifier = Modifier
                    .size(400.dp)
                    .padding(top = 16.dp)
            )
            Text(
                text = description,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
        // 왼쪽 상단 뒤로 가기 버튼
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.TopStart) // 왼쪽 상단에 배치
                .padding(16.dp) // 화면 경계에서 16dp 떨어뜨림
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack, // Material Design 화살표 아이콘
                contentDescription = "뒤로 가기"
            )
        }
    }
}