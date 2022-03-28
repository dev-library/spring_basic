package com.ict.controller.di.classfile;

import org.springframework.stereotype.Component;

//@Component
// Singer를 상속한 발라드가수와 팝 가수를 생성해서 빈 컨테이너에 등록해보겠습니다.
public class Singer {
	
	// 가수는 무대가 있건 없건 노래를 할 수 있기 때문에
	// 다른 어떤 요소 없이 오직 노래기능만 넣어둡니다.
	
	public void sing() {
		System.out.println("가수가 노래를 합니다.");
	}
}