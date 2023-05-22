package com.spring.core.chap01;

/*
    스프링 의존성 주입이 사용되지 않아 생기는 문제
    호텔 클래스 내부에서 현재 직접 객체를 생성해주는 코드의 경우
    나중에 의존객체를 변경해야 할 때 직접 호텔클래스 내부를 수정해야함.
    이 경우 OCP를 위배하게 됨.
    심지어 Hotel에서 셰프를 변경한다면, 레스토랑 내부에서도 변경해야 하므로
    2군데를 수정해야 함.
 */
public class Hotel {
    // 호텔 내부에 입점한 레스토랑 선언
    private Restaurant restaurant;

    // 수석 셰프
    private Chef chef;

    public Hotel(){
        // 생성자 내부 new 키워드를 이용해 호텔에 입점시킬 식당 종류와 셰프를 선택함
        restaurant = new FusionRestaurant();
        chef = new JapaneseChef();
    }
    // 호텔의 정보를 알려주기
    public void inform(){
        String chefName = chef.getClass().getSimpleName();
        String restaurantName = restaurant.getClass().getSimpleName();
        // 안내 멘트
        System.out.println("우리 호텔의 레스토랑은 " +
                                    restaurantName +
                                    "이며, 수석 셰프는 " +
                                    chefName +
                                    "입니다.");
        // 요리가 순서대로 나옴
        restaurant.order();
    }
}
