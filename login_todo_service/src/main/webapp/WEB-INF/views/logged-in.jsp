<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ page import="entity.User"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/login_todo_service/static/css/logged-in.css?ver=1">
</head>
<body>
	<p>환영합니다 ${sessionScope.user.name}님!</p>
	<p>hi this is english trial</p>
	<p>change info?</p>
	<form action="update" method="post">
		<label for="name">name</label>
		<input id="name" type="text" name="name"/>
		<label for="email">email</label>
		<input id="email" type="text" name="email"/>
		<label for="password">password</label>
		<input id="password" type="password" name="password"/>
		<input type="submit" value="change!" />
	</form>
	<form action="withdrawal" method="get">
	<button type="submit">Account Withdrawal</button>
	</form>
	<form action="sign-out" method="get">
		<button type="submit">sign out</button>
	</form>
</body>
</html>