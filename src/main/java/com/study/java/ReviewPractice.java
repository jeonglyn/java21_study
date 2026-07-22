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

    // 2.
}



