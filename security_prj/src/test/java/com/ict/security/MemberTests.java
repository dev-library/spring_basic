package com.ict.security;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/security-context.xml"
})
@Log4j
public class MemberTests {

	@Autowired
	private PasswordEncoder pwen;
	@Autowired 
	private DataSource ds;
	
	//@Test
	public void testCryptDefaultDB() {
		String[] idList= {"user00", "member00", "admin00"};
		
		String sql = "UPDATE users set password=? WHERE username=?";
		
		try {
			Connection con = ds.getConnection();
			
			for(String id : idList) {
				PreparedStatement pstmt = con.prepareStatement(sql);
				String pw = pwen.encode("pw00");
				log.info("암호화 후 : " + pw);
				pstmt.setString(1, pw);
				pstmt.setString(2, id);
				
				pstmt.executeUpdate();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	public void testCryptCustomDB() {
		try {
			Connection con = ds.getConnection();
			String sql = "INSERT INTO member_tbl(userid, userpw, username) VALUES (?,?,?)";
			
			for(int i = 0; i < 30; i++) {
				PreparedStatement pstmt = con.prepareStatement(sql);
				
				pstmt.setString(2, pwen.encode("pw" + i));
				if (i < 10) {
					pstmt.setString(1,  ("user" + i));
					pstmt.setString(3,  ("준회원" + i));
				}
				else if (i < 20) {
					pstmt.setString(1,  ("user" + i));
					pstmt.setString(3,  ("정회원" + i));
				}
				else if (i < 30) {
					pstmt.setString(1,  ("user" + i));
					pstmt.setString(3,  ("운영자" + i));
					
				}
				pstmt.execute();
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}	
	
	//@Test
	public void testInsertAuth() {
		try {
			Connection con = ds.getConnection();
			String sql = "INSERT INTO member_auth(userid, auth) VALUES(?,?)";
			
			for(int i = 0; i < 30; i++) {
				PreparedStatement pstmt = con.prepareStatement(sql);
				
				if(i < 10) {
					pstmt.setString(1,  "user" + i);
					pstmt.setString(2, "ROLE_USER");
				}
				else if(i < 20) {
					pstmt.setString(1,  "user" + i);
					pstmt.setString(2, "ROLE_MEMBER");
				}
				else if(i < 30) {
					pstmt.setString(1,  "user" + i);
					pstmt.setString(2, "ROLE_ADMIN");
				}
				pstmt.execute();
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}







