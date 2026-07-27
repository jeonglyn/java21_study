package com.study.java;

public class ReviewPractice {

    // 2. static - 객체마다 있는 것이 아니라 클래스 자체에 딱 하나
    static class Counter {
        static int totalCount = 0;      // static: 모든 Counter 객체가 공유하는 값
        int myCount = 0;                // non-static : 객체마다 각자 따로 가지는 값

        public void increase() {
            totalCount++;       // 모든 객체가 이 값을 공유하며 같이 증가시킴
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

        System.out.println(p.name); // public이라 외부에서 접근 가능
//        System.out.println(p.age);  // 컴파일 에러 발생! private 라 Person 클래스 밖에서 접근 불가능함

        p.introduce();                // introduce()가 public이라 호출 가능


        // ----- 2) static vs non-static 확인 -----
        Counter c1 = new Counter();
        Counter c2 = new Counter();

        c1.increase();
        c2.increase();
        c2.increase();

        System.out.println(c1.myCount);          // 1  (c1은 자기 것만 셌음)
        System.out.println(c2.myCount);          // 2  (c2도 자기 것만 셌음)
        System.out.println(Counter.totalCount);  // 3  (c1, c2가 공유하는 값이라 합쳐짐)


        // ----- 3) final 확인 -----
        final int MAX_SCORE = 100;
//         MAX_SCORE = 200;   // 컴파일 에러! final은 재할당 불가능
        System.out.println(MAX_SCORE);           // 100


        // ----- 4) public static final 확인 -----
        // 객체(AccessModifierPractice)를 만들지 않고도 바로 접근 가능
        System.out.println(ReviewPractice.MAX_USERS);   // 100
    }
}



