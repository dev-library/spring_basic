package com.ict.service;

import java.util.List;

import com.ict.domain.BoardAttachVO;
import com.ict.domain.BoardVO;
import com.ict.domain.Criteria;
import com.ict.domain.SearchCriteria;

// 구현 클래스 BoardServiceImpl의 뼈대가 됩니다.
public interface BoardService {

	// 인터페이스 내에 먼저 메서드를 선언하고, impl 클래스에서 구현합니다.
	public List<BoardVO> getList(SearchCriteria cri);
	
	// 그나마 쉽게 하는 방법 : BoardMapper.java에 있는거
	// 그대로 복붙한다.
	public int countPageNum(SearchCriteria cri);
	
	// 글 하나만 가져오는 로직이었고 복붙
	public BoardVO select(long bno);
	
	// 글 삽입 로직 역시 복붙
	public void insert(BoardVO vo);

	// Mapper에서 삭제로직 선언부 가져오기. 
	public void delete(long bno);
	
	// Mapper에서 수정로직 선언부 가져오기.
	public void update(BoardVO vo);	
	
	// 게시물에 연동된 첨부파일 목록 가져오기
	public List<BoardAttachVO> getAttachList(Long bno);
	
	
}




