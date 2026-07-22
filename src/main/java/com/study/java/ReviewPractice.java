package com.study.java;

public class ReviewPractice {
    // JAVA21 스터디를 하던 도중 내가 접근제어자(public, private, final 등)에 대해
    // 아무것도 모른다는 것을 느끼게 되었다.

    // 그래서 스터디 진행 도중 접근 제어자에 대해 다시 한번 짚고 넘어가고자 한다

    // 1. public, private
    // 변수, 메서드, 클래스를 누가 접근할 수 있는가를 정하는 키워드!
    static class Person {
        public String name;                 // public : 어디서든 접근이 가능하다 (다른 클래스, 다른 패키지에서도)
        private int age;                    // private : 이 클래스(Person) 안에서만 접근 가능

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public void introduce() {
            // Person 클래스 내부이므로 private인 age도 자유롭게 사용이 가능하다
            System.out.println(name + "는 " + age + "살 입니다.");
        }
    }

    // 2. static - 객체마다 있는 것이 아니라 클래스 자체에 딱 하나
    static class Counter {
        static int totalCount = 0;      // static: 모든 Counter 객체가 공유하는 값
        int myCount = 0;                // non-static : 객체마다 각자 따로 가지는 값

        public void increase() {
            totalCount++;       // 모든 객체가 이 값을 공유하며 같이 증가시팀
            myCount++;          // 이 객체만의 값
        }
    }

    // 3. final - 한번 정해지면 다시는 못 바꿈
    // final 클래스 : 더 이상 상속 불가능
    static final class Circle {
//        class SmailCircle extends Circle {}   // 이렇게 상속하려 하면 컴파일 에러 발생
    }

    // 4. 조합해서 자주 쓰는 패턴 - public static final
    // 어디서든 꺼내 쓸 수 있고, 객체 안만들어도 되고, 절대 안 바뀌는 상수
    public static final int MAX_USERS = 100;

    // 실행 예시
    public static void main(String[] args) {
        // 1) public, private
        Person p = new Person("철수", 26);
    }
}



