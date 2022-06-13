<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Help</title>
</head>
<body>
	<strong>${reason}</strong>
	<br/>
	<p>your were sent here because one of reasons down here</p>
	<p>1. your username is invalid</p>
	<p>2. password should contain at least 3 characters of combination of numbers and alphabets</p>
	<p>3. when your username is not found</p>
	<p>4. when your password is incorrect</p>
	<br/>
	
	<br/>
	<a href="/login_todo_service/forgot-password">forgot password?</a>
	<p>or create new account </p>
	<a href="/login_todo_service/index">go to index</a>
</body>
</html>