package com.study.java21.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamPractice {
    public static void main(String[] args) {
        // Java Stream API는 컬렉션(배열, 리스트 등)의 저장 요소를 하나씩 참조하여
        // 람다식으로 연산 처리할 수 있도록 돕는 함수형 스타일의 소스 처리 라이브러리

        // 기존 FOR, while 루프 방식의 외부 반복을 지양하고, 내부 반복을 통해 코드 가독성을 극대화하여
        // 데이터 처리 흐름을 직관적으로 보여줌

        // 1. Stream의 핵심 3단계 파이프라인
        // 최종 연산이 호출되기 전까지 중간 연산은 실행되지 않음
        // [데이터 소스]      ---->   [중간 연산] ----> [중간 연산] ----> [최종 연산]
        // (List, Array 등)          (filter)          (map)          (collect)

        // 1) 스트림 생성 : 데이터 소스(리스트, 배열 등)로부터 스트림을 만듦
        // 2) 중간 연산 : 데이터를 가공하여 반환 타입이 Stream이므로 계속 이어서 연결할 수 있음
        // 3) 최종 연산 : 스트림을 소비하여 결과를 만들어냄. 최종 연산이 수행되면 스트림은 닫히고 재사용할 수 없음

        // 기본 파이프라인 예시
        List<String> names = List.of("Alice", "Bob", "Charlie", "David", "Eve");

        List<String> result = names.stream()
                // 중간 연산 - 조건에 맞는 요소만 통화 (lazy)
                .filter(name -> name.length() > 2)  // 글자수가 2글자 이상인
                // 중간 연산 - 각 요소를 변환 (lazy)
                .map(name -> name.toUpperCase())    // 문자열을 소문자에서 대문자로 변환 또는 String::toUpperCase()
                // 중간 연산 - 정렬 (lazy)
                .sorted()
                // 최종 연산 - 여기서 이제 중간 연산들이 실행됨
                .collect(Collectors.toList());

        System.out.println(result);         // [ALICE, BOB, CHARLIE, DAVID, EVE]


        // 2. FOR문 VS Stream 비교

        // 기존 For-each 문
        // 코드가 길고, 중간 결과를 저장할 임시 리스트를 직접 관리해야함

        List<String> names2 = List.of("Alice", "Bob", "Charlie", "David", "Eve");

        // 1) 임시 리스트를 직접 만들어야함
        List<String> result2 = new ArrayList<>();

        // 2) 조건 검사
        for(String name : names2) {
            if(name.length() > 4) {
                result2.add(name);
            }
        }

        // 3) 변환을 위한 또 다른 임시 리스트
        List<String> result3 = new ArrayList<>();
        for (String name : result2) {
            result3.add(name.toUpperCase());
        }

        System.out.println(result3);        // [ALICE, BOB, CHARLIE, DAVID]


        // Stream API
        // 무엇을 해야 할지만 정의하면 되니 직관적이고 간결하다

        List<String> names3 = List.of("Alice", "Bob", "Charlie", "David", "Eve");

        List<String> result4 = names.stream()                                       // 스트림 생성
                                    .filter(name -> name.length() > 4)        // 중간 연산 : 길이 4초과만
                                    .map(name -> name.toUpperCase())          // 중간 연산 : 대문자 변환
                                    .collect(Collectors.toList());                  // 최종 연산 : 리스트로 수집

        System.out.println(result4);        // [ALICE, BOB, CHARLIE, DAVID]


        // 3. 자주 쓰는 핵심 API 메서드
        // 1) 중간 연산
        // filter - 조건에 맞는 요소만 통과
        List<Integer> numbers = List.of(1,2,3,4,5,6,7,8,9,10);

        List<Integer> filterResult = numbers.stream()
                                            .filter(n -> n % 2 == 0)    // 짝수만 통과
                                            .collect(Collectors.toList());

        System.out.println(filterResult);   // [2, 4, 6, 8, 10]


        // map - 요소를 다른 값으로 변환
        List<String> names4 = List.of("alice", "bob", "jack");

        List<String> mapResult = names4.stream()
                                        .map(name -> name.toUpperCase())    // 소문자 -> 대문자로 변환
                                        .collect(Collectors.toList());

        System.out.println(mapResult);      // [ALICE, BOB, JACK]

        // sorted - 정렬
        List<Integer> sortedResult = List.of(10, 5, 2, 1, 3);

        // 오름차순
        List<Integer> ascending = sortedResult.stream()
                                                .sorted()
                                                .collect(Collectors.toList());

        System.out.println(ascending);      // [1, 2, 3, 5, 10]

        // 내림차순
        List<Integer> descending = sortedResult.stream()
                                                .sorted((a, b) -> b - a)
                                                .collect(Collectors.toList());

        System.out.println(descending);     // [10, 5, 3, 2, 1

        // distinct - 중복제거
        List<Integer> distinct = List.of(1, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 5);

        List<Integer> distinctResult = distinct.stream()
                                                .distinct()
                                                .collect(Collectors.toList());

        System.out.println(distinctResult); // [1, 2, 3, 4, 5]

        // limit - 앞에서 n개만 자르기
        List<Integer> limit = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        List<Integer> limitResult = limit.stream()
                .limit(5)
                .collect(Collectors.toList());

        System.out.println(limitResult);    // [1, 2, 3, 4, 5]


        // 2) 최종연산
        // collect - 결과를 컬렉션으로 변환
        List<String> names5 = List.of("alice", "bob", "jack");

        // 리스트로 수집
        List<String> list = names5.stream().collect(Collectors.toList());

        System.out.println(list);           // [alice, bob, jack]

        // 문자열로 이어 붙이기
        String joined = names5.stream().collect(Collectors.joining(", "));

        System.out.println(joined);         // alice, bob, jack

        // 그룹으로 묶기
        Map<Integer, List<String>> grouped = names5.stream().collect(Collectors.groupingBy(s -> s.length()));

        System.out.println(grouped);        // {3=[bob], 4=[jack], 5=[alice]}


        // forEach - 각 요소를 순회하며 실행
        List<String> names6 = List.of("alice", "bob", "jack");

        // 각 이름을 출력
        names6.stream().forEach(name6 -> System.out.println(name6));

        // 메서드 참조로 더 줄이기
        names6.stream().forEach(System.out::println);


        // count - 요소 개수 반환
        List<String> names7 = List.of("alice", "bob", "jack");

        long count = names7.stream().filter(name -> name.length() > 3).count();

        System.out.println("길이 3 초과 개수: " + count);  // 2


        // anyMatch - 조건에 맞는 요소가 하나라도 있는지 확인 (있으면 true)
        List<String> names8 = List.of("alice", "bob", "jack");

        boolean hasShortName = names8.stream().anyMatch(name -> name.length() <= 3);

        System.out.println("짧은 이름 있음: " + hasShortName);    // true


        // reduce - 모든 요소를 하나의 값으로 환산
        List<Integer> numbers2 = List.of(1,2,3,4,5);

        int sum = numbers2.stream().reduce(0, (누적값, 현재요소) -> 누적값 + 현재요소);
        // 여기서 0은 초기값

        System.out.println("누적 합계: " + sum);    // 15


        // 4. 자주 쓰는 Collectors

        // joining - 문자열 연결
        String joined2 = names.stream().collect(Collectors.joining(", ", "[", "]"));

        System.out.println("조인: " + joined2);

        // groupingBy - 조건으로 그룹핑
        Map<Integer, List<String>> groupedByLength = names.stream().collect(Collectors.groupingBy(String::length));

        System.out.println("길이별 그룹: " + groupedByLength);

        // counting - 그룹별 개수
        Map<Integer, Long> countByLength = names.stream().collect(Collectors.groupingBy(String::length, Collectors.counting()));

        System.out.println("길이별 개수: " + countByLength);

        // 4. flatMap - 중첩 구조 펼치기 (중복 제거는 x)
        List<List<Integer>> nested = List.of(List.of(1,2,3), List.of(4,5), List.of(6,7,8,9));

        // map은 Stream<List<Integer>> 반환 -> 중첩 구조 유지
        // flatMap은 각 List를 풀어서 Stream<Integer>로 반환 -> 1차원으로 펼침
        List<Integer> flat = nested.stream().flatMap(List::stream).collect(Collectors.toList());

        System.out.println("flatMap 결과: " + flat);

        // 5. 스트림 사용시 주의사항

        // 원본 데이터 불변
        // 스트림은 원본 리스트를 절대 변경하지 않음
        // 가동된 결과만 새로 만들어짐
        List<String> map = List.of("alice", "bob");

        List<String> upper = map.stream().map(String::toUpperCase).collect(Collectors.toList());

        System.out.println(map);        // [alice, bob]
        System.out.println(upper);      // [ALICE, BOB]

        // Stream 재사용
        // Stream은 한번 소비하면 끝 -> 두번쓰면 IllegalStateException
        // Stream<String> stream = names.stream();
        // stream.forEach(System.out::println);
        // stream.forEach(System.out::println);     // IllegalStateException 발생

        // 필요하면 매번 새로 생성
        names.stream().forEach(System.out::println);
        names.stream().forEach(System.out::println);    // 새로운 Stream이라 OK

        // 단순 반복에 Stream 남용
        // 가독성만 떨어지고 얻는게 없음
        // names.stream().forEach(System.out::println);  // 굳이?

        // 단순 반복은 for-each가 더 명확함
        for(String name : names) {
            System.out.println(name);
        }
    }
}
