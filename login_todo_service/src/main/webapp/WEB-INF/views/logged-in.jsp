<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="entity.Todo" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="/login_todo_service/static/css/logged-in.css?ver=2" />
    <title>Document</title>
  </head>
  <body>
    <header>
      <div class="header_left">
        <button type="button" class="view_btn">
          <i class="fa-solid fa-bars"></i>
        </button>
        <button type="button" class="home">
          <i class="fa-solid fa-house"></i>
        </button>
        <div class="name">Login_todo_service</div>
      </div>
      <div class="header_right">
        <p class="loggedin_username">${sessionScope.user.name}</p>
        <button class="settings_btn" type="button">
          <i class="fa-solid fa-user"></i>
        </button>
        <button class="notification" type="button">
          <i class="fa-solid fa-bell"></i>
        </button>
        <button class="quick_add" type="button">
        <i class="fa-solid fa-plus"></i>
        </button>
      </div>
      <div class="changeInfo">
        <div class="title">Change Profile Info!</div>
        <div class="contents">
          <div class="navigation">
            <ul>
              <li class="nav_items">
                <label for="name">
                  <i class="fa-solid fa-user"></i>
                  name
                </label>
              </li>
              <li class="nav_items">
                <label for="email">
                  <i class="fa-solid fa-at"></i>
                  email
                </label>
              </li>
              <li class="nav_items">
                <label for="password">
                  <i class="fa-solid fa-key"></i>
                  password
                </label>
              </li>
              <div class="space_usage"> </div>
              <form action="sign-out" method="get">
                <button type="submit">Sign out</button>
              </form>
              <form action="withdrawal" method="get">
                <button type="submit">Delete account</button>
              </form>
            </ul>
          </div>
          <form action="update" method="post" class="changeInfo__profile">
            <h1>Change Properties!</h1>
            <div class="changeInfo__profile__name">
              <label for="name">name</label>
              <input id="name" type="text" name="name" />
              <p>
                your name will appear on your dashboard and to your friends!
              </p>
            </div>
            <div class="changeInfo__profile__email">
              <label for="email">email</label>
              <input id="email" type="text" name="email" />
              <p>
                your email will appear on your profile as your only contact!
              </p>
            </div>
            <div class="changeInfo__profile__password">
              <label for="password">password</label>
              <input id="password" type="password" name="password" />
              <p>password should contain at least 1 numeric and 1 letter!</p>
            </div>
            <button>submit!</button>
          </form>
        </div>
      </div>
    </header>
    <main>
      <section class="tab">
        <p>more tasks!</p>
        <div class="btn_area">
    	    <button class="todos__btn"><i class="fa-solid fa-plus"></i> add todos!</button>
        </div>
        <button><i class="fa-solid fa-eye"></i><span> view all</span></button>
        <button><i class="fa-solid fa-calendar-days"></i><span> view by date</span></button>
      </section>
      <section class="todos">

        <h1>Todos!</h1>
        <c:forEach var="item" items="${todos}">
			<div class="todo_container">
				<div class="visibility">
					<div class="circle"></div>
				</div>
				<span>${item.todo}</span>
				<div class="todo_container_settings">
					<div class="date"><c:out value="${fn:substring(item.create_date, 5, 10)}, ${fn:substring(item.create_date, 0,4)}"/></div>
					<form class="upt_form" action="todoUpdate" method= "get">
						<select class="newState" name="newState" >
							<option value="${item.state}" selected >${item.state eq "doing"? "doing" : "done"} </option>
							<option value="${item.state eq 'doing' ? 'done' : 'doing' }">${item.state eq "doing" ? "done" : "doing"}</option>
						</select>
						<select class="newImportance" name="newImportance" >
							<option class="select_option" value="${item.importance}" selected >${item.importance eq 1 ? "Work" : "Personal"}</option>
							<option class="select_option" value="${item.importance eq 1 ? 0 : 1}">${item.importance eq 1 ? "Personal" : "Work"}</option>
						</select>
						<button class="upt_btn" name="upt_btn" value="${item.todocode}"><i class="fa-solid fa-check"></i></button>
					</form>
					<form action="delete" method="get">
						<button name="del_btn" value="${item.todocode}"><i class="fa-solid fa-trash-can"></i></button>
					</form>
				</div>
			</div>
		</c:forEach>
      </section>
      <section class="todos__manage">
        <form class="todo_form" action="merge" method="get">
          <label class="add_label" for="todo">todo</label>
          <input
            class="todo_input"
            type="text"
            required
            name="todo"
            id="todo"
            placeholder="add todo here!"
          />
          <div>
            <p>check the state!</p>
            <input type="radio" name="state" id="done" value="done" required />
            <label for="done">Done</label>
            <input type="radio" name="state" id="doing" value="doing" />
            <label for="doing">Doing</label>
          </div>
          <div>
            <p>check the importance!</p>
            <input
              type="radio"
              name="importance"
              id="personal"
              value="0"
              required
            />
            <label for="personal">Personal</label>
            <input type="radio" name="importance" id="work" value="1" />
            <label for="work">Work</label>
          </div>
          <div class="todo_form_submits">
	          <input class="td_add_btn" type="submit" value="add" name="submit" />
	          <input class="td_upt_btn" type="submit" value="update" name="submit" />
          </div>
        </form>
      </section>
    </main>
    <script src="/login_todo_service/static/js/logged-in.js"></script>
    <script
      src="https://kit.fontawesome.com/3e27d22283.js"
      crossorigin="anonymous"
    ></script>
  </body>
</html>
