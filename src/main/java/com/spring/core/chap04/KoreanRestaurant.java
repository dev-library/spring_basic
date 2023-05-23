package com.spring.core.chap04;

public class KoreanRestaurant implements Restaurant {
    // 셰프도 레스토랑 소속
    private Chef chef;

    // 코스요리 메뉴도 레스토랑 소속
    private Course course;

    public KoreanRestaurant(){
        this.chef = new KoreanChef();
        this.course = new KoreanCourse();
    }

    @Override
    public void order() {
        System.out.println("한식을 주문합니다.");
        course.combineMenu();
        chef.cook();
    }
}
