package com.study.java21.sealed;

public class SealedPractice {
    // Sealed Class & Interface란? (JAVA 17)
    // 이 타입을 상속하거나 구현할 수 있는 클래스는 내가 지정한 것들뿐이다 라고 명시적으로 제한하는 문법
    // 기존에는 public class나 public interface를 만들면 누구든 자유롭게 상속/구현할 수 있었으나,
    // sealed는 그 범위를 컴파일러 차원에서 고정시킴

    // ===== 1. 기존 방식의 문제점 =====
    // 누구나 Shape 클래스를 상속할 수 있음 -> 계층이 어디까지 열려있는지 컴파일러가 알 수 없음
    public static abstract class Shape {
    }

    public static class Circle extends Shape {}
    public static class Square extends Shape {}

    // 다른 사람이 언제든 새로운 자식 클래스를 만들 수 있음
    public static class Triangle extends Shape {}


    // ===== 2. sealed로 제한하기 =====
    // sealed : 이 클래스를 상속할 수 있는 대상을 permits로 명시
    // permits 뒤에 나열된 클래스만 Shape2 클래스를 상속할 수 있다
    public static sealed class Shape2 permits Circle2, Square2, Triangle2 {
    }

    // 자식 클래스는 반드시 세 가지 중 하나를 선택해야 함 (final / sealed / non-sealed)

    // 1) final - 더 이상 상속 불가능 (계층을 여기서 완전히 닫는다)
    public static final class Circle2 extends Shape2 {
        private final double radius;

        public Circle2(double radius) {
            this.radius = radius;
        }
    }

    // 2) sealed - 또 다른 permits로 제한된 자식들만 허용 (계층을 이어가되 계속 제한)
    //    Square2는 sealed로 열어두고, 그 아래를 다시 permits로 제한한다
    public static sealed class Square2 extends Shape2 permits SmallSquare {
        private final double side;

        public Square2(double side) {
            this.side = side;
        }
    }

    public static final class SmallSquare extends Square2 {
        public SmallSquare(double side) {
            super(side);
        }
    }

    // 3) non-sealed - 제한을 풀어서 다시 누구나 상속 가능하게 되돌림
    public static non-sealed class Triangle2 extends Shape2 {
        private final double base;
        private final double height;

        public Triangle2(double base, double height) {
            this.base = base;
            this.height = height;
        }
    }

    // Triangle2가 non-sealed라서, 이렇게 누구나 자유롭게 상속 가능
    public static class RightTriangle extends Triangle2 {
        public RightTriangle(double base, double height) {
            super(base, height);
        }
    }


    // ===== 3. 실무에서 가장 많이 쓰는 형태 : sealed interface =====
    // interface에도 sealed를 붙일 수 있음
    public sealed interface PaymentMethod permits CreditCard, BankTransfer, Cash {}

    public record CreditCard(String cardNumber) implements PaymentMethod {}
    public record BankTransfer(String accountNumber) implements PaymentMethod {}
    public record Cash() implements PaymentMethod {}
    // record는 자동으로 final이라 별도 표시 없이 그냥 implements만 하면 된다


    // ===== 4. 핵심 효과 : switch와 결합했을 때 exhaustiveness 체크 =====
    static String describe(PaymentMethod method) {
        // switch가 "값을 반환하는 표현식"으로 쓰일 때는
        // 끝에 세미콜론(;)이 반드시 필요함
        return switch (method) {
            case CreditCard c -> "카드 결제: " + c.cardNumber();
            case BankTransfer b -> "계좌이체: " + b.accountNumber();
            case Cash c -> "현금 결제";
            // default 절이 없어도 컴파일 에러가 안 남!
            // 컴파일러가 permits 목록(CreditCard, BankTransfer, Cash)을 알고 있어서
            // 이 세 가지를 다 처리했으니 더 이상 빠진 경우가 없다는 걸 검증해준다
        };
    }


    // ===== 실행 진입점 =====
    public static void main(String[] args) {
        PaymentMethod card = new CreditCard("1234-5678");
        PaymentMethod cash = new Cash();

        System.out.println(describe(card));   // 카드 결제: 1234-5678
        System.out.println(describe(cash));   // 현금 결제

        // sealed 계층 확인용
        Shape2 c2 = new Circle2(5.0);
        Shape2 t2 = new Triangle2(3.0, 4.0);
        Shape2 rt = new RightTriangle(3.0, 4.0);   // non-sealed라 자유롭게 확장된 예

        System.out.println(c2 instanceof Circle2);       // true
        System.out.println(rt instanceof Triangle2);     // true
    }


    // ===== 정리 - sealed를 쓰는 이유 =====
    // 1. 타입 계층을 내가 정한 범위로 확실히 고정할 수 있다
    // 2. switch 패턴 매칭과 결합하면 빠뜨린 케이스가 없는지를 컴파일 타임에 검증받을 수 있다
    // 3. 도메인 모델에서 이 값은 이 몇 가지 상태 중 하나다 를 표현할 때 매우 유용하다
}