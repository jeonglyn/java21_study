package com.study.java21.pattern;

public class PatternMatchingPractice {
    // Pattern Matching for instanceof란?
    // instanceof로 타입을 확인한 뒤 그 타입으로 캐스팅하는 과정을 생략하고, 바로 변수에 담아 쓸 수 있게 해주는 문법
    // 타입 확인 + 캐스팅 + 변수 선언을 한번에 처리하여 코드가 짧아진다.

    // 3) 부정문(!)과 함께 사용할 때 - 이른 반환 (early return) 패턴
    // obj가 String이 아니면 즉시 return 하므로
    // 그 아래 코드에서는 obj가 String이라는 게 보장됨 -> s를 계속 사용 가능
    static void printLength(Object obj) {
        if (!(obj instanceof String s)) {
            return;
        }
        // 여기 도달했다는 건 obj가 String이라는 뜻이므로 s 사용 가능
        System.out.println(s.length());
    }

    public static void main(String[] args) {
        // 기존 방식(JAVA 15 이전)
        Object obj = "Hello";

        if(obj instanceof String) {
            // 타입 확인 후, 별도로 캐스팅해서 새 변수에 담아야함
            String s = (String) obj;
            System.out.println(s.length());
        }

        // Pattern Matching 방식
        Object obj2 = "Hello";

        // instanceof 뒤에 바로 변수명(s)을 적으면, 캐스팅 없이 s를 바로 사용 가능
        // 조건이 true 일 때 컴파일러가 자동으로 obj가 String으로 캐스팅해서 s라는 변수에 담아줌
        // 이때 s를 바인딩 변수 라고 부른다
        if(obj2 instanceof String s) {
            System.out.println(s.length());
        }

        // * 바인딩 변수의 사용 가능 범위
        // 바인딩 변수는 아무데서나 쓸 수 있는게 아니라, true로 확정된 이후의 영역에서만 사용할 수 있다
        Object obj3 = "Hello";

        // 1) if 블록 안에서 사용 - 가장 기본적인 형태
        if(obj3 instanceof String s) {
            System.out.println(s.toUpperCase());
        }

//        System.out.println(s);  // 컴파일 에러 => 블록 밖이라 s가 존재하지 않는다

        // 2) && 로 조건을 이어붙일 때 - 뒤쪽에서 s 사용 가능
        // obj instanceof String s가 true 여야 뒤의 && 조건도 검사하므로,
        // 뒤쪽 조건이 실행되는 시점엔 s가 이미 String으로 확정되어있다.
        if(obj3 instanceof String s2 && s2.length() > 3) {
            System.out.println(s2 + "는 3글자보다 깁니다");
        }

        // 3) 부정문과 함께 사용할 때 예시 코드는 위에
        printLength("Hello");   // 5
        printLength(123);       // String이 아니라서 아무 출력 X

        // 4) OR(||)과 함께 사용할 땐 주의!
        // obj instanceof String s가 false여도 뒤 조건(obj instanceof Integer)이 실행될 수 있어서
        // s가 String으로 확정됐다고 보장할 수 없음 -> 따라서 뒤에서 s 사용 불가능

//        if(obj instanceof String s || obj instanceof Integer) {
//            System.out.println(s);      // 컴파일 에러 발생
//        }
    }

    // * 실무에서 자주 사용하는 방법 : equals() 오버라이드
    static class Person {
        private final String name;

        public Person(String name) {
            this.name = name;
        }

        // 기존 방식
//        @Override
//        public boolean equals(Object o) {
//            if(!(o instanceof Person)) {
//                return false;
//            }
//
//            Person p = (Person) o;  //캐스팅 코드가 별도로 필요했음
//            return name.equals(p.name);
//        }

        // Pattern Matching 방식
        @Override
        public boolean equals(Object o2) {
            return o2 instanceof Person p && name.equals(p.name);
        }
    }
}
