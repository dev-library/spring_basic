<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>${board.bno }번글 수정페이지입니다.</h1>
	${board}
	<form action="/boardUpdate" method="post">
		<input type="hidden" name="bno" value="${board.bno }"/>
		제목 : <input type="text" name="title" value="${board.title }">
		글쓴이 : <input type="text" name="writer" value="${board.writer }"><br/>
		본문 : <textarea name="content" rows="20" cols="100">${board.content }</textarea><br/> 
		<input type="submit" value="글쓰기"><input type="reset" value="초기화">
	</form>
</body>
</html>







