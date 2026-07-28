package com.study.java21.textblock;

import java.util.ArrayList;
import java.util.List;

public class TextBlockPractice {
    // TEXT BLOCK (JAVA 15버전부터 정식 도입)
    // 여러 줄 문자열을 \n, + 없이 자연스럽게 작성할 수 있다

    // """(쌍따옴표 3개)로 시작/종료
    // SQL쿼리, JSON, HTML 등 여러 줄 텍스트 작성 시 주로 사용

    public static void main(String[] args) {
        // 1. 기존 방식
        // + 연산자와 \n을 매번 붙여서 사용, 가독성이 떨어졌음
        String oldJson = "{\n" + "  \"name\": \"홍길동\",\n"
                            + "\"age\" : 30\n" + "}";

        System.out.println("====기존 방식====");
        System.out.println(oldJson);

        // 2. TEXT BLOCK 방식
        // 여는 """ 뒤에는 반드시 줄바꿈이 와야 한다
        // 같은 줄에 내용을 적게 되면 컴파일 에러가 발생함

        String textBlockJson = """
                {
                    "name" : "홍길동",
                    "age"  : 30
                }
                """;
        System.out.println("====Text Block 방식====");
        System.out.println(textBlockJson);

        // 3. 들여쓰기 자동 처리 원리
        // Text Block은 '가장 왼쪽에 있는 줄'을 기준으로 공통 들여쓰기를 자동 제거한다
        // 즉, 소스코드에서 보기 좋게 들여써도 실제 문자열에는 불필요한 공백이 안 들어감
        // 마지막 닫는 줄(""")의 위치가 이 기준선을 결정하는 핵심 요소이다
        String indentedText = """
                              첫 번째 줄
                                  두 번째 줄 (들여쓰기 4칸)
                              세 번째 줄
                              """;
        System.out.println("====들여쓰기 처리====");
        System.out.println(indentedText);

        // 닫는 """ 를 맨 왼쪽으로 옮기면 전체 텍스트에 들여쓰기가 그대로 살아있음
        String indextedText2 = """
                첫 번째 줄
                두 번째 줄
        """;    // 닫는 """가 왼쪽에 있어서 기준선이 왼쪽으로 이동함

        System.out.println("====들여쓰기 처리2====");
        System.out.println(indextedText2);


        // 4. 줄 끝 공백/개행 제어
        // Text Block은 각 줄 끝의 공백(trailing space)을 자동으로 제거함
        // 의도적으로 줄 끝 공백을 유지하고 싶으면 \s 를 사용한다
        String trailingSpace = """
                뒤에 공백 유지\s\s\s
                다음 줄""";

        System.out.println("====\\s로 공백 유지====");
        System.out.println("[" + trailingSpace + "]");

        // 줄바꿈 없이 이어붙이고 싶은 땐 \ (백슬래시)로 줄바꿈을 무시한다
        String noNewLine = """
                이 줄과 \
                다음 줄이 하나로 이어짐""";

        System.out.println("==== \\로 줄바꿈 무시====");
        System.out.println(noNewLine);


        // 5. formatted() 와 조합 (JAVA 15에 함께 추가된 String API)
        // Text Block 안에 %s, %d 같은 포맷 지정자를 넣고 formatted()로 값 대입 가능
        String templete = """
                이름 : %s
                나이 : %d
                """.formatted("홍길동", 30);

        System.out.println("==== formatted() 조합 ====");
        System.out.println(templete);


        // * 핵심 규칙 정리
        // 1) 여는 """ 뒤에는 반드시 줄바꿈이 필요함! (컴파일 에러 발생)
        // 2) 공통 들여쓰기 -> 가장 왼쪽 줄과 닫는 """의 위치를 기준으로 자동 제거함
        // 3) 줄 끝 공백 -> 자동 제거되며, 의도적으로 공백을 남기려면 \s 를 사용
        // 4) 줄바꿈 무시 -> 줄 끝에 \ 을 붙이면 다음 줄과 이어진다




        // * 연습 문제
        // 1. 아래와 같은 형태의 영수증 출력
        // ==============================
        //      OURS LEAGUE MART
        // ==============================
        // 주문번호 : 20260728-001
        // 주문일자 : 2026-07-28
        // ------------------------------
        // 상품명           수량    금액
        // ------------------------------
        String receipt = """
        ==============================
             OURS LEAGUE MART
        ==============================
        주문번호 : 20260728-001
        주문일자 : 2026-07-28
        ------------------------------
        상품명           수량    금액
        ------------------------------    
        """;

        System.out.println(receipt);


        // 2. formatted()로 동적 값 채우기
        // 주문번호, 주문일자를 파라미터로 받아서 formatted()로 채워 넣는 메서드를 만드세요
        createHeader("20260728-001", "2026-07-28");


        // 3. 상품 목록 여러 줄 동적 생성
        // List<String> productNames, List<Integer> quantities, List<Integer> prices를 받아서
        // 각 상품을 한 줄씩 아래 형식으로 출력하는 메서드를 만드세요.
        // 아메리카노         2     8000
        // 크루아상           1     4500
        List<String> productNames = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();
        List<Integer> prices = new ArrayList<>();

        productNames.add("아메리카노");
        productNames.add("크루아상");

        quantities.add(2);
        quantities.add(1);

        prices.add(8000);
        prices.add(4500);

        System.out.println(productNames.toString());
        System.out.println(quantities.toString());
        System.out.println(prices.toString());

        StringBuilder sb = new StringBuilder();
        sb.append(productNames.get(0)).append(quantities.get(0)).append(prices.get(0));

        System.out.println(sb.toString());


    }


    // 연습 문제 2번 메서드
    public static String createHeader(String orderId, String orderDate) {
        // Text Block + formatted() 조합으로 구현
        String receipt2 = """
        ==============================
             OURS LEAGUE MART
        ==============================
        주문번호 : %s
        주문일자 : %s
        ------------------------------
        상품명           수량    금액
        ------------------------------  
        """.formatted(orderId, orderDate);

        System.out.println(receipt2);
        return receipt2;
    }
}
