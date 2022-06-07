<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="entity.Todo" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	
	<form action="merge" method="get">
		<p>manage todo!</p>
		<label for="todo">todo</label>
		<input type="text" required name="todo" id="todo" placeholder="add todo here!"/>
		<div>
			<p>check the state!</p>
			<input type="radio" name="state" id="done" value="done" required/>
			<label for="done">Done</label>
			<input type="radio" name="state" id="doing" value="doing" />
			<label for="doing">Doing</label>
		</div>
		<div>
			<p>check the importance!</p>
			<input type="radio" name="importance" id="personal" value="0" required/>
			<label for="personal">Personal</label>
			<input type="radio" name="importance" id="work" value="1" />
			<label for="work">Work</label>
		</div>
		<input type="submit" value="add" name="submit"/>
		<input type="submit" value="update" name="submit" />
		<label>추후 자바스크립스로 핸들</label>
	</form>
	
	<c:forEach var="item" items="${todos}">
		<span>${item.todo}</span>
		<span>${item.state}</span>
		<span>${item.importance le 1 ? "Personal" : "Work"}</span>
		<form action="delete" method="get">
			<button type="submit" name="del_btn" value="${item.todocode}">delete</button>
		</form>
	</c:forEach>

<%--
	<%
		//test
		if(request.getAttribute("todos") == null) {
			out.println("why null? <br />");
			out.println("no todo added yet! <br />");
			
		} else {
			ArrayList<Todo> todos = (ArrayList<Todo>) request.getAttribute("todos");
			
			for(int i=0; i< todos.size(); i++) {
				out.print(todos.get(i).getTodo() + " / ");
				out.print(todos.get(i).getState() + " / ");
				out.println(todos.get(i).getImportance() == 1 ? "normal" : "urgent" + "<br />");
			}
			
		}
	%>
 --%>
</body>
</html>