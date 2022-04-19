package com.ict.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.domain.ReplyVO;
import com.ict.mapper.ReplyMapper;

@Service
public class ReplyServiceImpl implements ReplyService {

	// 서비스가 매퍼를 호출하므로 매퍼를 위에 선언해야 합니다.
	@Autowired
	private ReplyMapper mapper;

	@Override
	public List<ReplyVO> listReply(long bno) {
		return mapper.getList(bno);
	}

	@Override
	public void addReply(ReplyVO vo) {
		mapper.create(vo);
	}

	@Override
	public void modifyReply(ReplyVO vo) {
		mapper.update(vo);	
	}

	@Override
	public void removeReply(Long rno) {
		mapper.delete(rno);
	}
	

	
}
