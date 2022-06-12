<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Help</title>
</head>
<body>
	<p>your were sent here because one of reasons down here</p>
	<p>1. your username is invalid</p>
	<p>sorry but <strong>${username == null ? " " : username}</strong> is already taken!</p>
	<br/>
	<p>2. password should contain at least 3 characters of combination of numbers and alphabets</p>
	<br/>
	<a href="/login_todo_service/forgot-password">forgot password?</a>
	<p>or create new account </p>
	<a href="/login_todo_service/index">go to index</a>
</body>
</html>