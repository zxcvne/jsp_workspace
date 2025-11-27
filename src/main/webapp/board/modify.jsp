<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>수정 페이지 보기</h1>
	<form action="/brd/update" method="post">
	<!-- input 안만들고 값 보낼 때 사용 -->
	<input type="hidden" name="bno" value="${b.bno}">
	<table border="1">
		<tr>
			<th>no.</th>
			<td>${b. bno} </td>
		</tr>
		<tr>
			<th>title</th>
			<td><input type="text" name="title" value="${b.title}"> </td>
		</tr>
		<tr>
			<th>writer</th>
			<td>${b. writer} </td>
		</tr>
		<tr>
			<th>regdate</th>
			<td>${b. regdate} </td>
		</tr>
		<tr>
			<th>moddate</th>
			<td>${b. moddate} </td>
		</tr>
		<tr>
			<th>content</th>
			<td><textarea rows="10" cols="30" name="title">${b.content}</textarea> </td>
		</tr>
	</table>
	<button type="submit">수정</button>
	<button type="reset">취소</button>
	</form>
</body>
</html>