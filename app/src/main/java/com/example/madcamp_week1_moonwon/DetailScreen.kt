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
import androidx.compose.ui.layout.ContentScale

@Composable
fun DetailScreen(navController: NavController, imageRes: Int) {
    // 타로 카드 설명 매핑
    val tarotDescriptions = mapOf(
        R.drawable.tarot_0 to "\n광대(The Fool)\n" +
                "타로 카드의 첫 번째이며, '새로운 출발'을 의미한다. " +
                "당신은 자아를 찾기 위하여 여행(모험)을 떠나고 싶어한다.\n\n" +
                "카드 속 젊은이의 발 끝에는 낭떠러지가 있다. " +
                "그것을 아는지 모르는지 발을 막 내디디려 하고 있다. "+
                "결과에 관계없이 어떠한 모험을 감수해서라도 도전하고자 하는 열정과 의지가 엿보인다.\n\n" +
                "배낭은 아주 작지만(가진 것은 비록 없지만), 순수함을 나타내는 흰장미를 손에 쥐고 있다. " +
                "아직 세상을 잘 모르지만, 당신의 뒤에는 밝은 태양이 비추고 있다. " +
                "따라서 당신이 언덕에서 굴러 떨어지더라도 큰 부상은 당하지 않을 것이다.\n\n" +
                "왜냐하면 보이지 않는 힘이 당신을 보호하고 있기 때문이다. " +
                "당신 옆엔 반려견도 있다. 당신은 혼자가 아니다.\n\n" +
                "당신이 만약 이 카드를 만난다면, 지금 생각하고 있는 어떤 일을 과감하게 도전하라는 암시로 받아들여도 좋을 것이다. " +
                "그러나 무모한 도전보다는, 철저한 준비를 통하여 당신이 인지하고 있지 못하는 주변의 위험요소를 하나씩 찾아서 제거해 나가지 않으면 안된다.",
        R.drawable.tarot_1 to "\n마법사 (The Magician)\n" +
                "타로 카드의 두 번째, 마법사는 영혼의 세계와 인간 세계와의 다리 역할을 하는 존재이다. " +
                "오른손에는 하늘 높이 올려진 지팡이가 있으며, 왼손은 땅을 가리킨다. " +
                "마법사는 영적인 힘의 전달자이며 신과 인간의 중재자이기도 하다.\n\n" +
                "머리에는 영원의 상징이 있으며, 허리에는 자신의 꼬리를 물고 있는 뱀이 있다. " +
                "마법사의 책상 위에는 자연의 4가지 기본 요소인 흙, 바람, 불, 물을 상징하는 물건들이 있다.\n\n" +
                "마법사의 예복은 흰색이며(순수함), 외투는 붉은 색이다(세속적인 경험과 지식). " +
                "또 아래쪽 화단에 핀 꽃들을 보면, 흰 백합과 가시 달린 장미가 섞여있다. " +
                "마법사는 대립되는 사물 사이의 중재자이다.\n\n" +
                "순수함과 경험, 영혼과 물질, 하늘과 땅 등과 같이 혼합체를 의미한다. " +
                "그는 광대와는 달리, 무경험자가 아니다. 그는 진실의 스승이다.\n\n" +
                "당신이 타로에서 이 마법사를 만났다면, 당신은 당신의 길을 찾을 수 있도록 돕는 훌륭한 조력자를 만날 수 있음을 암시한다. " +
                "혹은 당신 스스로의 경험 속에서 해답을 찾을 수 있을 것이다.\n\n" +
                "당신이 만약 새로운 일을 시작하는 단계라면 좋은 인연을 만난다는 징조이기도 하다. " +
                "그러나, 타인의 도움보다는 당신의 발전을 위해 스스로 더 노력하지 않으면 안된다.",
        R.drawable.tarot_2 to "\n여사제 (The High Priestess)\n" +
                "이 카드는 옳고 그름이 명확하고 지혜가 있는 여사제를 의미한다. " +
                "마법사와 달리 하늘과 땅을 이어 주는 다리 역할을 한다. " +
                "이 카드는 뚜렷한 이중성을 내포하고 있다. 그래서 그녀는 흰색과 검정색 두 기둥의 사이에 서 있다. " +
                "그녀는 우리 모두의 마음 속에 있는 내부적인 영혼의 안내자다.\n\n" +
                "그녀의 뒤에는, 비옥함과 풍요로움을 나타내는 석류나무로 뒤 덮힌 장막이 있다. " +
                "그녀의 발앞앞에는 초승달이 걸려 있으며, 성모 마리아를 떠오르게 하는 흰 예복과 푸른 겉옷을 함께 입고 있다. " +
                "가슴의 십자가와 손의 토라(모세의 율법)는 그녀가 소유하고 있는 정신적인 힘을 다시 한 번 상기시킨다.\n\n" +
                "여 사제는 우리들의 무의식 속에서 어렴풋하게  진실을 가르쳐 주는 스승을 의미한다. " +
                "그녀는 자신의 힘을 자랑스럽게 과시하지 않는다. 조용하게, 당신에게 '진가를 인정받지 못하는 잠재력'이 있음을 암시한다. " +
                "여사제는, 당신의 문제에 대한 해답을 얻기 위하여 당신 자신의 내부를 깊숙히 들여다 볼 필요성이 있음을 시사한다. \n\n" +
                "B는 Boaz(강인함), J는 Jachin(세우다)이라는 뜻으로 '조화'를 의미하지만, 갈등과 대립의 의미, 즉, 양면성도 함께 가지고 있음을 알아야 한다.",
        R.drawable.tarot_3 to "\n여왕 (The Empress)\n" +
                "여왕은 전형적인 만물의 근원을 의미하는 카드이다. " +
                "그녀는 무성하고 푸른 나무와 풍성한 밀밭으로 둘러싸여 있으며, 새 생명을 잉태한 것처럼 보인다.\n\n" +
                "그녀가 앉아 있는 의자 아래에는 심장 모습의 베개가 있다. 그녀의 겉옷은 비옥함을 상징하는 석류나무로 장식되어져 있다. " +
                "그녀의 뒤 나무사이로 강물이 흘러 내린다. 물은 생명의 상징이다. " +
                "그녀의 손에는 이 땅에서의 모든 만물위에 군림하는 권위를 나타내는 지팡이가 들려져 있다.\n\n" +
                "여 사제가 무의식과 영혼을 의미한다면, 여왕은 모든 하사품 등을 나타낸다. " +
                "이 카드의 의미는 부유함과 임무의 완수, 물질적인 필요와 욕구에 대한 만족 등을 암시한다. \n\n" +
                "만약 당신이 이 카드를 만난다면,  반드시 당신 스스로에게 물어 보아야 하는 것이 있다. " +
                "지금 당신의 삶에 만족하고 있는가? \n\n" +
                "만약 당신이 물질적, 정신적으로 만족스럽다 하더라도, 인생의 성취감을 느끼기 위해서는 반드시 새로운 활력을 찾아야 할 때이다.",
        R.drawable.tarot_4 to "\n황제 (The Emperor)\n" +
                "황제는 여왕에 대한 보완적인 카드이다. " +
                "그는 남성적인 권위, 가장으로서의 특징 등을 의미한다. " +
                "권력과 권위를 나타내며, 그의 엄격한 태도는 여왕의 육감적인 아름다움과는 전혀 다른 것이다. \n\n" +
                "그의 왕좌 뒤에는 메마른 산(당당한  지배자의 상징)이 있다. 왕좌는 4개의 양머리(지적이며 확고한 도전의 상징)로 장식되어 있다. " +
                "그의 오른손은 생명의 상징인 T자형 십자가를 잡고 있다. 그리고 그의 왼손에는 그가 지배하고 있는 천체가 있다. \n\n" +
                "그의 과격한 모습으로  보아, 황제는 냉엄하고 무정하며, 심판을 위해서는 오로지 자신의 법과 의견에 의존할 것이다. " +
                "그래서 이 카드는, 수 많은 어려움에도 불구하고 '목적 달성'과 '성공의 가능성'을 강하게 암시하고 있다. ",
        R.drawable.tarot_5 to "\n교황 (The Hierophant)\n" +
                "이 카드는 인간과 신을 연결한다는 의미에서 마법사와도 비슷하다. " +
                "전통적으로 교황의 역할은 신과 인간의 중재자이다. " +
                "인간이 신과 만나려 할 때 그를 돕는 영혼의 스승이 바로 교황이다. " +
                "방향을 제시하는 마법사와는 달리,  교황은 신뢰받고 있는 안내자이며 당신을 직접 인도하여 정신적인 목표점으로 이끌어 줄 것이다.\n\n" +
                "이 카드는 당신이 어린 시절부터 가르침을 받아왔던 교육관, 그리고 대대로 물려받은 전통에 놓여져 있음을 암시한다. " +
                "무엇인가로부터, 혹은 누구에게선가 권위와 통제를 받고 있을 수도 있다.\n\n" +
                "당신이 이 카드를 만나게 되면, 당신 내면의 정신적인 삶과 선입견 등을 다시 한번 되돌아 볼 필요가 있다. " +
                "아마도 근본적인 것을 다시 생각해 봐야 할 수도 있을 것이다. " +
                "그렇지 않으면, 당신 스스로의 발로 이 세상에 우뚝 설 수 있는 새로운 방법을 배워야 할 지도 모른다.",
        R.drawable.tarot_6 to "\n연인 (The Lovers)\n" +
                "이 카드는 우리 삶에 있어서의 대인관계, 특히 친밀성 또는 성적인 관계를 나타낸다. \n\n" +
                "현재 당신의 생활에 사랑이 얼마나 중요한지를 암시해 주며, 지금 당신이 처해 있는 여러가지 대인관계의 상황 등을 반영하고 있다. " +
                "카드속의 남녀는 그들 위에 있는 구름속 천사들에 의하여 축복받고 보호받는다. \n\n" +
                "태양은 머리위에서 밝고 따뜻하며 평온하게 빛난다. " +
                "그들이 딛고 있는 땅은 인생의 즐거움을 상징하듯이 푸르고 비옥하다. \n\n" +
                "그러나 모든 다른 카드와 마찬가지로, 이 카드에도 부정적인 면이 존재하고 있다. " +
                "여인 뒤의 나무에 있는 뱀은 인간성의 추락, 세상에 대한 유혹과 배신을 암시하고 있다. " +
                "물질에 대한 집착과 탐욕은 과일의 유혹으로 암시하고 있다.\n\n" +
                "한편, 남자 뒤에는 나뭇잎에 불꽃이 이는 검은 색의 나무가 보이는데, '불꽃'은 우리들 가슴속에 있는 정열의 번쩍임을 의미한다. " +
                "그런데 정열을 너무 방치해 두면, 정신적인 불균형을 초래할 수도 있다.\n\n" +
                "이 세상에는 축복과 저주가 공존하고 있다. 그리하여 그것은  또한 선과 악의 양자가 될 잠재력을 보유하고 있다. " +
                "이 카드는 '아름다움' 과 '아름다움의 파괴'라는 양면성이 동시에 존재한다. " +
                "당신이 지금 사랑을 하고 있다면, 축복이지만, 큰 유혹과 불행의 위험 속에 놓여져 있음을 잊어서는 안된다. ",
        R.drawable.tarot_7 to "\n전차 (The Chariot)\n" +
                "전차 위 젊은이의 표정에는 야망과 야심이 넘쳐 난다. " +
                "그는 새로운 경험과 모험을 갈망한다는 점에서 '광대'와도 비슷하다.\n\n" +
                "그러나, 그의 눈은 초점이 뚜렷하며, 광대보다는 분명한 목적이 있다. " +
                "따라서, 발랄한 정열과 잠재력, 그리고 위대한 목표와 성공을 위한 당신의 갈망을 암시한다.\n\n" +
                "카드 상단에는 별로 가득 차 있으며, 견장의 초승달과 왕관의 별들은 하늘의 힘과 영적인 도움을 암시하고 있다." +
                "이 카드는 목표의 추구뿐만이 아니라, 성공을 향한 확고한 의지를 나타내고 있다.\n\n" +
                "전차 앞부분에는 검정색과 흰색의 스핑크스가 있는데, 검정색 스핑크스는 어떠한 대가를 치루어도 목표를 달성하고야 말겠다는 다소 부정적인 야욕을 의미하고, " +
                "흰색은 목적 달성을 위해 열심히 노력하겠다는 긍정적인 의지를 뜻한다.\n\n" +
                "따라서, 이 카드는 적극적인 노력으로 목표 달성이라는 결실을 얻을 수 있다는 의미도 있지만, 당신의 과도한 욕심으로 인하여 당신 인생에 해가 되는 요인도 발생할 수 있음을 경고하고 있다.",
        R.drawable.tarot_8 to "\n힘 (Strength)\n" +
                "이 카드는 '힘'을 의미한다. " +
                "그러나, 강렬한 전사나 황제로서의 파워가 아니라 '온화한 여인의 힘'이다.\n\n" +
                "그녀는 비무장이며 갑옷도, 무기도 없다. " +
                "그녀는 오직 자신 스스로의 내부로부터 나오는 온유하고도 조용한 힘 만으로 맹수(사자)를 압도하고 있다. " +
                "'전차'카드가 남성적인 힘과 강한 의지를 나타낸다면, 이 카드는 내부적인 힘과 어떠한 장애물이라도 제거할 수 있는 인간의 정신적인 따뜻한 힘을 의미한다.\n\n" +
                "그녀가 입고 있는 흰 외투는 청결함과 순결함을 나타내고, 그녀의 머리 위에는 영원의 상징이 보인다. " +
                "그녀는 꽃으로 된 왕관과 벨트를 하고 있으며,  울타리 조차 없는 개방된 푸른 들판에서 무방비 상태로 사자와 함께 서 있다.\n\n" +
                "'전차' 카드는,  세속적인 성공은 우리들의 목표가 에너지로 추구될 때에 비로소 달성할 수 있다는 것을 의미하지만, 이 카드는 우리들의 깊은 힘은 우리들 자신의 내부로부터 나온다는 것을 알려준다. " +
                "사자는 당신 인생의 여행길에서 당신을 방해하는 고난과 시련을 의미한다.\n\n" +
                "따라서, 이 카드는 당신 내부적의 힘과 믿음을 이용하면 어떠한 어려움도 극복할 수 있다는 것을 말해주고 있다. " +
                "즉, 당신의 정신적인 삶을 더욱 더 깊게 해야 할 필요가 있다는 것을 조언하고 있다.",
        R.drawable.tarot_9 to "\n은둔자 (The Hermit)\n" +
                "은둔자는 현명한 마음의 소유자이다. " +
                "그는 '광대' 카드와 마찬가지로 메마른 산꼭대기의 절벽 끝에 서 있다. " +
                "그러나 조심성없는 천진난만한 광대와는 달리 은둔자는 한 손에 지혜의 지팡이를 쥐고 있다. " +
                "또 다른 손에는 길을 모르는 사람에게 방향을 가르쳐 줄 수 있는 빛을 가지고 있다.\n\n" +
                "은둔자는 집단적 무의식의 존재를 통하여 우리 속에 살고 있는 고대의 인물이며, 그의 빛으로 어둠을 꿰뚫고 우리들을 인도하는 정신적 지도자이다. " +
                "은둔자는 우리에게 도전과 올바른 길이 무엇임을 가르쳐 줄 것이다. " +
                "즉, 은둔자는 어려운 시기에 우리를 인도하는 선지자이며, 우리가 길을 잃었을 때 우리에게 양심의 소리를 전하는 예언가이기도 하다.\n\n" +
                "이 은둔자를 만나면, 당신에게 정신적인 도움을 주는 사람이 주위에 나타나거나, 당신이 찾고 있는 답을 바로 당신 자신의 마음속에서 찾을 수 있을 것이라는 암시로 받아들여야 한다.",
        R.drawable.tarot_10 to "\n운명의 수레바퀴 (Wheel of Fortune)\n" +
                "아마 다른 어떠한 카드보다도 강렬한 이 카드는 밝음과 어둠, 선과 악의 이중적인 면을 가진다. " +
                "이 카드는, 돌고 돌아서 다시 돌아온다는 의미로, 시간의 순환적인 속성을 나타낸다.\n\n" +
                "당신의 인생은 상승과 하락으로 가득 차 있다. 그러나, 어려움과 번영이 영원히 지속되는 일은 없다. " +
                "당신의 삶은 항상 변화하므로 당신은 변화에 적응하는 법을 배워야 한다. " +
                "그렇지 않으면 당신은 늘 뒤 처지게 될 것이다.\n\n" +
                "이 카드는 이 세상의 무상한 속성을 분명하게 나타내며, 이 카드를 만났을 때 당신이 어떠한 순환의 주기 속에 있는지, 당신의 삶을 재점검 해 보아야 할 것이다.\n\n" +
                "만약 당신이 지금 최악의 밑바닥에 있는 상황이라면, 자신을 격려하여 앞으로 나아 가게하고, 당신의 상황에 긍정적인 변화를 주기 위해 적극적으로 노력해야 한다. " +
                "혹은 당신이 최고조에 머무르고 있다면, 이것을 당연한 것으로 받아들여서는 안된다.\n\n" +
                "이 카드는 심볼(암시)로 가득 차 있다. " +
                "수레바퀴에는 조합에 따라 의미가 달라지는 문자 기호들과 우주의 4대 원소가 그려져 있다. " +
                "수레바퀴 위에는 스핑크스가 묘한 미소를 지으며 칼을 들고 있다. " +
                "스핑크스는 지혜를 의미하지만, 인간의 생사를 농락한 수수께끼 같은 존재임을 잊어서는 안된다.\n\n" +
                "바퀴 왼편에는 황금 색 뱀이 물 흐르듯 내려오고 있다. " +
                "뱀은 냉정한 지혜를 의미하기도 하지만 인간을 유혹하여 타락 시키는 역할도 한다.\n\n" +
                "바퀴를 등에 지고 날아다니는 붉은 여우는 이집트의 아누비스신으로 영혼의 무게를 측정하는 심판의 신이다.\n\n" +
                "카드의 모서리는 우주의 4대 원소를 상징하는데, 사람은 곧 공기이며 신의 심부름꾼이자 수호자이다. " +
                "소의 형상은 흙이며 의지력과 존엄성을 나타낸다. 사자는 불과 힘. 그리고 독수리는 물과 권력, 영광을 의미한다. " +
                "또, 책은 기록과 연구를 뜻한다.\n\n" +
                "당신은 지금 어느 단계에 서 있는가?",
        R.drawable.tarot_11 to "\n정의 (Justice)\n" +
                "이 카드는 균형과 조화를 나타낸다. " +
                "당신이 인생의 긍정적 혹은·부정적인 무엇인가를 만났을 때, 당신은 반드시 옳고 그름,그리고 어둠과 밝음의 사이에서 선택을 해야만 한다. " +
                "이 카드는, 건전하고 성공적인 인생을 살기 위하여 당신이 반드시 유지해야 할 '균형'을 의미한다.\n\n" +
                "여성은 두 줄의 사이에 앉아 있다. " +
                "그녀의 오른손에는 이중성을 나타내는 양날이 있는 검이 있으며, 왼손에는 균형과 조화를 상징하는 저울이 있다.\n\n" +
                "이 카드를 만나면, 당신은 지금 반드시 어떠한 선택을 해야 할 상황은 아니라는 것이다. " +
                "오히려 당신 인생에 있어서의 혼란을 대체할 평화를 가져다 줄 더 많은 평형을 결정하기 위하여, 불균형과 과도한 영역을 돌아다 보아야 한다.\n\n" +
                "무엇인가를 결정해야 하는 시기라면, 더 많은 시간과 노력이 필요하다.",
        R.drawable.tarot_12 to "\n매달린 사람 (The Hanged Man)\n" +
                "매달린 사람은 포로인 것처럼 보이며, 알 수 없는 강한 힘에 의하여 억압 당하고 있는 것처럼 보인다. " +
                "재앙 혹은 갈등을 암시하고 있다.\n\n" +
                "그러나 좀 더 자세히 보면, 이 카드의 긍정적인 면도 보인다. " +
                "젊은이의 표정은 평화스럽고, 지금의 상황에 만족하고 있는 것 처럼도 보인다. " +
                "또, 밝은 후광이 그의 머리 뒤를 감싸고 있다. 매달린 사람은 기꺼이 희생자가 되려고 한다. " +
                "더 높은 이상을 실현하기 위하여 희생의 길을 선택한 사람인 것이다.\n\n" +
                "그는 십자가 위에 거꾸로 매달려 있다. " +
                "마치 예수의 메시지에 대한 증언 때문에 십자가에 거꾸로 매달린 성 베드로의 경우와도 비슷하다. " +
                "매달린 사람은, 보다 높은 대의를 위하여 순간의 유혹을 던져버리는 강렬한 의지를 뜻한다.\n\n" +
                "그의 이러한 자발적인 희생 때문에 결국 그 마음속에 품고 있는 목적들을 달성하게 된다.\n\n" +
                "이 카드를 만나게 되면, 다른 사람들의 혜택을 위하여 또는 우리 자신이 필요로 하는 것을 완수하기 위하여, 좀 더 사심 없는 태도로 행동할 필요가 있다. " +
                "혹은, 과감한 양보나 희생을 한다면 역설적으로 당신이 원하는 것을 얻을 수도 있을 것이다. ",
        R.drawable.tarot_13 to "\n죽음 (Death)\n" +
                "아마도 타로 카드 중에서 이 '죽음의 카드'가 가장 두렵고 기분 나쁠 것이다. " +
                "이 카드를 만나면, 자신 혹은 가까운 사람의 죽음을 암시하는 것이 아닐까 염려하기도 한다. " +
                "하지만, 이 카드는 역설적으로 가장 긍정적인 것일 수가 있다.\n\n" +
                "죽음이란, 좀 더 가치 있고 중요한 새로운 시작을 의미하며, 변화 단계의 종결을 암시하기 때문이다." +
                "만약 당신이 이 죽음의 카드를 만난다면, 당신 주변에 붙어 있는 불건전한 부착물들을 제거할 필요가 있다.\n\n" +
                "이것은 '변화'의 카드이다. " +
                "당신은 오래된 사고방식의 틀에 사로잡혀, 성장을 방해하는 부착물들에 집착하고 있을지도 모른다. " +
                "이런 집착에서 당신 자신을 해방시켜야만 새롭게 변모할 수 있다.\n\n" +
                "즉, 새로운 출발을 위해 오래된 습관을 제거해야 한다. " +
                "그리하여 당신 삶을 긍정적으로 완전히 변화시켜야 한다. " +
                "\"씨가 땅에 떨어 지지 않으면, 결코 열매는 맺히지 않을  것이다\"라는 문구로 이 죽음의 카드를 이해하라.",
        R.drawable.tarot_14 to "\n절제 (Temperance)\n" +
                "이 카드는 '정의' 카드와 마찬가지로, 당신 삶의 완성을 위한 '균형과 평온함'을 나타낸다. " +
                "카드 속 천사의 한쪽 발은 돌 위에, 다른 발은 강 위에 있다.\n\n" +
                "천사는 미지의 환경에 무모하게 뛰어들기 보다는, 바닷물을 미리 느껴 보고 있다. " +
                "생각없이 뛰어드는 광대의 자유분방함을 완화하고 조절할 것이다.\n\n" +
                "그녀의 손에는 물을 섞고 있는 두 개의 컵이 있다. " +
                "절제된 사람이라면 인생에 있어 서로 융합하며 극단을 피하고 균형을 찾을 것이다. " +
                "그녀의 뒤 지평선 너머로 '자각과 자기 완성'을 암시하는 '지혜의 왕관'이 있다.\n\n" +
                "즉, 이 카드는 절제와 타협을 중요시하고 있다.",
        R.drawable.tarot_15 to "\n악마 (The Devil)\n" +
                "악마의 카드는, 당신이 외부적인 힘에 의하여 구속당하고 있다고 생각하게 만드는, 당신을 억제하는 부정적인 힘을 암시한다. " +
                "이 카드는 당신의 내부에 있는 힘이며, 당신의 공포· 탐욕· 충동 등을 구체적으로 나타낸다.\n\n" +
                "악마의 발 아래에 있는 쇠사슬에 묶인 남녀는, 악마의 힘에 넋을 잃은 상태이다. " +
                "따라서 희망이 없으며 무감각한 표정으로 서 있다. " +
                "그러나, 쇠사슬은 그들의 목에 느슨하게 매달려 있으며, 만약 악마의 부착물을 제거할 수만 있다면, 자유는 그들의 것이다.\n\n" +
                "악마는 기만과 환상의 지배자이다. " +
                "그가 당신을 묶고 있는 쇠사슬은 실존하는 것이 아니다. " +
                "당신은 악마에게 스스로 지배당하고 있는 것이다. " +
                "당신이 이 악마를 만나게 되면, 자신이 지금 무언가에 집착해 있거나 빠져 있지는 않은지 잘 살펴 보아야 한다.\n\n" +
                "당신은 때때로 외부의 어떠한 힘에 조종당하고 있다는 생각에 절망에 빠지기도 한다. " +
                "하지만, 당신 스스로가 무기력의 쇠사슬에 스스로 얽메여져 있는 것이다.",
        R.drawable.tarot_16 to "\n탑 (The Tower)\n" +
                "이것은 악마의 카드 다음에 나오는 것으로, 악마의 구속이 깨어지는 것을 나타낸다. " +
                "탑은 감금과 환각의 장소이며, 이 탑의 파괴는 우리 삶의 자유로움을 경험할 수 있도록 한다. " +
                "게다가, 탑은 당신의 욕심과 착각을 의미한다.\n\n" +
                "기만, 욕심, 환각의 탑이 파괴될 때 당신은 진실과 본체를 맞이할 수 있을 정도로 자유로워질 것이다. " +
                "탑의 급격한 파괴는 갑작스러운 변화, 그리고 그러한 변화가 사람의 갈피를 잡지 못하게 하는 효과 등을 의미한다. " +
                "그러나, 자유스럽고 능력있는 인간이 되기 위해서는,  개인적인 변화라는 시련과 급격한 붕괴를 받아들여야만 한다.\n\n" +
                "이 카드는 영감, 자유,  실재, 그리고 속박으로부터의 탈출에 대한 것이다. " +
                "악마의 카드가 변화의 필요성을 나타낸다면, 이 카드는 현재의 실질적인 변화를 나타낸다.",
        R.drawable.tarot_17 to "\n별 (The Star)\n" +
                "이것은 절제의 카드와 비슷한 조화와 균형의 카드이다. " +
                "그러나, 탑의 카드 다음에 등장하기 때문에 이것은 변화와 혼란의 시기가 종료된 것을 나타낸다. " +
                "이것은 성취, 마음의 평화, 정신적 안정의 카드이다.\n\n" +
                "절제의 카드와 마찬가지로, 카드 속의 여인은 두 개의 물병을 가지고 있다. " +
                "그러나, 이 경우 대지를 비옥하게 하기 위하여 그녀는 물을 쏟아 붓고 있으며, 그녀 주변의 무성한 나무들로 표현되는 비옥함의 순환을 계속 이어나가고 있다.\n\n" +
                "절제의 카드와 마찬가지로, 한쪽 발은 토지위에 다른 발은 물 위에 있다. " +
                "물은 정신적인 영역을  나타내며, 토지는 물질적인 세계의 상징이다.\n\n" +
                "그녀는 경험의 지혜, 고요한 자기 확신, 자기 인식 등을 나타낸다. " +
                "그녀는 광대의 카드와 같이 무모하게 미지의 세계로 돌진하지는 않는다. " +
                "그녀의 지혜 때문에, 고요하게 평화스러운 상태를 유지하는 것이다.\n\n" +
                "이 카드는 우리 내부에 있는 길잡이 그리고, 우리가 신성한 영혼의 보호와 인도 아래 있다는 것을 알고 있는 '우리들 본성의 깊숙한 부분'이다.",
        R.drawable.tarot_18 to "\n달 (The Moon)\n" +
                "이 카드는 직관, 이상, 그리고 무의식의 카드이다. 달은 태양 빛의 반사로써 생기는 불빛을 제공한다. " +
                "비록 흐릿하고, 불명확하고, 높은 의식의 세계로 여행할 때 우리의 앞길을 희미하게 비추지만, 장막에 가려진 무의식의  메시지를 해석하는 우리의 판단력을 도와주고 있다.\n\n" +
                "카드의 바닥에 있는 물은 집단적인 무의식의 내부적인 바다(게와 새우)를 의미한다. " +
                "이 생물은, 마치 작은 길목의 시작 부분에서 '개와 늑대'가 우리 마음 속의 길들여진 것과 야성적인 측면을 나타내듯이, 우리의 마음 깊은 곳으로부터 나타나는 불안한 이미지들을 의미한다.\n\n" +
                "먼 산에 있는 두 개의 탑 사이로 길이 나있다. " +
                "그 탑은 선과 악을 상징한다. " +
                "멀리 보이는 산은, 달빛 아래에서 우리가 찾고 있는 높은 지식을 나타낸다.",
        R.drawable.tarot_19 to "\n태양 (The Sun)\n" +
                "태양은 낙관주의·성취·어두운 밤 뒤에 나타나는 새벽의 이미지이다. " +
                "이 땅 위의 모든 생명의 근원으로서 태양 카드는 축복과 신성, 생명의 원천 그 자체를 나타낸다.\n\n" +
                "즐겁게 놀고 있는 아이는, 우리가 가장 진실된 자기 자신과 조화가 될 때의 내부적 마음의 기쁨을 나타낸다. " +
                "아이가 타고 있는 흰 말은 '힘과 영혼의 순수함'을 나타낸다. " +
                "배경의 해바라기는 태양아래의 '인생과 영혼의 비옥함'을 나타낸다.\n\n" +
                "이 카드는 타로 카드 중에서 가장 긍정적인 카드 중의 하나이다. " +
                "따라서 이 카드를 만났다면, 기쁨과 감사함으로써 받아들여야 한다. " +
                "이 카드와 관련하여 고려해야 할 면이 있다면, 좋은 시절에는 어려운 시기를 잊어 버리기 쉬우니 조심해야 한다는 것이다.\n\n" +
                "당신의 기쁨 속에서도, 지난 어려운 시기들을 결코 잊어서는 안된다. " +
                "즉, 연민과 어려움은 경험의 열매이므로, 결코 잊혀져서는 안되는 것들이다.",
        R.drawable.tarot_20 to "\n심판 (Judgement)\n" +
                "이 카드는 결정(결단력)의 필요성과 연관이 있다. " +
                "당신은 선택의 기로에 있으며, 지금은 바로 당신 앞에서 놓인 문제를 해결해야할 시점이 되었다. " +
                "당신의 커다란 행복을 발전시키기 위하여 결단력을 내릴 시기인 것이다.\n\n" +
                "이 카드는 내부적인 자각, 새로운 삶의 방식의 시작, 세계의 경험 등을 나타낸다. " +
                "우리 내부에서 잠복하고 있는 어떠한 것, 미처 자각하지 못한 지식 또는 진실이 마침내 눈을 뜨며 빛의 세계로 나오게 된 것이다.\n\n" +
                "이 카드에도 긍정적·부정적인 두가지 면이 다 있다.\n\n" +
                "긍정적인 의미에서, 깊은 잠(명쾌함이 결여된 시기)으로부터 깨어나며, 이제서야 정신적인 재탄생과 부활속에서 통찰력이 우리 위에 나타나기 시작한 것이다.\n\n" +
                "부정적인 면은, 우리는 과거의 행동을 설명하기 위하여 그리고, 과거에 우리가 해왔던 것들을 정당화시키기 위하여(진실의 불빛 아래에서) 소환되고 있는 중이다." +
                "밝은 불빛 아래에, 숨겨져 있던 것들을 가지고 나와야할 시기이기도 하다.\n\n" +
                "즉, 자기폭로가 필요한 때이다.",
        R.drawable.tarot_21 to "\n세계 (The World)\n" +
                "이 카드는 임무의 완성과 여행의 끝을 의미한다. " +
                "드디어 목적을 달성하였다. " +
                "광대의 여행은 이제 여기에서 끝을 맺는다.\n\n" +
                "그의 지혜와 경험은 이제 완벽한 것이 되었다. " +
                "이 카드는 운명의 수레바퀴, 즉, 결실을 맺는 뜻을 내포하고 있다.\n\n" +
                "중앙의 춤추는 사람은 '왕권과 행복'을 나타내는 자주빛 겉옷을 입고 있다. " +
                "그녀는 우리들과 함께, 여행이 끝난 것을 기뻐하고 있는데, 여행의 완료뿐만 아니라 그것이 약속하는 새로운 시작을 더불어서 축하하고 있다. " +
                "이 카드는 자각, 기쁨이 가득한 지혜, 그리고 잘못된 억압에서 벗어난 자유를 나타낸다.\n\n" +
                "카드의 원은, 긴 여행에서 진리를 찾기 위하여 고군분투했던 시간들을 의미한다. " +
                "그래도, 원은 여전히 돌고 있는 인생의 수레바퀴다.\n\n" +
                "따라서 우리의 가장 만족스러운 성취의 시간일지라도 그 바퀴는 계속하여 순환할 것이라는 사실을 잊어서는 안된다. " +
                "변화는 언제가는 찾아올 것이다. " +
                "이 때 당신의 여행은 다시 시작될 수도 있다.\n\n" +
                "당신의 인생은 지금 어디쯤 와 있는가?"
    )
    // 선택된 카드의 설명
    val description = tarotDescriptions[imageRes] ?: "카드 설명이 없습니다."

    Box(modifier = Modifier.fillMaxSize()) {
        // 타로 카드 이미지와 설명
        Image(
            painter = painterResource(id = R.drawable.gradation_bg), // PNG 파일의 리소스 ID
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
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
                    .size(330.dp)
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