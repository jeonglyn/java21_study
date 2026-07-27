package com.study.java21.textblock;

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


    }
}
