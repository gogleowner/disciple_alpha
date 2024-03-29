package com.disciple.model;

public class ReciteWords {
	private String malthm1_addr;
	private String malthm2_addr;
	private String malthm1;
	private String malthm2;
	private String book;
	
	public ReciteWordsVo getContents_1(int num) {
		switch (num) {
		case 0:
			malthm1_addr = "로마서 10:9~10";
			malthm1 = "네가 만일 네 입으로 예수를 주로 시인하며 또 하나님께서 그를 죽은 자 가운데서 살리신 것을 네 마음에 믿으면 구원을 받으리라"
					+ "사람이 마음으로 믿어 의에 이르고 입으로 시인하여 구원에 이르느니라";
			malthm2_addr = "마태복음 16:16";
			malthm2 = "시몬 베드로가 대답하여 이르되 주는 그리스도시요 살아 계신 하나님의 아들이시니이다";
			book = " ";
			break;
		case 1:
			malthm1_addr = "베드로전서 1:21";
			malthm1 = "예언은 언제든지 사람의 뜻으로 낸 것이 아니요 오직 성령의 감동하심을 받은 사람들이 하나님께 받아 말한 것임이라";
			malthm2_addr = "여호수아 1:8";
			malthm2 = "이 율법책을 네 입에서 떠나지 말게 하며 주야로 그것을 묵상하여 그 안에 기록된 대로 다 지켜 행하라 그리하면 네 길이 평탄하게 될 것이며 네가 형통하리라";
			book = "영적훈련과 성장";
			break;
		case 2:
			malthm1_addr = "로마서 11:36(상)";
			malthm1 = "이는 만물이 주에게서 나오고 주로 말미암고 주에게로 돌아감이라 그에게 영광이 세세에 있을지어다 아멘";
			malthm2_addr = "고린도후서 13:13";
			malthm2 = "주 예수 그리스도의 은혜와 하나님의 사랑과 성령의 교통하심이 너희 무리와 함께 있을지어다";
			book = "영적훈련과 성장";
			break;
		case 3:
			malthm1_addr = "히브리서 4:15";
			malthm1 = "우리에게 있는 대제사장은 우리의 연약함을 동정하지 못하실 이가 아니요 모든 일에 우리와 똑같이 시험을 받으신 이로되 죄는 없으시니라";
			malthm2_addr = "요한복음 14:6";
			malthm2 = "예수께서 이르시되 내가 곧 길이요 진리요 생명이니 나로 말미암지 않고는 아버지께로 올 자가 없느니라";
			book = "기독교의 기본진리(말씀사)";
			break;
		case 4:
			malthm1_addr = "로마서 5:12";
			malthm1 = "그러므로 한 사람으로 말미암아 죄가 세상에 들어오고 죄로 말미암아 사망이 들어왔나니 이와 같이 모든 사람이 죄를 지었으므로 사망이 모든 사람에게 이르렀느니라";
			malthm2_addr = "히브리서 9:27";
			malthm2 = "한번 죽는 것은 사람에게 정해진 것이요 그 후에는 심판이 있으리니";
			book = "기독교의 기본진리(말씀사)";
			break;
		case 5:
			malthm1_addr = "로마서 5:8";
			malthm1 = "우리가 아직 죄인 되었을 때에 그리스도께서 우리를 위하여 죽으심으로 하나님께서 우리에 대한 자기의 사랑을 확증하셨느니라";
			malthm2_addr = "갈라디아서 3:13";
			malthm2 = "그리스도께서 우리를 위하여 저주를 받은 바 되사 율법의 저주에서 우리를 속량하셨으니 기록된 바 나무에 달린 자마다 저주 아래에 있는 자라 하였음이라";
			book = "특종 믿음사건(두란노)";
			break;
		case 6:
			malthm1_addr = "사도행전 2:28";
			malthm1 = "주께서 생명의 길을 내게 보이셨으니 주 앞에서 내게 기쁨이 충만하게 하시리로다 하였으므로";
			malthm2_addr = "갈라디아서 2:20";
			malthm2 = "내가 그리스도와 함께 십자가에 못 박혔나니 그런즉 이제는 내가 사는 것이 아니요 오직 내 안에 그리스도께서 사시는 것이라 이제 내가 육체 가운데 사는 것은 나를 사랑하사"
					+ " 나를 위하여 자기 자신을 버리신 하나님의 아들을 믿는 믿음 안에서 사는 것이라";
			book = "특종 믿음사건(두란노)";

			break;
		case 7:
			malthm1_addr = "고린도전서 12:13";
			malthm1 = "우리가 유대인이나 헬라인이나 종이나 자유인이나 다 한 성령으로 세례를 받아 한 몸이 되었고 또 다 한 성령을 마시게 하셨느니라";
			malthm2_addr = "사도행전 2:38";
			malthm2 = "베드로가 이르되 너희가 회개하여 각각 예수 그리스도의 이름으로 세례를 받고 죄 사함을 받으라 그리하면 성령의 선물을 받으리니";
			book = "놀라운 하나님의 은혜(IVP)";
			break;
		case 8:
			malthm1_addr = "디도서 3:5";
			malthm1 = "우리를 구원하시되 우리가 행한 바 의로운 행위로 말미암지 아니하고 오직 그의 긍휼하심을 따라 중생의 씻음과 성령의 새롭게 하심으로 하셨나니";
			malthm2_addr = "로마서 4:18";
			malthm2 = "아브라함이 바랄 수 없는 중에 바라고 믿었으니 이는 네 후손이 이같으리라 하신 말씀대로 많은 민족의 조상이 되게 하려 하심이라"
					+ "행위에서 난 것이 아니니 이는 누구든지 자랑하지 못하게 함이라";
			book = "놀라운 하나님의 은혜(IVP)";
			break;
		case 9:
			malthm1_addr = "로마서 3:21~22";
			malthm1 = "이제는 율법 외에 하나님의 한 의가 나타났으니 율법과 선지자들에게 증거를 받은 것이라 "
					+ "곧 예수 그리스도를 믿음으로 말미암아 모든 믿는 자에게 미치는 하나님의 의니 차별이 없느니라";
			malthm2_addr = "로마서 8:32";
			malthm2 = "자기 아들을 아끼지 아니하시고 우리 모든 사람을 위하여 내주신 이가 어찌 그 아들과 함께 모든 것을 우리에게 주시지 아니하겠느냐";
			book = "성령 세례와 성령 충만(IVP)";
			break;
		case 10:
			malthm1_addr = "고린도후서 7:1";
			malthm1 = "그런즉 사랑하는 자들아 이 약속을 가진 우리는 하나님을 두려워하는 가운데서 거룩함을 온전히 이루어 육과 영의 온갖 더러운 것에서 자신을 깨끗하게 하자";
			malthm2_addr = "요한일서 3:3";
			malthm2 = "주를 향하여 이 소망을 가진 자마다 그의 깨끗하심과 같이 자기를 깨끗하게 하느니라";
			book = "성령 세례와 성령 충만(IVP)";
			break;
		case 11:
			malthm1_addr = "요한계시록 22:7";
			malthm1 = "보라 내가 속히 오리니 이 두루마리의 예언의 말씀을 지키는 자는 복이 있으리라 하더라";
			malthm2_addr = "데살로니가전서 4:16~17";
			malthm2 = "주께서 호령과 천사장의 소리와 하나님의 나팔 소리로 친히 하늘로부터 강림하시리니 그리스도 안에서 죽은 자들이 먼저 일어나고 "
					+ "그 후에 우리 살아 남은 자들도 그들과 함께 구름 속으로 끌어 올려 공중에서 주를 영접하게 하시리니 그리하여 우리가 항상 주와 함께 있으리라";
			book = "순전한 기독교(홍성사)";
			break;
		}

		ReciteWordsVo vo = new ReciteWordsVo();
		vo.setWord1_addr(malthm1_addr);
		vo.setWord2_addr(malthm2_addr);
		vo.setWord1(malthm1);
		vo.setWord2(malthm2);
		vo.setBook(book);
		return vo;
	}

