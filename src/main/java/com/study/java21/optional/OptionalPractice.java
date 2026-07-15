package com.study.java21.optional;

import java.util.Optional;

public class OptionalPractice {
    public static void main(String[] args) {
        // Optional이란?
        // null 대신 사용하는 "값이 있을 수도, 없을 수도 있는 컨테이너"
        // 기존에는 null 체크를 깜빡하면 NPE(NullPointerException)가 터졌는데
        // Optional을 쓰면 "값이 없을 수 있음"을 타입으로 명시하여 컴파일 단계에서 처리를 강제할 수 있음

        // 쉽게 요약하자면
        // null 처리를 안전하게 수행하고, NPE 발생을 방지하기 위한 래퍼(Wrapper) 클래스임

        // 1. Optional 객체 생성하기
        // Optional 객체는 상황에 따라 3가지 정적 팩토리 메서드로 만듦

        // 1) 절대 null이 아닌 경우 (null을 넣으면 즉시 NPE 발생)
        // of() - 값이 반드시 존재할 때
        Optional<String> opt1 = Optional.of("hello");

//        Optional<String> opt12 = Optional.of(null);  //npe 발생

        // 2) null일 수도 있고 아닐 수도 있는 경우 (실무에서 가장 많이 사용)
        // ofNullable()
        String maybeNull = null;
        Optional<String> opt2 = Optional.ofNullable(maybeNull);

        // 3) 빈 객체를 만들 경우
        // empty() - 값이 없음을 명시적으로 표현
        Optional<String> opt3 = Optional.empty();


        // 2. 자주 쓰는 핵심 API 메서드

        // 1) 안전하게 값 꺼내기
        // - orElse(T) : 값이 비어있다면 매개변수로 지정한 기본값을 반환. 값이 있든 없든 괄호 안의 로직이 항상 실행
        //               새로운 연산이나 메모리 할당이 필요 없는 고정된 상수나 기 생성된 변수라면 orElse() 써도 무방
        String r1 = opt2.orElse(getDefault());  // 값이 없을 때
        String r2 = opt1.orElse(getDefault());  // 값이 있을 때

        System.out.println(r1); // 값이 없을 때, default
        System.out.println(r2); // 값이 있을 때, hello

        // - orElseGet(fn) : 값이 비어있을 때만 괄호 안의 함수를 실행하여 기본값을 반환 (성능 최적화에 유리)
        //                   DB 조회, 신규 객체 생성, 메서드 호출은 무조건 orElseGet()
        String r3 = opt2.orElseGet(() -> getDefault());     // 값이 없을 때
        String r4 = opt1.orElseGet(() -> getDefault());     // 값이 있을 때, 실행 안됨

        System.out.println(r3);     // default
        System.out.println(r4);     // hello


        // - orElseThorw() : 값이 비어있다면 기본 예외(NoSuchElementException) 또는 지정한 커스텀 예외를 던짐
        try {
            opt3.orElseThrow(() -> new IllegalArgumentException("값이 없습니다"));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage()); // 값이 없습니다
        }


        // 2) 값 존재 여부 확인
        // isPresent(), isEmpty()
        Optional<String> present = Optional.of("Java");
        Optional<String> empty = Optional.empty();

        present.isPresent();
        empty.isPresent();
        empty.isEmpty();

        System.out.println(present.isPresent());    // true
        System.out.println(empty.isPresent());      // false
        System.out.println(empty.isEmpty());        // true  — 값 없음 (Java 11+)


        // 3) map(Function) : 값이 존재하면 다른 값으로 변환
        Optional<String> name = Optional.of("alice");

        // 값이 있으면 변환, 없으면 Optional.empty() 반환
        Optional<Integer> length = name.map(s -> s.length());
        System.out.println(length);     // Optional[5]

        Optional<String> emptyName = Optional.empty();
        Optional<Integer> length2 = emptyName.map(s -> s.length());
        System.out.println(length2);    // Optional.empty()


        // 4) fiatMap : Optional을 반환하는 메서드와 연결
        Optional<String> opt5 = Optional.of("hello");

        // map을 쓰면 Optional<Optional<String>> - 중첩 발생
        Optional<Optional<String>> nested = opt5.map(s -> Optional.of(s.toUpperCase()));
        System.out.println(nested); // Optional[Optional[HELLO]]

        // flatMap을 쓰면 Optional<String> - 중첩 제거
        Optional<String> flat = opt5.flatMap((s -> Optional.of(s.toUpperCase())));
        System.out.println(flat);  // Optional[HELLO]


        // 5) ifPresent() : 값이 있을 때만 실행
        Optional<String> value = Optional.of("Java 21");
        value.ifPresent(s -> System.out.println("값 : " + s));   // 값 : Java 21

        Optional<String> empty2 = Optional.empty();
        empty2.ifPresent(s -> System.out.println("실행안됨"));    // 아무것도 실행안됨


        // 안티 패턴 _ 실무에서 주의해야할 사항들
        // 1. isPresent() 후 get() 호출 지양
        //    값 없으면 NoSuchElementException 발생
        //    isPresent() 확인 없이 쓰면 위험함

        // bad
        if(opt1.isPresent()) {
            String val = opt1.get();
        }

        // good
        opt1.ifPresent(System.out::println);
        String val2 = opt1.orElse(getDefault());

        // 2. 클래스 필드로 선언 - 직렬화 불가, 설계 의도랑 맞지 않음
//        private Optional<String> name;

        // 3. 메서드 파라미터(매개변수) 타입으로 사용금지
        //    파라미터(매개변수)에 Optional을 사용하면, 호출하는 쪽에서 굳이 Optional.of() 형태로 감싸서 넘겨야 하거나
        //    심지어 null을 넘겨버릴 수도 있음. 오히려 복잡성과 에러 확률 증가!
        //    반환 타입(Return)으로만 사용할 것
//        void process(Optional<String> name) {}


    }

    static String getDefault() {
        System.out.println(("getDefault() 실행됨!!"));
        return "default";
    }
}
