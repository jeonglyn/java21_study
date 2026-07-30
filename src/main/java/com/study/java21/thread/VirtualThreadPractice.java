package com.study.java21.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class VirtualThreadPractice {
    // Virtual Thread
    // 해당 챕터를 공부하기 위해선 스레드라는 개념에 대해 알아야 한다.

    // 스레드(Thread)란?
    // 스레드 = 일을 하는 일꾼 한명 이라고 생각하면 좋다

    // 보통 프로그램이 실행되면 일꾼 1명이 코드를 위에서부터 순서대로 처리한다.
    // 근데 일이 오래 걸리는 작업(DB조회, API호출)이 있으면, 일꾼을 더 고용해서
    // 동시에 여러 일을 시킬 수 있는데, 이것이 멀티 스레드이다.

    // 기존 자바 스레드는 OS 스레드와 1대1로 매핑되었음.
    // 이게 동시 요청이 수만 건이면 스레드 풀이 금방 바닥나는 상황이 생겼음

    // 특히 I/O 대기가 많은 백엔드(DB조회, API 호출)에서는 스레드가 일하는 시간 보다 기다리는 시간이 훨씬 김
    // 근데 기존 자바 스레드는 기다리는 동안에도 OS 스레드 하나를 계속 붙잡고 있었음 -> 자원 낭비

    // 그래서 JAVA21에서 가상 스레드 (Virtual Thread)가 정식 도입
    // JVM이 관리하는 매우 가벼운 스레드를 여러 개 생성할 수 있게 되었음

    public static void main(String[] args) throws Exception {
        // 1. 가상 스레드 기본 생성 방법

        // 기존 방식 (플랫폼 스레드)
        Thread platformThread = new Thread(() -> {
            System.out.println("플랫폼 스레드 실행 : " + Thread.currentThread());
        });
        platformThread.start();

        // 가상 스레드 생성 방식
        // 1) Thread.ofVirtual()
        Thread virtualThread = Thread.ofVirtual().start(() -> {
            System.out.println("가상 스레드 실행 : " + Thread.currentThread());

            // isVirtual()로 확인 가능함
            System.out.println("가상 스레드 여부 : " + Thread.currentThread().isVirtual());
        });

        virtualThread.join();       // 스레드가 끝날때까지 대기


        // 2. ExecutorService로 대량 생성
        // 실무에서 주로 사용하는 방식임

        // 여기서 사용할 Future 및 ExecutorService를 알아보자

        // * Future : 오래 걸리는 비동기 작업의 결과를 나중에 받을 수 있게 도와주는 인터페이스
        //            -> 쉽게 말해 주문 대기표 라고 생각하자

        // * ExecutorService : 스레드 풀을 통해 작업을 비동기적이고 효율적으로 실행할 수 있게 돕는 동시성 프레임워크
        //                      -> 쉽게 말해 일꾼 관리자
        //                      -> 일꾼을 하나하나 new Thread로 직접 만드는 대신, 일꾼 관리자한테 일감만 던져주면
        //                      -> 관리자가 알아서 일꾼을 배정해줌

        // 예시 : 일꾼한테 커피 만들어줘 라고 시킨 (일꾼은 백그라운드에서 일함, 나는 안 기다림)
        // 1) 일꾼 관리자 고용 : 가상 스레드로 일꾼을 필요한 만큼 계속 만들어줘 라는 뜻이다
        ExecutorService master = Executors.newVirtualThreadPerTaskExecutor();

        // 2) 일꾼한테 커피 만들라고 시킴
        Future<String> bulider = master.submit(() -> {
            // 이 안의 코드가 다른 일꾼이 실행하는 부분
            Thread.sleep(1000);    // 커피 만드는데 1초 걸린다고 가정
            return "아메리카노";           // 다 만들면 이걸 진동벨에 저장
        });

        System.out.println("주문 완료, 기다리는 동안 다른 일 할 수 있음");

        // 진짜로 커피가 필요한 시점에 진동벨을 확인 (여기서 커피 나올때까지 대기)
        String coffee = bulider.get();  // "아메리카노"
        System.out.println("커피 받음: " + coffee);

        // .submit()        -> 이 일 좀 해줘 하고 다른 일꾼에게 맡기고, 나는 바로 다음 줄로 넘어감
        // Future<String>   -> 나중에 String 결과가 나올 진동벨
        // .get()

        // 기존 방식 : 고정 크기 스레드 풀 (200개가 한계)
//        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(200);

        // 가상 스레드 방식 : 요청마다 새로운 가상 스레드를 무한으로 생성
        try (ExecutorService virtualExecutor = Executors.newVirtualThreadPerTaskExecutor()) {

            // 10,000개의 작업을 동시에 실행해도 문제 없음
            List<Future<String>> futures = new ArrayList<>();

            for(int i = 0; i < 10_000; i++) {
                int taskId = i;
                Future<String> future = virtualExecutor.submit(() -> {
                    Thread.sleep(100);  //I/O 대기를 흉내냄
                    return "작업 " + taskId + " 완료";
                });
                futures.add(future);
            }

            // 처음 3개만 결과 확인
            for(int i = 0; i < 3; i++) {
                System.out.println(futures.get(i).get());
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        // try-with-resources 블록이 끝나면 executor가 자동으로 close() 되면서
        // 모든 작업이 끝날 때까지 기다려줌



        // 3. 캐리어 스레드 개념 확인
        // 가상 스레드는 소수의 '캐리어 스레드' (보통 CPU 코어 개수만큼) 위에서 번갈아가며 실행된다.
        // Thread.sleep()이나 I/O 블로킹이 걸리면 캐리어 스레드에서
        // 자동으로 떨어져나가고, 그 스레드는 다른 가상 스레드를 실행하는 데 재사용된다

        Thread.ofVirtual().start(() -> {
            try {
                System.out.println("작업 시작 캐리어 스레드에 마운트됨!");
                Thread.sleep(50);   // 이 순간 캐리어 스레드에서 unmount됨
                System.out.println("sleep 끝. 다시 마운트되어 활동 재개!!");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).join();

        // 주의사항!
        // 1) 가상 스레드는 I/O 대기 중 자원 낭비를 줄이는 것이 핵심이지, CPU 연산 자체를 빠르게 해주는 것이 아님!
        // 2) synchronized 블록 안에서는 가상 스레드의 장점이 사라진다.
        // 3) 가상 스레드는 재사용 하면 안된다!
        // 4) 모든 곳에서 가상 스레드를 사용하는 것은 아니다! 커넥션 풀 크기 등으로 동시성이 제한되는 경우엔 가상 스레드는 장점이 없다
    }

}
