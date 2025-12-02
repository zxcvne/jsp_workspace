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
	<h1>회원가입 페이지</h1>
	<form action="/user/insert" method="post">	
	id : <input type="text" name="id" placeholder="id ..."> <br>
	pwd : <input type="text" name="pwd" placeholder="password ..."> <br>
	email : <input type="text" name="email" placeholder="email ..."> <br>
	phone : <input type="text" name="phone" placeholder="phone ..."> <br>
	<button>회원가입</button>
	</form>
</body>
</html>