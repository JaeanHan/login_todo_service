package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.User;
import repository.UserDao;
import repository.UserDaoImpl;

@WebServlet("/sign-in")
public class SignInController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao = new UserDaoImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		System.out.println("apply PRG pattern?"); // 새로고침 할 때 마다 session에 저장하니까
		
		if(session == null || req.isRequestedSessionIdValid()) {
			req.getRequestDispatcher("/WEB-INF/views/logged-in.jsp").forward(req, resp); // 로그인 됐는데 새로고침 했을 때
		} else { 
			resp.sendRedirect("/login_todo_service/index"); // 로그인 안됐거나 세션 없어졌을 때
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		User user = userDao.SignIn(username, password); // db에서 정보 확인하고 받아와서
		
		if(user != null) { // 잘 받아왔으면
			HttpSession session = req.getSession();
			
			session.setAttribute("user", user); //session에 저장
			
			req.getRequestDispatcher("/WEB-INF/views/logged-in.jsp").forward(req, resp); // 다음페이지로 보내기
			
		} else {
			resp.sendRedirect("/login_todo_service/index"); // 회원가입이 안됐을 경우 index로 보내기
		}
	}
}
