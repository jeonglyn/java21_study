package com.study.java21.record;

import java.util.Objects;

public class RecordPractice {
    // Record란? (JAVA16부터)
    // 불변 데이터를 담는 클래스를 한 줄로 선언할 수 있게 해주는 문법
    // DTO, VO처럼 데이터만 담고
    // 로직은 거의 없는 클래스를 만들 때 반복되는 코드 (생성자, getter, equals, toString 등)을
    // 컴파일러가 자동으로 만들어줌

    public class Person {
        // 기존 방식
        private final String name;
        private final int age;

        // 생성자를 직접 작성
        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        // getter도 직접 작성
        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        // equals, hashCode, toString도 직접 작성하거나 IDE로 생성
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Person)) return false;
            Person person = (Person) o;
            return age == person.age && name.equals(person.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age);
        }

        @Override
        public String toString() {

            return "Person{name=" + name + ", age=" + age + "}";
        }
    }

    // Record 방식
    // 필드 선언 한 줄로 생성자, getter, equals, hashCode, toString이 전부 자동 생성됨
    public record Person1(String name, int age) {
        // * 유효성 검사
        //   생성자 본문을 직접 쓰지 않고도, 검증 로직만 추가하고 싶을 때 주로 사용

        // 매개변수 목록 ()을 따로 쓰지 않고, record 선언부에 있는 매개변수(name, age)를 그대로 사용

        public Person1 {
            // ex) 조건을 안지키면 객체 생성 자체를 막는다
            if(age < 0) {
                throw new IllegalArgumentException("나이는 음수일 수 없습니다.");
            }

            if(name == null || name.isBlank()) {
                throw new IllegalArgumentException("이름은 공백일 수 없습니다.");
            }

            // 값 가동도 가능하다 (ex: 공백 제거)
            name = name.strip();
        }

        // * record가 못 하는 제약사항들
        // 1) 추가 인스턴스 필드 선언 불가능
        //    오직 생성자에 선언한 필드만 가진다
//        private String nickname;        // 컴파일 에러뜸

        // 2) 메서드 추가는 가능함
        //    단, 필드 값을 바꾸는 메서드는 못 만듦 -> setter류
        public String greeting() {
            return "Hello, " + name + "!";
        }

        // 3) 다른 클래스를 상속(extends)할 수 없음 (record는 암묵적으로 final이라 상속도 불가능함)
        //    인터페이스 구현은 가능함

    }

    // 자동으로 생성되는 것들
    public static class Main {
        public static void main(String[] args) {
            // 1) 생성자 - 필드 선언 순서 그대로 자동 생성됨
            Person1 p1 = new Person1("철수", 30);

            // 2) getter - 단, get 접두사가 없고 필드명과 동일한 메서드로 생성됨
            //    getName()이 아니라 name() 으로 호출
            System.out.println(p1.name());    // 철수
            System.out.println(p1.age());     // 30

            // 3) toString() - 필드 값을 보기 좋게 자동 포맷팅
            System.out.println(p1);     // Person1[name=철수,age=30]

            // 4) euqals() / hashCode() - 모든 필드 값을 비교해서 자동 생성
            Person1 p2 = new Person1("철수", 30);

            System.out.println(p1.equals(p2));      // true  -> 필드 값이 같으면 true
            System.out.println(p1 == p2);           // false -> 객체 자체는 다름


            // * Record는 불변이라는 점이 핵심이다!
            //   만약 값을 바꾸고 싶다면, 기존 값을 바탕으로 새 객체를 만들어야한다
            //   record의 필드는 선언과 동시에 자동으로 private final이 되기 때문에, 생성 이후에는 절대 값을 바꿀 수 없다!
            Person1 updated = new Person1(p1.name(), 25);

            System.out.println(p1.equals(updated));
        }
    }
}
