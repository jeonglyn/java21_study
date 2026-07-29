package com.study.java21.patternSwitch;

import com.study.java21.sealed.SealedPractice;

import java.awt.*;

public class SwitchPatternPractice {
    // 타입 계층 예시
    static class Shape{}
    static class Circle extends Shape{
        double radius;
        Circle(double radius){
            this.radius = radius;
        }
    }
    static class Rectangle extends Shape{
        double width, height;
        Rectangle(double width, double height) {
            this.width = width;
            this.height = height;
        }
    }
    static class Triangle extends Shape{
        double base, height;
        Triangle(double base, double height) {
            this.base = base;
            this.height = height;
        }
    }

    // sealed interface 생성 6번에서 사용
    sealed interface PaymentMethod permits CreditCard, Cash {}
    record CreditCard(String number) implements PaymentMethod {}
    record Cash() implements PaymentMethod {}

    // 개념부터 살펴보자면..
    // 이전에 배웠던 Pattern Matching for instanceof : 타입 체크와 형변환을 한번에 처리하는 문법
    //             Sealed Class : permits로 자식 타입을 제한하면, switch에서 default 없이도 컴파일러가 빠진 경우 없음을 검증해줌

    // Pattern Matching switch는 이 두 가지를 합쳐서 switch문 자체를 더 똑똑하게 만든 기능이다.

    // 기존 switch 문의 한계
    // 기존에는 오직 byte, short, int, char, String, enum 같은 값이 정확히 같은지만 비교할 수 있었음
    // 하지만 객체가 어떤 타입인지는 switch로 표현이 불가능해서, if-else와 instanceof 를 써야 했다.


    public static void main(String[] args) {

        // 1. 기존 방식
        Shape shape = new Circle(5.0);
        String oldResult;

        if(shape instanceof Circle){
            Circle c = (Circle) shape;      //매번 형변환이 필요함
            oldResult = "원, 반지름=" + c.radius;
        } else if(shape instanceof Rectangle){
            Rectangle r = (Rectangle) shape;
            oldResult = "사각형, " + r.width + "x" + r.height;
        } else {
            oldResult = "알 수 없음";
        }

        System.out.println(oldResult);

        // 2. Pattern Matching switch - 타입 패턴
        //    case 타입 변수명 -> 형태로 작성하면, 매칭됨과 동시에 변수가 해당 타입으로 바인딩된다
        String result = switch (shape) {
            case Circle c -> "원, 반지름=" + c.radius;
            case Rectangle r -> "사각형, " + r.width + "x" + r.height;
            case Triangle t -> "삼각형, 밑변=" + t.base;
            default -> "알 수 없음";
            //Shape가 sealed가 아니라서 default가 없으면 컴파일 에러 발생함
        };

        System.out.println(result);

        // 3. null 처리 - case null
        // 기존 switch는 대상이 null이면 NullPointerException이 발생
        // Java21 부터는 case null을 명시적으로 처리 가능해짐
        Shape nullShape = null;
        String nullResult = switch (nullShape) {
            case null -> "도형이 없습니다! null";
            case Circle c -> "원 입니다.";
            default -> "알 수 없음";
        };

        System.out.println(nullResult);

        // 4. Guard Pattern - when 절
        // 타입만으로는 부족하고, 조건까지 걸어야할 때 when을 사용한다
        // if문을 case 안에 또 넣지 않아도 된다

        Shape bigCircle = new Circle(100.0);
        String sizeResult = switch (bigCircle) {
            case Circle c when c.radius > 50 -> "큰 원입니다. 반지름 (" + c.radius + ")";
            case Circle c -> "작은 원입니다. 반지름 (" + c.radius + ")";
            // 여기서 주의할 점은 같은 타입(Circle)이라도 when 조건이 있는 case가 먼저 와야한다
            // when이 없는 Circle c가 먼저 오면 그 뒤의 패턴(switch)은 절대 실행되지 않는다
            default -> "원이 아닙니다.";
        };

        System.out.println(sizeResult);


        // 5. Record Pattern 과 결합
        // record는 필드가 명확하므로, switch case 에서 필드 값을 바로 꺼내(구조 분해) 쓸 수 있음
        record Point(int x, int y) {}

        Object point = new Point(3, 4);
        String pointResult = switch (point) {
            case Point(int x, int y) when x == 0 && y == 0 -> "원점입니다.";
            case Point(int x, int y) -> "좌표 (" + x + ", " + y + ")";
            default -> "포인트가 아닙니다.";
        };

        System.out.println(pointResult);



        // 6. sealed + switch = exhaustiveness 검증 (Sealed class 복습)
        PaymentMethod payment = new CreditCard("1234-5678");
        String payResult = switch (payment) {
            case CreditCard c -> "카드: " + c.number();
            case Cash c -> "현금";
            // sealed 인터페이스라서 permits에 있는 타입을 전부 다뤘다면
            // default 없이도 컴파일 에러가 나지 않음 (컴파일러가 누락 여부를 검증)
        };
        System.out.println("=== sealed + exhaustiveness ===");
        System.out.println(payResult);
    }
}
