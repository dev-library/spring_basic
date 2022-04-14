package com.ict.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.domain.BoardVO;
import com.ict.domain.Criteria;
import com.ict.mapper.BoardMapper;

// BoardService 인터페이스 구현
@Service // 빈 컨테이너에 등록(root-context.xml에서 컴포넌트 스캔까지 완료해야 등록됨)
public class BoardServiceImpl implements BoardService {

	// 서비스가 DAO(Mapper.java)를 호출한다면 선언을 하고 의존성주입을 해야합니다.
	// 해당 코드를 작성해주세요.(BoardController.java를 참조하세요.)
	// 전체 회원을 보려면, 회원목록을 들고오는 메서드를 실행해야 하고
	// 그러면, 그 메서드를 보유하고 있는 클래스를 선언하고 주입해줘야 합니다.
	// DB 접근시 사용하는 BoardMapper를 선언하고 주입해주세요.
	// 참고) BoardMapperTests.java
	@Autowired
	private BoardMapper boardMapper;
	
	// 리턴자료형이 없는 insert, delete, update 구문은 사용자 행동 기준으로 메서드를 나눕니다.
	// 리턴자료형이 있는 select 구문은 하나의 메서드가 하나의 쿼리문을 담당합니다.
	@Override
	public List<BoardVO> getList(Criteria cri) {
		return boardMapper.getList(cri);
	}

	@Override
	public int countPageNum() {
		return boardMapper.countPageNum();
	}

	@Override
	public BoardVO select(long bno) {
		return boardMapper.select(bno);
	}

	@Override
	public void insert(BoardVO vo) {
		boardMapper.insert(vo);
	}

	@Override
	public void delete(long bno) {
		// mapper를 이용해 구현
		boardMapper.delete(bno);
	}

	@Override
	public void update(BoardVO vo) {
		boardMapper.update(vo);
	}



	
	
	
	
	
	
	
}
