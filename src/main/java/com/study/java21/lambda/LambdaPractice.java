package com.study.java21.lambda;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.*;

public class LambdaPractice {
    // 람다식 : 메서드를 단순한 식 형태로 표현한 것

    // * 람다식을 사용하는 이유
    // 1. 코드가 짤아짐  2. 가독성 향상  3. 함수형 프로그래밍 스타일 지원

    // 우리가 평소에 메서드를 만드는 방식
    public static boolean isOver4(int n) {
        return n > 4;
    }

    // 이 메서드를 어딘가에 "전달"하고 싶을 때
    // 기존 Java 8 이전에는 이렇게 익명 클래스를 만들어야함
    Predicate<Integer> condition = new Predicate<Integer>() {
        @Override
        public boolean test(Integer n) {
            return n > 4;
        }
    };

    // Java 8부터 Lambda로 한줄로 표현이 가능해짐
    Predicate<Integer> condition2 = (n) -> n > 4;

    // 파라미터가 하나면 괄호로 생략 가능
    Predicate<Integer> condition3 = n -> n > 4;

    public static void main(String[] args) {
        // * 람다식 문법 구조
        // 형식 : (매개변수) -> 표현식 또는 { 실행문 }
        // 예시 : (int x, int y) -> { return x + y; }
        // 더간단히 : (x, y) -> x + y

        // 파라미터 없을 때
        Runnable r = () -> System.out.println("Hello");

        // 파라미터 하나일 때 (괄호 생략 가능)
        Consumer<String> print = s -> System.out.println(s);

        // 파라미터 여러 개일때 (괄호 필수)
        Comparator<Integer> compare = (a, b) -> a - b;

        // 로직이 여러 줄일 때 (중괄호 + return 필요)
        Predicate<String> isLong = s -> {
            int length = s.length();
            return length > 5;          // 블록일 땐 return 명시
        };

        // 람다식은 아래 함수형 인터페이스들과 함께 자주 사용됨

        // 1. Predicate<T> - T를 받아서 boolean 반환 (조건 검사)
        // 데이터 필터링 (Stream.filter)
        Predicate<Integer> isEven = n -> n % 2 == 0;
        System.out.println(isEven.test(4));  // true
        System.out.println(isEven.test(5));  // false

        // 2. Function<T, R> = T를 받아서 R 반환 (변환)
        // 데이터 변환 (Stream.map)
        Function<String, Integer> toLength = s -> s.length();
        System.out.println(toLength.apply("hello"));

        // 3. Consumer<T> - T를 받아서 아무것도 반환 안함 (소비)
        // 데이터 소비 (List.forEach)
        Consumer<String> printer = s -> System.out.println(s);
        printer.accept("hello");

        // 4. Supplier<T> - 아무것도 안받고 T 반환 (공급)
        // 데이터 공급 (Optional.orElseGet)
        Supplier<String> supplier = () -> "hello!!";
        System.out.println(supplier.get());

        // 5. Runnable - 매개변수, 리턴 모두 없음
        // 스레드 작업 정의
        Runnable task = () -> System.out.println("hi");

        // Lambda를 더 줄이는 문법
        // s -> System.out.println(s) -> System.out::println
        Consumer<String> printer2 = System.out::println;
        printer2.accept("hello everyone");

        // s -> s.length() 를 아래처럼 줄일 수도 있음
        Function<String, Integer> length = String::length;
        System.out.println(length.apply("hello everyone"));

    }
}
