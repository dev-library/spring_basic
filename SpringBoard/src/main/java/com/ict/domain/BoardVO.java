package com.ict.domain;

import java.sql.Date;
import java.util.List;

import lombok.Data;

// lombok을 이용해 get-setter, 생성자, toString을 만들어주세요.
@Data
public class BoardVO {
	
	// board_tbl 구조에 맞게 멤버변수를 선언해주세요.
	private long bno; 
	private String title;
	private String content;
	private String writer;
	private Date regdate;
	private Date updatedate;
	private int replyCount;
	
	private List<BoardAttachVO> attachList;
	
}
