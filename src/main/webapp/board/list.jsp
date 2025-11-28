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
<h1>게시판 보기 화면</h1>

<table border="1">
<tr>
<th>no.</th>
<th>title</th>
<th>writer</th>
<th>regdate</th>
</tr>
<c:forEach items="${list}" var="b">
<tr>
<td>${b.bno} </td>
<td><a href="/brd/detail?bno=${b.bno}">${b.title}</a></td>
<td>${b.writer} </td>
<td>${b.regdate} </td>
</tr>
</c:forEach>
</table>
<!-- 가져온 ph 정보를 이용하여 하단 forEach를 구성 -->
<div>
	<!-- 이전 -->
	<c:if test="${ph.prev}">
		<a href="/brd/list?pageNo=${ph.startPage-1 }&qty=${ph.pgvo.qty}"> < </a>
	</c:if>
	<c:forEach begin="${ph.startPage}" end="${ph.endPage}" var="i">
		<a href="/brd/list?pageNo=${i}&qty=${ph.pgvo.qty}">${i}</a> 
	</c:forEach>
	<!-- 다음 -->
	<c:if test="${ph.next}">
		<a href="/brd/list?pageNo=${ph.endPage+1 }&qty=${ph.pgvo.qty}"> > <a>
	</c:if>
</div>

<a href="/index.jsp">index로 이동</a>

</body>
</html>