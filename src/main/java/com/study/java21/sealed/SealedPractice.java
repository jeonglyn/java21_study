package com.study.java21.sealed;

public class SealedPractice {
    // Sealed Class & Interface란? (JAVA 17)
    // 이 타입을 상속하거나 구현할 수 있는 클래스는 내가 지정한 것들뿐이다 라고 명시적으로 제한하는 문법
    // 기존에는 public class나 public interface를 만들면 누구든 자유롭게 상속/구현할 수 있었으나,
    // sealed는 그 범위를 컴파일러 차원에서 고정시킴

    // 기존 방식
    public abstract class Shape {   // 누구나 Shape 클래스를 상속할 수 있음
    }

    public class Circle extends Shape {}
    public class Square extends Shape {}

    // 다른 사람이 언제든 새로운 자식 클래스를 만들 수 있음
    public class Triangle extends Shape {}


    // sealed로 제한하기
    // sealed : 이 클래스를 상속할 수 있는 대상을 permits로 명시
    // permits 뒤에 나열된 클래스만 Shape 클래스를 상속할 수 있다
    public sealed class Shape2 permits Circle2, Square2, Triangle2 {
    }

    // 자식 클래스는 반드시 세 가지 중 하나를 선택해야 함

    // 1) final - 더 이상 상속 불가능 (계층을 여기서 완전히 닫는다)
    public final class Circle2 extends Shape2 {
        private final double radius;

        public Circle2(double radius) {
            this.radius = radius;
        }
    }

    public final class Square2 extends Shape2 {
        private final double side;

        public Square2(double side) {
            this.side = side;
        }
    }

    public final class Triangle2 extends Shape2 {
        private final double base;
        private final double height;

        public Triangle2(double base, double height) {
            this.base = base;
            this.height = height;
        }
    }

    // 2) sealed - 또 다른 permits로 제한된 자식들만 허용 (계층을 이어가되 계속 제한)
    public sealed class Polygon extends Shape permits Triangle3, Square3{
    }

    // 3) non-sealed - 제한을 풀어서 다시 누구나 상속 가능하게 되돌림
    public non-sealed class Triangle extends Shape {}


    // * 실무에서 가장 많이 쓰는 형태 : sealed interface
    // interface에도 sealed를 붙일 수 있음
    public sealed interface PaymentMethod permits CreditCard, BankTransfer, Cash {}

    public final class CreditCard implements PaymentMethod {
        private final String cardNumber;

        public CreditCard(String cardNumber) {
            this.cardNumber = cardNumber;
        }
    }

    public final class BankTransfer implements PaymentMethod {
        private final String accountNumber;

        public BankTransfer(String accountNumber) {
            this.accountNumber = accountNumber;
        }
    }

    public final class Cash implements PaymentMethod {
    }

}
