<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Password Issue</title>
</head>
<body>
	<p>reset password by keys you wrote when enrolled!</p>
	<p>you will be automatically redirected if change success!</p>
	<p>this page will appear again when errors occur :(</p>
		<form action="forgot-password" method="post">
		<label for="username">username</label>
		<input required type="text" name="username" id="username"/>
		<label for="email">email</label>
		<input required type="text" name="email" id="email" />
		<label for="password">new password</label>
		<input required type="password" name="newPassword" id="password"/>
		<button>submit</button>
	</form>
</body>
</html>