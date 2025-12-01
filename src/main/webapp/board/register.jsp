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
	<!-- enctype="multipart/form-data" : 첨부파일 보낼때 사용 -->
	<form action="/brd/insert" method="post" enctype="multipart/form-data">
	title : <input type="text" name="title" placeholder="title..."> <br>
	writer : <input type="text" name="writer" placeholder="writer..."> <br> 
	content : <br>
	<textarea rows="10" cols="30" name="content" placeholder="content..."></textarea><br>
	file : <input type="file" name="imagefile">
	<button type="submit">등록</button>
	</form>
</body>
</html>