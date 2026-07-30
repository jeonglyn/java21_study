package com.study.java21.collection;

import java.util.*;
import java.util.LinkedHashSet;
import java.util.SequencedCollection;
import java.util.stream.Collectors;

public class RecentProductViewer {
    //  요구사항
    //
    //  시나리오: 쇼핑몰에서 사용자가 "최근 본 상품"을 관리하는 기능을 만듭니다.
    //
    //  LinkedHashSet 또는 SequencedMap을 활용해 최근 본 상품을 순서대로 저장
    //  최대 5개까지만 유지 (초과 시 가장 오래된 것부터 제거)
    //  이미 본 상품을 다시 보면 → 기존 기록 제거 후 맨 뒤(최신)로 재등록
    //  아래 기능 구현
    //  viewProduct(String productName) : 상품 조회 기록 추가
    //  getMostRecentlyViewed() : 가장 최근 본 상품 반환
    //  getFirstViewed() : 가장 오래된(가장 먼저 본) 상품 반환
    //  getHistoryNewestFirst() : 최신순으로 정렬된 전체 목록 반환 (reversed() 활용)
    //  getHistoryOldestFirst() : 오래된 순 전체 목록 반환
    //  TODO만 채우면 됨


    private static final int MAX_HISTORY = 5;

    // TODO 1: 순서가 있고 + 중복 제거가 되는 자료구조를 필드로 선언하세요
    // 힌트: 상품 "이름"만 저장한다고 가정합니다 (중복 조회 시 재정렬 필요)
    private final SequencedCollection<String> history;

    public RecentProductViewer() {
        this.history = new LinkedHashSet<>();
    }

    /**
     * 상품을 조회했을 때 호출.
     * - 이미 목록에 있으면 제거 후 맨 뒤(최신)로 다시 추가
     * - 목록에 없으면 그냥 맨 뒤에 추가
     * - 추가 후 MAX_HISTORY(5개)를 초과하면 가장 오래된 것 제거
     */
    public void viewProduct(String productName) {
        // TODO 2: 구현 → ✅ 정답. remove → addLast → 초과 시 removeFirst 순서 정확함
        history.remove(productName);

        history.addLast(productName);

        if(history.size() > MAX_HISTORY) {
            history.removeFirst();
        }
    }

    /** 가장 최근에 본 상품 반환. 기록이 없으면 null */
    public String getMostRecentlyViewed() {
        // TODO 3: ✅ 정답.
        // 다만 참고: history가 비어있으면 getLast()에서 NoSuchElementException이 먼저 터지므로
        //          아래 null/isBlank 체크는 사실상 도달하지 못함 (지금 예제는 항상 데이터가 있어서 문제 안 됨)
        //          완전하게 하려면 if (history.isEmpty()) return null; 을 getLast() 호출 "전"에 둬야 함
        String last = history.getLast();

        if(last == null || last.isBlank()) {
            return null;
        }

        return last;
    }

    /** 가장 먼저(오래) 본 상품 반환. 기록이 없으면 null */
    public String getFirstViewed() {
        // TODO 4: ✅ 정답. (TODO 3과 동일한 참고사항 적용됨 - getFirst()도 빈 컬렉션이면 예외 발생)
        String first = history.getFirst();

        if(first == null || first.isBlank()) {
            return null;
        }
        return first;
    }

    /** 최신순(최근 본 것부터) 리스트 반환 */
    public List<String> getHistoryNewestFirst() {
        // TODO 5: ✅ 정답. new ArrayList<>(history.reversed())로 뷰를 실제 리스트로 복사함
        List<String> reversed = new ArrayList<>(history.reversed());

        if(reversed.isEmpty()) {
            return null;
        }

        return reversed;
    }

    /** 오래된 순(먼저 본 것부터) 리스트 반환 */
    public List<String> getHistoryOldestFirst() {
        // TODO 6: ✅ 정답. new ArrayList<>(history)로 원본 순서 그대로 복사함
        List<String> oldest = new ArrayList<>(history);

        if(oldest.isEmpty()) {
            return null;
        }

        return oldest;
    }

    public static void main(String[] args) {
        RecentProductViewer viewer = new RecentProductViewer();

        viewer.viewProduct("노트북");
        viewer.viewProduct("마우스");
        viewer.viewProduct("키보드");
        viewer.viewProduct("모니터");
        viewer.viewProduct("노트북");   // 재조회 → 맨 뒤로 이동해야 함
        viewer.viewProduct("헤드셋");
        viewer.viewProduct("의자");     // 6번째 → 가장 오래된 것 제거되어야 함

        System.out.println("최근순: " + viewer.getHistoryNewestFirst());
        System.out.println("오래된순: " + viewer.getHistoryOldestFirst());
        System.out.println("가장 최근: " + viewer.getMostRecentlyViewed());
        System.out.println("가장 오래된: " + viewer.getFirstViewed());
    }


}
