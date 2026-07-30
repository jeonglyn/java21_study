package com.study.java21.migration;

import java.util.ArrayList;
import java.util.List;

public class OrderNotifier21 {
    //  요구사항 (Java 21 스타일 버전 OrderNotifier21.java 새로 작성)
    //  Notification 추상 클래스 → sealed interface로, OrderPlaced/OrderShipped → record로 변환
    //  describe() 메서드 → Pattern Matching switch + Record 패턴으로 변환 (default 없이 exhaustive하게)
    //  buildEmailBody() → Text Block(""") 으로 변환
    //  main()에서 동일한 결과가 출력되는지 확인

    //  힌트
    //  sealed interface 선언: sealed interface Notification permits OrderPlaced, OrderShipped {}
    //  Record 선언: record OrderPlaced(String orderId, int amount) implements Notification {}
    //  switch 패턴 매칭: case OrderPlaced(String orderId, int amount) -> ...
    //  Text Block: """ 뒤에 바로 줄바꿈, %s 자리에 .formatted(customerName, message) 활용 고려

    sealed interface Notification permits OrderPlaced, OrderShipped {}

    record OrderPlaced(String orderId, int amount) implements Notification {}
    record OrderShipped(String orderId, String trackingNumber) implements Notification {}

    static String describe(Notification n) {
        return switch (n) {
            case OrderPlaced(String orderId, int amount) -> "고객님, 주문 " + orderId + "이(가) " + amount + "원에 접수되었습니다.";
            case OrderShipped(String orderId, String trackingNumber) -> "고객님, 주문 " + orderId + "이(가) 발송되었습니다. 송장번호: " + trackingNumber;
        };
    }


    static String buildEmailBody(String customerName, String message) {
        String body = """
                안녕하세요, %s님
                
                아래 내용을 확인해주세요:
                %s
                
                감사합니다.
                """.formatted(customerName, message);

        return body;
    }


    public static void main(String[] args) {
        List<Notification> notifications = new ArrayList<>();
        notifications.add(new OrderPlaced("ORD-001", 35000));
        notifications.add(new OrderShipped("ORD-001", "TRK-998877"));

        for (Notification n : notifications) {
            System.out.println(describe(n));
        }

        System.out.println(buildEmailBody("김철수", "주문하신 상품이 발송되었습니다."));
    }


}
