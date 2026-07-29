package com.study.java21.patternSwitch;

public class OrderStatusPractice {
    // 주문 상태 알림 시스템
    // 배경 시나리오
    // 주문 상태에 따라 다른 알림 메세지를 만들어야 한다. 주문 상태는 아래 4가지이다.
    // 1) 결제 대기중
    // 2) 배송중 (운송장 번호 포함)
    // 3) 배송 완료 (배송 완료 날짜 포함)
    // 4) 주문 취소 (취소 사유 포함)

    // 요구 사항
    // 1. 타입 계층 설계
    //    -> sealed interface OrderStatus를 만들고, permits로 아래 4가지 타입만 허용하도록 제한
    //       * record Pending() implements OrderStatus {} — 결제 대기중 (필드 없음)
    //       * record Shipping(String trackingNumber) implements OrderStatus {} — 배송중
    //       * record Delivered(String deliveredDate) implements OrderStatus {} — 배송 완료
    //       * record Cancelled(String reason) implements OrderStatus {} — 취소
    //    -> 조건 : 클래스 레벨에 선언할 것!

    // 2. 기본 타입 패턴 매칭
    //    -> OrderStatus를 받아서 알림 메시지를 반환하는 메시지를 만들어라(createNotification 메서드 생성)
    //    -> 조건 : OrderStatus가 sealed 이므로 default 없이 4가지를 모두 처리

    // 3. null 처리
    //    -> createNotification에 null이 들어올 수 있다고 가정하고, case null로 주문 정보를 찾을 수 없다고 반환할 것

    // 4. Guard Pattern (when) 활용
    //    -> 배송이 시작된 지 오래된 주문은 다른 메시지를 보여줘야함.
    //       Shipping 상태에서 운송장 번호가 "UNKNOWN"인 경우와 아닌 경우를 when으로 구분할 것

    // 5. Record Pattern으로 구조 분해


    // step1. 타입 계층 설계
    sealed interface OrderStatus permits Pending, Shipping, Delivered, Cancelled {};

    record Pending() implements OrderStatus {}
    record Shipping(String trackingNumber) implements OrderStatus {}
    record Delivered(String deliveredDate) implements OrderStatus {}
    record Cancelled(String reason) implements OrderStatus {}

    // step2. 기본 타입 패턴 매칭


    public static void main(String[] args) {

    }
}
