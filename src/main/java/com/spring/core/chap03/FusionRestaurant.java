package com.spring.core.chap03;

public class FusionRestaurant implements Restaurant {

    // 셰프
    private Chef chef;
    // 요리코스
    private Course course;

    // FusionRestaurant 내부에서 무슨 Chef와 Course가 들어올지 결정하지 않음
    public FusionRestaurant(Chef chef, Course course){
        this.chef = chef;
        this.course = course;
    }
    @Override
    public void order() {
        System.out.println("여기는 퓨전 레스토랑입니다.");
        course.combineMenu();
        chef.cook();
    }
}
