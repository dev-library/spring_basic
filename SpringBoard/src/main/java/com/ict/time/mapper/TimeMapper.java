package com.ict.time.mapper;

import org.apache.ibatis.annotations.Select;

public interface TimeMapper {
	// 마이바티스는 인터페이스 파일에 메서드를 선언만 하고(실행문 정의 하지 않음)
	// 실행할 쿼리문을 해당 메서드에 연결하면 세팅이 끝납니다.

	// 1. 어노테이션을 사용해 해당 메서드 호출시 DB에 전달할 쿼리문을 작성
	@Select("SELECT SYSDATE from dual")
	public String getTime();
	
	// 2. 메서드만 만들어 놓고 외부 xml파일과 연동해 해당 파일에 쿼리문 작성(주로 쓰는 방법)
	public String getTime2();
}
