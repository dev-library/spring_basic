package com.ict.controller.di.classfile;

import org.springframework.stereotype.Component;

@Component
public class BalladSinger extends Singer {

	// Singer를 상속하고 sing()을 오버라이딩해서
	// "발라드 가수가 소몰이 창법으로 노래를 합니다."
	// 를 콘솔에 찍도록 설정해줍니다.
	// 그리고 빈 컨테이너에 등록해주세요.
	@Override
	public void sing() {
		System.out.println("발라드 가수가 소몰이 창법으로 노래를 합니다.");
	}
	
	
}