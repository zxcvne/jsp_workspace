<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>게시판 글쓰기 페이지 입니다.</h1>
	<form action="/brd/insert" method="post">
	title : <input type="text" name="title" placeholder="title..."> <br>
	writer : <input type="text" name="writer" placeholder="writer..."> <br> 
	content : <br>
	<textarea rows="10" cols="30" name="content" placeholder="content..."></textarea><br>
	<button type="submit">등록</button>
	</form>
</body>
</html>