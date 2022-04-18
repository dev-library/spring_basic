package com.ict.service;

import java.util.List;

import com.ict.domain.ReplyVO;

public interface ReplyService {

	public List<ReplyVO> listReply(long bno);
	
	public void addReply(ReplyVO vo);
	
	public void modifyReply(ReplyVO vo);
	
	public void removeReply(Long rno);
	
}
