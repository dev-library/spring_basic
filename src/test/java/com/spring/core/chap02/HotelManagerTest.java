package com.spring.core.chap02;

import com.spring.core.chap02.config.HotelManager;
import org.junit.jupiter.api.Test;

public class HotelManagerTest {

    @Test
    public void diTest(){
        // 호텔 매니저 객체 생성
        HotelManager manager = new HotelManager();
        // 매니저 객체의 hotel()을 호출하면 내부적으로 Hotel을 조립해서 리턴
        Hotel hotel = manager.hotel();
        // 완성된 hotel객체의 inform() 호출
        hotel.inform();
    }
}
