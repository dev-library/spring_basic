package com.spring.core.chap04;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("fs")
public class FusionRestaurant implements Restaurant {

    // 셰프
    @Autowired // 컨테이너에 든 자료중 Chef에 대입할 수 있는걸 자동으로 집어넣어라.
    private Chef chef;
    // 요리코스
    @Autowired
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
