package com.ict.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import lombok.extern.log4j.Log4j;

@Log4j
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		log.warn("로그인 성공");
		List<String> roleList = new ArrayList<>();
		
		for(GrantedAuthority role : authentication.getAuthorities()) {
			roleList.add(role.getAuthority());
		}
		
		// roleList에 포함된 권한을 통해 로그인 계정의 권한에 따라 처리
		log.warn("부여받은 권한들 : " + roleList);
		if(roleList.contains("ROLE_ADMIN")) {
			response.sendRedirect("");// 주소는 추후에 로그인된 유저가 갈 페이지를 만들고 거기로 연결해주세요.
			return;
		}
		if(roleList.contains("ROLE_MEMBER")) {
			response.sendRedirect("");// 주소는 추후에 로그인된 유저가 갈 페이지를 만들고 거기로 연결해주세요.
			return;
		}
		
		response.sendRedirect("");// 로그인 안한유저에게 보낼 페이지
	}
}




