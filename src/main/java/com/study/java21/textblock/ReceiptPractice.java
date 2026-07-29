package com.study.java21.textblock;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ReceiptPractice {
    public static void main(String[] args) {
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
//        List<String> productNames = new ArrayList<>();
//        List<Integer> quantities = new ArrayList<>();
//        List<Integer> prices = new ArrayList<>();
//
//        productNames.add("아메리카노");
//        productNames.add("크루아상");
//
//        quantities.add(2);
//        quantities.add(1);
//
//        prices.add(8000);
//        prices.add(4500);

        // 위와 같이 list에 넣는 것도 아래와 같이 할 수 있다.
        List<String> productNames = List.of("아메리카노", "크루아상");
        List<Integer> quantities = List.of(2, 1);
        List<Integer> prices = List.of(8000, 4500);

        String receipt3 = """
                %s       %d       %d
                """.formatted(productNames.get(0), quantities.get(0), prices.get(0));
        String receipt33 = """
                %s         %d       %d
                """.formatted(productNames.get(1), quantities.get(1), prices.get(1));

        receipt3 = """
                %s\
                %s
                """.formatted(receipt3, receipt33);


        System.out.println(receipt3);

        //========================================================
        // 3번 풀이
        //========================================================

        String productLines = IntStream.range(0, productNames.size())
                .mapToObj(i -> String.format("%-10s %4d %8d", productNames.get(i), quantities.get(i), prices.get(i)))
                .collect(Collectors.joining("\n"));


        System.out.println(productLines);
        //========================================================

        // 4. 줄 끝 공백 다뤄보기
        // 상품명과 수량/금액 사이 정렬을 맞추기 위해 공백을 넣어야 하는 상황이 생길 수 있는데
        // 이때 Text Block에서 배운 \s(공백 유지)가 실제로 필요한지,
        // 아니면 String.format()의 %-10s 같은 포맷팅으로 해결하는 게 나은지 판단하고 구현하고 이유를 정리
        String receipt4 = """
                아메리카노\s\s\s\s2\s\s\s\s8000
                크루아상\s\s\s\s\s1\s\s\s\s4500
                """;

        // Text Block에서 배운 \s 가 더 유용한 것 같음
        // Text Block에 \s만 추가하면 손쉽게 원하는 문구를 만들 수 있다.
        // 물론 내가 String.format을 제대로 사용하지 못해서 그런거 일 수도 있다...

//        String receipt44 = String.format("%-10s", "아메리카노 2 8000");

        System.out.println(receipt4);
//        System.out.println(receipt44);


        //========================================================
        // 4번 풀이
        //========================================================
        // \s를 사용하는 것도 좋으나, \s는 공백 개수를 직접 세어야 하는 불편함이 있음
        // 반면 %-10s는 문자열 길이와 상관없이 항상 10칸을 맞추어준다.
        // 앞에서 내가 작성한 방식이 아니라 따로 인자를 하나씩 넣었어야 했다.
        String line = String.format("%-10s%-6d%4d", "아메리카노", 2, 8000);

        System.out.println(line);

        //========================================================

        // 5. 전체 조합
        // 2~4번에서 만든 메서드들을 사용하여 main()에서 아래처럼 최종 영수증을 출력
//        ==============================
//        OURS LEAGUE MART
//        ==============================
//        주문번호 : 20260728-001
//        주문일자 : 2026-07-28
//        ------------------------------
//        상품명           수량    금액
//        ------------------------------
//        아메리카노         2     8000
//        크루아상           1     4500
//        ------------------------------
//        합계                    12500
//        ==============================
        String receipt5 = createHeader("20260728-001", "2026-07-28");

        receipt5 = """
                %s
                ------------------------------
                %s
                ------------------------------
                합계\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s12500
                """.formatted(receipt5, receipt3);

        System.out.println(receipt5);

        //========================================================
        // 5번 풀이
        //========================================================
        // 내가 한 방식은 하드코딩으로 만든 방식이라 데이터가 변경되면 그때마다 로직을 바꿔야 하는 불편함이 있다...
        // 따라서 전체적인 메서드를 만들고 그 안에 파라미터만 바꿔서 사용할 수 있는 형식이 좋다
        String receipt55 = createReceipt("20260728-001", "2026-07-28", productNames, quantities, prices);

        System.out.println(receipt55);
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
        상품명          수량    금액
        ------------------------------  
        """.formatted(orderId, orderDate);

        return receipt2;
    }

    // 5번 문세 풀이
    // 연습 문제 5번 메서드
    public static String createReceipt(String orderId, String orderDate, List<String> productNames, List<Integer> quantities, List<Integer> prices) {
        // 1) 헤더 부분 생성 (문제2 재사용)
        String header = createHeader(orderId, orderDate);

        // 2) 상품 목록 : 리스트 크기만큼 반복하여 한줄씩 생성 (문제3 개선 후 재사용)
        //  %-10s : 문자를 왼쪽 정렬 10칸으로
        //  %4d   : 숫자를 오른쪽 정렬 4칸으로
        //  %8d   : 숫자를 오른쪽 정렬 8칸으로
        String productLines = IntStream.range(0, productNames.size())
                .mapToObj(i -> String.format("%-10s %4d %8d",
                        productNames.get(i), quantities.get(i), prices.get(i)))
                .collect(Collectors.joining("\n"));

        // 3) 합계 : 하드코딩 대신 실제 계산 (수량 x 금액의 합)
        int total = IntStream.range(0, prices.size())
                .map(i -> prices.get(i) * quantities.get(i))
                .sum();

        // 4) 정적인 구분선/합계 틀은 Text Block으로, 동적인 값은 formatted()로
        return """
               %s----------------------------------
               %s
               ---------------------------------
               합계                       %d
               ================================= 
               """.formatted(header, productLines, total);
    }
}
