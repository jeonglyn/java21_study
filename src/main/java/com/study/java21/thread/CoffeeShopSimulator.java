package com.study.java21.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CoffeeShopSimulator {
    // 시나리오 : 카페에 손님 5명이 동시에 주문을 함.
    //           각 주문은 커피를 만드는 데 시간이 좀 걸리는 작업이라 가정

    // 요구사항
    // 1) Executors.newVirtualThreadPerTaskExecutor() 로 "일꾼 관리자" 생성
    // 2) 손님 5명의 주문을 각각 submit() 으로 맡기기 → 각 주문은 Future<String> (진동벨) 반환
    // 3) Thread.sleep() 으로 커피 만드는 시간(1초) 흉내내기
    // 4) 모든 주문의 결과를 .get() 으로 받아서 출력
    // 5) 전체 5잔을 순차로 만들면 5초 걸리지만, 가상 스레드로 동시에 처리하면 약 1초 만에 끝나는 것을 시간 측정으로 직접 확인


    public static void main(String[] args) throws Exception {

        String[] customers = {"철수", "영희", "민수", "지훈", "수빈"};

        // TODO 1: 일꾼 관리자(ExecutorService) 생성
        // 힌트: 가상 스레드를 작업마다 새로 만들어주는 팩토리 메서드를 쓰세요
        try (ExecutorService manager = Executors.newVirtualThreadPerTaskExecutor()) {
            /* TODO */
            // 시작 시간 기록 (전체 걸린 시간을 재기 위함)
            long startTime = System.currentTimeMillis();

            // TODO 2: 진동벨(Future)을 담아둘 리스트 준비
            List<Future<String>> tickets = new ArrayList<>();

            // TODO 3: 손님 5명 각각의 주문을 manager에게 맡기기 (submit)
            // - 각 주문은 "손님 이름 + 커피 만드는 중..." 을 출력하고
            // - 1초(1000ms) 대기 후
            // - "{이름}님 커피 완성!" 문자열을 반환해야 함
            for (String customer : customers) {
                /* TODO: manager.submit(...) 작성 */
                Future<String> ticket = manager.submit(() -> {
                    System.out.println(customer + " 커피 만드는 중...");
                    Thread.sleep(1000);
                    return customer + "님 커피 완성!";

                });
                tickets.add(ticket);
            }

            // TODO 4: 모든 진동벨을 확인(.get())해서 결과 출력
            for (Future<String> ticket : tickets) {
                String result = ticket.get();/* TODO */;
                System.out.println(result);
            }

            // TODO 5: manager 종료 (close)
            long endTime = System.currentTimeMillis();
//            manager.close();
            System.out.println("전체 소요 시간: " + (endTime - startTime) + "ms");
            // 예상: 약 1000ms 근처 (5초가 아니라!)
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


}
