package com.study.java21.collection;

import java.util.*;

public class SequencedPractice {
    // SequencedCollection
    // JAVA 21 이전에는 순서가 있는 컬렉션에서 첫번째/마지막 요소를 가져오거나
    // 역순으로 순회하는 방법이 컬렉션 타입마다 제각각이었음

    List<String> list = new ArrayList<>();
    String first = list.get(0);                 // List 방식
    String last = list.get(list.size() - 1);    // List 방식 번거로움

    LinkedHashSet<String> set = new LinkedHashSet<>();
    // Set은 get(index)가 아예 없다... 첫/마지막 요소 꺼내려면
    // Iterator를 직접 돌리거나 stream().findFirst() 써야 함

    // 하지만 JAVA21 에서 SequencedCollection, SequencedSet, SequencedMap 인터페이스가 도입되면서
    // List, Set(LinkedHashSet), Deque가 동일한 방식(getFirst(), getLast(), reversed())로 사용할 수 있게 되었다.

    public static void main(String[] args) {
        // 1. List에서 SequencedCollection 사용
        List<String> fruites = new ArrayList<>(List.of("사과", "바나나", "체리"));

        // 기존 방식
        String oldFirst = fruites.get(0);
        String oldLast = fruites.get(fruites.size() - 1);

        // SequencedCollection 방식
        String newFirst = fruites.getFirst();
        String newLast = fruites.getLast();

        System.out.println(oldFirst + " " + oldLast + " " + newFirst + " " + newLast);   // 사과 체리 사과 체리


        // addFirst / addLast  : 앞뒤에 요소 추가
        fruites.addFirst("딸기");     // [딸기, 사과, 바나나, 체리]
        fruites.addLast("포도");      // [딸기, 사과, 바나나, 체리, 포도]
        System.out.println("추가 후 : " + fruites);            // 추가 후 : [딸기, 사과, 바나나, 체리, 포도]

        // reversed() : 역순 뷰(view)를 반환 - 복사본이 아니라 뒤집힌 창
        List<String> reversedFruites = fruites.reversed();
        System.out.println(reversedFruites);        // [포도, 체리, 바나나, 사과, 딸기]


        // 2. LinkedHashSet에서 SequencedCollection 사용
        // HashSet은 순서 보장이 안되므로 SequencedCollection이 아님
        // 반드시 LinkedHashSet(삽입 순서 유지)를 사용해야 순서 개념이 의미 있다!

        LinkedHashSet<String> visitedPage = new LinkedHashSet<>();
        visitedPage.add("메인");
        visitedPage.add("상품목록");
        visitedPage.add("상세페이지");

        // 기존 방식 : Set은 get(index)가 없어서 아래와 같이 꺼내야 했다
        String oldFirstPage = visitedPage.iterator().next();

        // SequencedCollection 방식 사용
        String newFirstPage = visitedPage.getFirst();
        String newLastPage = visitedPage.getLast();

        System.out.println("최초 방문 페이지 : " + newFirstPage);  // 최초 방문 페이지 : 메인
        System.out.println("최근 방문 페이지 : " + newLastPage);   // 최근 방문 페이지 : 상세페이지

        // 3. SequencedMap - LinkedHashMap이 구현
        SequencedMap<String, Integer> scoreMap = new LinkedHashMap<>();
        scoreMap.put("국어", 90);
        scoreMap.put("수학", 50);
        scoreMap.put("영어", 70);

        // 첫 번째 / 마지막 엔트리를 Map.Entry로 꺼낼 수 있음
        Map.Entry<String, Integer> firstEntry = scoreMap.firstEntry();
        Map.Entry<String, Integer> lastEntry = scoreMap.lastEntry();

        System.out.println("첫 과목 : " + firstEntry.getKey() + " = " + firstEntry.getValue());
        System.out.println("마지막 과목 : " + lastEntry.getKey() + " = " + lastEntry.getValue());

        // 키 목록을 역순으로 조회할 수 도 있다
        System.out.println("입력 순서 : " + scoreMap.sequencedKeySet());
        System.out.println("역순 : " + scoreMap.reversed());

        // sequencedKeySet      : 키만 뽑기
        // sequencedValues      : 값만 뽑기
        // sequencedEntrySet    : 키+값 둘다 뽑기


    }
}
