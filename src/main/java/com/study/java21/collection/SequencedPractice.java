package com.study.java21.collection;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

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
}
