package com.study.java21.var;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VarPractice {
    public static void main(String[] args) {
        // var : Java 10부터 도입된 지역 변수 타입 추론 (Local Variable Type Inference) 키워드
        // 변수 선언 시 타입을 직접 쓰는 대신 var를 쓰면 컴파일러가 오른쪽 값을 보고 타입을 자동으로 결정

        // 1. 특징
        // - 정적 타입 유지 : JavaScript나 파이썬의 var처럼 타입이 변하는 동적 타입이 아님.
        //                      => 쉽게 말해 런타임이 아닌 컴파일 타임에 타입이 확정됨
        //                      => 결국 런타임 성능 저하가 전혀 없음

        // 2. 기본 사용법
        // 기존 방식
        String name1 = "Java";
        int count = 10;

        // var 사용 : 컴파일러가 오른쪽 데이터값을 보고 String, int로 확정
        var name2 = "Java";
        var count2 = 10;

        // 한번 확정된 타입은 변경이 불가능함
        var text = "hello";
//        text = 123;             // 컴파일 에러남 -> 이미 위에서 String 으로 확정했기 때문에


        // 3. var를 사용할 수 있는 곳

        // 1) 컬렉션
        // 기존 방식 : 타입이 양쪽에 중복
        HashMap<String, List<Integer>> map1 = new HashMap<String, List<Integer>>();

        // var 사용 : 오른쪽 데이터값만 보면 타입을 알 수 있음
        var map2 = new HashMap<String, List<Integer>>();

        var list = new ArrayList<String>();
        list.add("Java");
        list.add("Spring");

        System.out.println(list);       // [Java, Spring]

        // 2) for-each 루프
        var names = List.of("Alice", "Bob", "Charlie");

        // 기존 방식
        for(String name : names) {
            System.out.println(name);
        }

        // var 사용
        for(var name : names) {
            System.out.println(name);   // Alice, Bob, Charlie
        }

        // 3) 전통적인 for문
        // 기존 방식
        for(int i = 0; i < 3; i++) {
            System.out.println(i);      // 0, 1, 2
        }

        // var 사용
        for(var i = 0; i < 3; i++) {
            System.out.println(i);      // 0, 1, 2
        }


        // 4. var를 사용할 수 없는 곳

        // 1) 초기화 없이 선언만 하는 경우 - 추론할 값이 없음
//        var x;  // 컴파일 에러
//        x = 10;


        // 2) null로 초기화 하는 경우 - 이 경우에도 타입 추론이 불가능
//        var user = null; // 컴파일 에러

        // 3) 클래스 필드 - var는 로컬 변수 전용
//        Class Foo {
//            var name = "test";  // 컴파일 에러
//        }

        // 4) 메서드 파라미터
//        void process(var name) {}   // 컴파일 에러

        // 5) 메서드 반환 타입
//        var getName() { return "test"; }    // 컴파일 에러

        // 6) 람다식만 단독으로 대입하는 경우
//        var print = () -> System.out.println("Hello");  // 에러, 함수형 인터페이스 타입이 무엇인지 모름

        // 5. 실무 권장 사항
        // * 가동성이 향상될 때만 사용할 것
        //   -> var가 항상 좋은 건 아님. 오른쪽 데이터만 봐서 타입을 바로 알 수 있는 경우에만 사용하는 것이 좋다.

        // 타입이 명확할 때
        var number = 42;                        // int
        var text2 = "hello";                     // String
        var items = new ArrayList<String>();    // ArrayList<String>

        // 타입이 불명확할 때
        var result = getUserName();             // 반환 타입을 모르면 가독성 저하
        String result2 = getUserName();         // 차라리 이쪽이 더 명확함

        System.out.println(result);     // Alice (값은 출력됨)
        System.out.println(result2);    // Alice
    }


    static String getUserName() {
        return "Alice";
    }
}
