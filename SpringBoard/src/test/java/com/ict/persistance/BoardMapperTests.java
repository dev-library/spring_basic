package com.ict.persistance;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ict.domain.BoardVO;
import com.ict.mapper.BoardMapper;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardMapperTests {

	@Autowired
	private BoardMapper boardMapper;
	
	// 테스트용 메서드의 이름은 testGetList입니다.
	// 테스트 코드가 실행될 수 있도록 작성해주세요.
	@Test
	public void testGetList() {
		List<BoardVO> result = boardMapper.getList();
		log.info("저장된 게시물 정보 : " + result);
	}
	
	//@Test
	public void testInsert() {
		//vo를 입력받는 insert 메서드 특성상
		// title, content, writer가 채워진 vo를 먼저 생성해야 합니다.
		BoardVO vo = new BoardVO();
		log.info("채워넣기 전 : " + vo);

		vo.setTitle("테스트로입력하는제목");
		vo.setContent("테스트로입력하는본문");
		vo.setWriter("테스트글쓴이");
		
		log.info("채워넣은 후 : " + vo);
		// vo내부에 데이터가 바인딩된걸 확인했으니 메서드 호출
		boardMapper.insert(vo);
	}
	
	// select 메서드에 대한 테스트 코드 작성
	//@Test
	public void getSelect() {
		// 가져오기(글번호는 두 번쨰로 큰 번호로 해주세요.)
		boardMapper.select(5);
		// 로그 찍기
		//log.info(vo);
	}
	
	
	
}




