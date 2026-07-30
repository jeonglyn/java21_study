package com.study.java21.migration;

import java.util.ArrayList;
import java.util.List;

public class OrderNotifier8 {
    //  실습 코드 아래의 코드를 JAVA21 스타일로 리팩토링 하시오.
    //  OrderNotifier21 클래스를 새로 생성하여 작업할 것

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

    // 1. 클래스 상속으로 표현한 알림 타입 (Sealed + Record로 바꿔볼 것)
    static abstract class Notification {
        abstract String render();
    }

    static class OrderPlaced extends Notification {
        String orderId;
        int amount;
        OrderPlaced(String orderId, int amount) {
            this.orderId = orderId;
            this.amount = amount;
        }
        String render() { return "[주문접수] " + orderId + " / " + amount + "원"; }
    }

    static class OrderShipped extends Notification {
        String orderId;
        String trackingNumber;
        OrderShipped(String orderId, String trackingNumber) {
            this.orderId = orderId;
            this.trackingNumber = trackingNumber;
        }
        String render() { return "[배송시작] " + orderId + " / 송장: " + trackingNumber; }
    }

    // 2. instanceof + 캐스팅 반복 (Pattern Matching switch로 바꿔볼 것)
    static String describe(Notification n) {
        if (n instanceof OrderPlaced) {
            OrderPlaced p = (OrderPlaced) n;
            return "고객님, 주문 " + p.orderId + "이(가) " + p.amount + "원에 접수되었습니다.";
        } else if (n instanceof OrderShipped) {
            OrderShipped s = (OrderShipped) n;
            return "고객님, 주문 " + s.orderId + "이(가) 발송되었습니다. 송장번호: " + s.trackingNumber;
        }
        return "알 수 없는 알림입니다.";
    }

    // 3. 문자열 이어붙이기로 만든 여러 줄 메시지 (Text Block으로 바꿔볼 것)
    static String buildEmailBody(String customerName, String message) {
        String body = "안녕하세요, " + customerName + "님\n" +
                "\n" +
                "아래 내용을 확인해주세요:\n" +
                message + "\n" +
                "\n" +
                "감사합니다.";
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
