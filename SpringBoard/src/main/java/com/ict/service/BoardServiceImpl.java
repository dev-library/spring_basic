package com.ict.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ict.domain.BoardAttachVO;
import com.ict.domain.BoardVO;
import com.ict.domain.Criteria;
import com.ict.domain.SearchCriteria;
import com.ict.mapper.BoardAttachMapper;
import com.ict.mapper.BoardMapper;
import com.ict.mapper.ReplyMapper;

import lombok.extern.log4j.Log4j;

// BoardService 인터페이스 구현
@Service // 빈 컨테이너에 등록(root-context.xml에서 컴포넌트 스캔까지 완료해야 등록됨)
@Log4j
public class BoardServiceImpl implements BoardService {

	// 서비스가 DAO(Mapper.java)를 호출한다면 선언을 하고 의존성주입을 해야합니다.
	// 해당 코드를 작성해주세요.(BoardController.java를 참조하세요.)
	// 전체 회원을 보려면, 회원목록을 들고오는 메서드를 실행해야 하고
	// 그러면, 그 메서드를 보유하고 있는 클래스를 선언하고 주입해줘야 합니다.
	// DB 접근시 사용하는 BoardMapper를 선언하고 주입해주세요.
	// 참고) BoardMapperTests.java
	@Autowired
	private BoardMapper boardMapper;
	
	// 글 삭제시 해당 글의 전체 댓글을 삭제하기 위해 reply_tbl 생성
	@Autowired
	private ReplyMapper replyMapper;
	
	@Autowired
	private BoardAttachMapper attachMapper;
	
	// 리턴자료형이 없는 insert, delete, update 구문은 사용자 행동 기준으로 메서드를 나눕니다.
	// 리턴자료형이 있는 select 구문은 하나의 메서드가 하나의 쿼리문을 담당합니다.
	@Override
	public List<BoardVO> getList(SearchCriteria cri) {
		return boardMapper.getList(cri);
	}

	@Override
	public int countPageNum(SearchCriteria cri) {
		return boardMapper.countPageNum(cri);
	}

	@Override
	public BoardVO select(long bno) {
		return boardMapper.select(bno);
	}

	@Transactional
	@Override
	public void insert(BoardVO vo) {
		log.info("이번에 새로 쓰는 글번호 : " + vo.getBno());
		boardMapper.insert(vo);
		
		if(vo.getAttachList() == null || vo.getAttachList().size() <= 0) {
			return;
		}
		
		vo.getAttachList().forEach(attach -> {
			attach.setBno(vo.getBno());
			attachMapper.insert(attach);
		});
	}

	@Transactional
	@Override
	public void delete(long bno) {
		// 댓글먼저 삭제 후
		replyMapper.deleteAllReplies(bno);
		// mapper를 이용해 구현
		// 글 마저 삭제(댓글이 없는 글만 삭제가 가능하므로 위의 순서대로 작성)
		boardMapper.delete(bno);
	}

	@Override
	public void update(BoardVO vo) {
		boardMapper.update(vo);
	}

	@Override
	public List<BoardAttachVO> getAttachList(Long bno) {
		return attachMapper.findByBno(bno);
	}

	
	
}
