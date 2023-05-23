package com.spring.core.chap03;

public class KoreanCourse implements Course {

    @Override
    public void combineMenu() {
        System.out.println("------ 한정식 메뉴 구성 ------");
        System.out.println("1. 흑임자 미음");
        System.out.println("2. 전분을 짜낸 감자전");
        System.out.println("3. 구절판");
        System.out.println("4. 갈비구이");
        System.out.println("5. 병어찜");
        System.out.println("6. 동치미국수");
        System.out.println("7. 약과와 식혜");
    }
}
