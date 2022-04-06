package com.ict.persistance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ict.time.mapper.TimeMapper;

import lombok.extern.log4j.Log4j;

// RunWith, ContextConfuguration, Log4j 어노테이션 붙이기
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class TimeMapperTests {

	// 인터페이스는 호출하려면 구현클래스화 해야함.
	// 의존성 주입(@Autowired) 하면 자동으로 마이바티스가 구현해줌
	@Autowired
	private TimeMapper timeMapper;
	
	//@Test
	public void testGetTime() {
		log.info("현재 시간 조회중");
		log.info(timeMapper.getTime());
	}
	
	// testGetTime2() 를 만들어서 timeMapper의 getTime2를 호출해주세요.
	// 시간이 잘 찍히는지 확인해보세요.
	@Test
	public void testGetTime2() {
		log.info("getTime2를 호출했습니다.");
		log.info(timeMapper.getTime2());
	}
	
	
}
