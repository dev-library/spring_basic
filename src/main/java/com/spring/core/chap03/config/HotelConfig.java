package com.spring.core.chap03.config;

import com.spring.core.chap03.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// HotelConfig클래스가 주입에 필요한 객체들을 관리할것임을 명시
@Configuration
public class HotelConfig {

    // 빈 등록 : 스프링이 관리할 객체를 알려주는 작업
    // 방법 : @Bean 어노테이션을 생성코드 위에 붙여줌
    @Bean
    public Chef chef(){
        return new KoreanChef();
    }

    @Bean
    public Course koreanCourse(){
        return new KoreanCourse();
    }

    @Bean
    public Restaurant restaurant(){
        return new FusionRestaurant(chef(), koreanCourse());
    }

    @Bean
    public Hotel hotel(){
        return new Hotel(restaurant(), chef());
    }
}
