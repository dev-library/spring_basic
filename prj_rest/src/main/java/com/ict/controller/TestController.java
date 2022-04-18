package com.ict.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.domain.TestVO;

@RestController
// 클래스 위에 붙인 RequestMapping은 해당 컨트롤러의 공통된 진입주소를 설정해줍니다.
@RequestMapping("/test")
public class TestController {

	@RequestMapping("/hello")
	public String sayHello() {
		return "Hello Hello";
	}
	
	@RequestMapping("/sendVO")
	public TestVO sendTestVO() {
		TestVO testVO = new TestVO();
		
		testVO.setName("채종훈");
		testVO.setAge(21);
		testVO.setMno(1);
		return testVO;
	}
	
	@RequestMapping("/sendVOList")
	public List<TestVO> sendVoList(){
		List<TestVO> list = new ArrayList<>();
		for(int i = 0; i < 10; i++) {
			TestVO vo = new TestVO();
			vo.setMno(i);
			vo.setName(i + "종훈");
			vo.setAge(20 + i);
			list.add(vo);
		}
		return list;
	}
	
	@RequestMapping("/sendMap")
	public Map<Integer, TestVO> sendMap(){
		Map<Integer, TestVO> map = new HashMap<>();
		
		for(int i = 0; i < 10; i++) {
			TestVO vo = new TestVO();
			vo.setName("채종훈");
			vo.setMno(i);
			vo.setAge(50 + i);
			map.put(i, vo);
		}
		return map;
	}
	
	@RequestMapping("/sendErrorAuth")
	public ResponseEntity<Void> sendListAuth(){
		//ResponseEntity<Void> result = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		// return result;
		return // 익명클래스로 처리해도 상관없음. 
			new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping("/sendErrorNot")
	public ResponseEntity<List<TestVO>> sendListNot(){
		List<TestVO> list = new ArrayList<>();
		for(int i = 0; i < 10; i++) {
			TestVO vo = new TestVO();
			vo.setMno(i);
			vo.setName(i + "종훈");
			vo.setAge(20 + i);
			list.add(vo);
		}
		return 
			new ResponseEntity<List<TestVO>>(list, HttpStatus.NOT_FOUND);
	}
	
		
}










