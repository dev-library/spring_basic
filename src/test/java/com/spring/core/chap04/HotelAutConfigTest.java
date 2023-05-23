package com.spring.core.chap04;

import com.spring.core.chap04.config.HotelAutoConfig;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class HotelAutConfigTest {

    // 빈 컨테이너 생성에 앞서서 설정파일 실행
    AnnotationConfigApplicationContext ac =
            new AnnotationConfigApplicationContext(HotelAutoConfig.class);

    @Test
    void autoDITest(){
        Hotel hotel = ac.getBean(Hotel.class);
        hotel.inform();
    }
}
