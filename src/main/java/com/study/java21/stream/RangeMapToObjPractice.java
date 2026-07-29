package com.study.java21.stream;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RangeMapToObjPractice {
    public static void main(String[] args) {
        // InStream.range(start, end) - 숫자 스트림 생성
        // start부터 end-1까지의 int 값을 스트림으로 만들어줌 (end는 미포함)
        // 즉, for (int i = 0; i < names.size(); i++)와 똑같은 범위임

        List<String> names = List.of("아메리카노", "크루아상", "라떼");

        // 기본 range
        IntStream.range(0, names.size())
                .forEach(i -> System.out.println("인덱스: " + i));


        // rangeClosed(start, end)는 end를 포함함
        IntStream.rangeClosed(0, 3)
                .forEach(i -> System.out.println(i + " "));


        // 왜 InStream.range가 필요한가?
        // 리스트 하나만 순회한다면 한줄로 작성이 가능함
        names.stream().forEach(name -> System.out.println(name));

        // 하지만 인덱스가 필요한 경우, 예를 들자면 서로 다른 리스트를 같은 i로 꺼내야 하는 경우
        // 이럴 땐 값이 아니라 인덱스를 스트림으로 돌려야 한다
        // 그래서 InStream.range로 인덱스 스트림을 만드는 것!


        // mapToObj() 는 int를 객체(String, 커스텀 타입 등)으로 변환한다
        // IntStream은 int만 다루는 스트림이라 map이 아니라 mapToObj()를 사용해야함!
        List<String> indexedNames = IntStream.range(0, names.size())
                .mapToObj(i -> (i+1) + "번: " + names.get(i))
                // mapToObj: int i 를 받아서 -> String 객체로 변환
                .collect(Collectors.toList());

        indexedNames.forEach(name -> System.out.println(name));
    }
}
