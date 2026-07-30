package com.study.java21.migration;

public class MigrationChecklist {
    // 지금까지 배운 걸 하나로 묶어보는 마지막 챕터


    // DTO/VO 클래스가 getter/setter/equals/hashCode를 손으로 다 구현하고 있는가?
    //   → Record로 바꿀 수 있는지 검토

    // instanceof + 캐스팅이 반복되는 분기 로직이 있는가?
    //   → Pattern Matching (instanceof 또는 switch)로 정리 가능한지 검토

    // 타입 계층이 있는데 자식 클래스가 통제 안 되는 구조인가?
    //   → sealed로 제한해서 exhaustiveness 이점을 가져갈 수 있는지 검토

    // 여러 줄 문자열(JSON, SQL)을 문자열 이어붙이기(+)로 만들고 있는가?
    //   → Text Block으로 가독성 개선 가능한지 검토

    // List/Set 첫/마지막 요소를 다루는 방식이 컬렉션 타입마다 제각각인가?
    //   → SequencedCollection으로 통일 가능한지 검토

    // I/O 대기가 많은 작업에 스레드 풀 크기 제한 때문에 병목이 있는가?
    //   → Virtual Thread 도입 검토 (단, CPU 집중 작업엔 효과 없음 주의)

    // null 체크가 여러 겹 중첩되어 있는가?
    //   → Optional의 map/flatMap 체이닝으로 정리 가능한지 검토
}