	public ReciteWordsVo getContents_2(int num) {
		switch (num) {
		case 0:
			malthm1_addr = "마태복음 7:24";
			malthm1 = "그러므로 누구든지 나의 이 말을 듣고 행하는 자는 그 집을 반석 위에 지은 지혜로운 사람 같으리니";
			malthm2_addr = "요한복음 14:21";
			malthm2 = "나의 계명을 지키는 자라야 나를 사랑하는 자니 나를 사랑하는 자는 내 아버지께 사랑을 받을 것이요 나도 그를 사랑하여 그에게 나를 나타내리라";
			book = "내면세계의 질서와 영적성장(IVP)";
			break;
		case 1:
			malthm1_addr = "빌립보서 2:3~4";
			malthm1 = "아무 일에든지 다툼이나 허영으로 하지 말고 오직 겸손한 마음으로 각각 자기보다 남을 낫게 여기고 각각 자기 일을 돌볼뿐더러" +
					" 또한 각각 다른 사람들의 일을 돌보아 나의 기쁨을 충만하게 하라";
			malthm2_addr = "베드로전서 4:11(상)";
			malthm2 = "만일 누가 말하려면 하나님의 말씀을 하는 것 같이 하고 누가 봉사하려면 하나님이 공급하시는 힘으로 하는 것 같이 하라 이는 범사에 " +
					"예수 그리스도로 말미암아 하나님이 영광을 받으시게 하려 함이니 그에게 영광과 권능이 세세에 무궁하도록 있느니라 아멘";
			book = "내면세계의 질서와 영적성장(IVP)";
			break;
		case 2:
			malthm1_addr = "마태복음 28:19~20";
			malthm1 = "그러므로 너희는 가서 모든 민족을 제자로 삼아 아버지와 아들과 성령의 이름으로 세례를 베풀고 " +
					"내가 너희에게 분부한 모든 것을 가르쳐 지키게 하라 볼지어다 내가 세상 끝날까지 너희와 항상 함께 있으리라 하시니라";
			malthm2_addr = "마태복음 5:16";
			malthm2 = "이같이 너희 빛이 사람 앞에 비치게 하여 그들로 너희 착한 행실을 보고 하늘에 계신 너희 아버지께 영광을 돌리게 하라";
			book = "빛으로 소금으로(IVP)";
			break;
		case 3:
			malthm1_addr = "누가복음 6:45";
			malthm1 = "선한 사람은 마음에 쌓은 선에서 선을 내고 악한 자는 그 쌓은 악에서 악을 내나니 이는 마음에 가득한 것을 입으로 말함이니라";
			malthm2_addr = "잠언 15:23";
			malthm2 = "사람은 그 입의 대답으로 말미암아 기쁨을 얻나니 때에 맞는 말이 얼마나 아름다운고";
			book = "빛으로 소금으로(IVP)";
			break;
		case 4:
			malthm1_addr = "고린도전서 6:19~20";
			malthm1 = "너희 몸은 너희가 하나님께로부터 받은 바 너희 가운데 계신 성령의 전인 줄을 알지 못하느냐 너희는 너희 자신의 것이 아니라 " +
					"값으로 산 것이 되었으니 그런즉 너희 몸으로 하나님께 영광을 돌리라";
			malthm2_addr = "디모데후서 2:22";
			malthm2 = "또한 너는 청년의 정욕을 피하고 주를 깨끗한 마음으로 부르는 자들과 함께 의와 믿음과 사랑과 화평을 따르라";
			book = "다윗, 현실에 뿌리박힌 영성(IVP)";
			break;
		case 5:
			malthm1_addr = "시편 119:71";
			malthm1 = "고난 당한 것이 내게 유익이라 이로 말미암아 내가 주의 율례들을 배우게 되었나이다";
			malthm2_addr = "로마서 8:28";
			malthm2 = "우리가 알거니와 하나님을 사랑하는 자 곧 그의 뜻대로 부르심을 입은 자들에게는 모든 것이 합력하여 선을 이루느니라";
			book = "하나님을 경험하는 삶(요단)";
			break;
		case 6:
			malthm1_addr = "로마서 14:7~8";
			malthm1 = "우리 중에 누구든지 자기를 위하여 사는 자가 없고 자기를 위하여 죽는 자도 없도다";
			malthm2_addr = "요한계시록 3:20";
			malthm2 = "볼지어다 내가 문 밖에 서서 두드리노니 누구든지 내 음성을 듣고 문을 열면 내가 그에게로 들어가 그와 더불어 먹고 그는 나와 더불어 먹으리라";
			book = "하나님을 경험하는 삶(요단)";
			break;
		case 7:
			malthm1_addr = "사도행전 20:24";
			malthm1 = "내가 달려갈 길과 주 예수께 받은 사명 곧 하나님의 은혜의 복음을 증언하는 일을 마치려 함에는 나의 생명조차 조금도 귀한 것으로 여기지 아니하노라";
			malthm2_addr = "요한일서 2:16~17";
			malthm2 = "이는 세상에 있는 모든 것이 육신의 정욕과 안목의 정욕과 이생의 자랑이니 다 아버지께로부터 온 것이 아니요 세상으로부터 온 것이라 " +
					"이 세상도, 그 정욕도 지나가되 오직 하나님의 뜻을 행하는 자는 영원히 거하느니라";
			book = "하나님을 경험하는 삶(요단)";
			break;
		case 8:
			malthm1_addr = "베드로전서 5:8";
			malthm1 = "근신하라 깨어라 너희 대적 마귀가 우는 사자 같이 두루 다니며 삼킬 자를 찾나니";
			malthm2_addr = "에베소서 6:10~11";
			malthm2 = "끝으로 너희가 주 안에서와 그 힘의 능력으로 강건하여지고";
			book = "성경은 드라마다(IVP)";
			break;
		case 9:
			malthm1_addr = "요한복음 13:34~35";
			malthm1 = "새 계명을 너희에게 주노니 서로 사랑하라 내가 너희를 사랑한 것 같이 너희도 서로 사랑하라 "
					+ "너희가 서로 사랑하면 이로써 모든 사람이 너희가 내 제자인 줄 알리라";
			malthm2_addr = "요한일서 3:18";
			malthm2 = "자녀들아 우리가 말과 혀로만 사랑하지 말고 행함과 진실함으로 하자";
			book = "성경은 드라마다(IVP)";
			break;	
		}
		
		ReciteWordsVo vo = new ReciteWordsVo();
		vo.setWord1_addr(malthm1_addr);
		vo.setWord2_addr(malthm2_addr);
		vo.setWord1(malthm1);
		vo.setWord2(malthm2);
		vo.setBook(book);
		return vo;
	}
	
}
