package com.study.java21;

public class Main {
    // Record
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    // Java 16 정식 도입: Record
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    // Record = 불변(Immutable) 데이터 클래스를 단 한 줄로 선언하는 문법
    //
    // 아래 한 줄이 자동으로 만들어주는 것들:
    //   - private final String name
    //   - private final int age
    //   - 생성자: Person(String name, int age)
    //   - getter: name(), age()  ← Java Bean 방식의 getName()이 아닌 name()
    //   - equals(), hashCode(), toString() 전부 자동 생성
    record Person(String name, int age) {}

    public static void main(String[] args) {
        // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        // Java 10 도입: var (로컬 변수 타입 추론)
        // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        // 컴파일러가 오른쪽 값을 보고 타입을 자동으로 추론
        // 아래 코드는 컴파일 후 실제로 Person 타입으로 확정됨
        // 즉, 런타임 성능에 영향 없음 — 순수하게 타이핑 편의 기능
        var person = new Person("Alice", 30);
        System.out.println(person);

        // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        // Java 21 정식 도입: Pattern Matching for switch
        // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        // 기존 switch는 int, String, enum만 가능했음
        // Java 21부터 Object 타입도 switch로 처리 가능
        //
        // 동작 원리:
        //   1. obj가 Person 타입인지 체크 (instanceof 역할)
        //   2. 맞으면 p라는 변수에 자동으로 캐스팅해서 바인딩
        //   3. when = 추가 조건 (guard pattern) — p.age() >= 20일 때만 매칭
        //   4. 컴파일러가 모든 케이스를 처리했는지 검사 (exhaustiveness)
        //      → default가 없으면 컴파일 에러 날 수 있음
        Object obj = person;
        String result = switch (obj) {
            // "case Person p" = obj가 Person이면 p로 캐스팅
            // "when p.age() >= 20" = 추가 조건 (guard)
            case Person p when p.age() >= 20 -> p.name() + "은 성인";
            // 위 조건에 걸리지 않는 모든 나머지 케이스
            default -> "기타";
        };
        System.out.println(result);

        // Virtual Thread
        // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        // Java 21 정식 도입: Virtual Thread (Project Loom)
        // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        // 기존 Thread(플랫폼 스레드) vs Virtual Thread 차이:
        //
        //   플랫폼 스레드: OS 스레드와 1:1 매핑
        //     → 생성 비용 크고, 메모리 약 1MB씩 차지
        //     → 수천 개 생성하면 메모리 부족 & 컨텍스트 스위칭 비용 폭증
        //
        //   Virtual Thread: JVM이 직접 관리하는 경량 스레드
        //     → 생성 비용 거의 없고, 메모리 수 KB 수준
        //     → 수십만 개 동시 생성 가능
        //     → I/O 대기 중엔 자동으로 OS 스레드를 다른 작업에 양보
        //
        // Thread.ofVirtual() = Virtual Thread 빌더 생성
        // .start(Runnable) = 람다를 Virtual Thread로 즉시 실행
        Thread.ofVirtual().start(() ->
            // Thread.currentThread() = 현재 실행 중인 스레드 정보 출력
            // "VirtualThread[#21]/runnable@ForkJoinPool..." 형태로 출력됨
            // 여기서 "VirtualThread"라는 단어가 보이면 성공
            System.out.println("Virtual Thread : " + Thread.currentThread())
        );

        // 주의: main 스레드가 Virtual Thread보다 먼저 끝날 수 있어서
        // 간혹 Virtual Thread 출력이 안 보일 수 있음
        // 그럴 땐 아래 코드 추가:
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
