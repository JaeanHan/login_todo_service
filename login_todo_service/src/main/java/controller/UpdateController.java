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


@WebServlet("/update")
public class UpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserDao userDao = new UserDaoImpl();
	
//	@Override 새로고침 하는 경우?
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		HttpSession session = req.getSession();
////		User user = (User) session.getAttribute("user");
//		req.getRequestDispatcher("logged-in.jsp").forward(req, resp);
//		
//	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		User currentUser = (User) session.getAttribute("user"); //session에 저장되어있는 user 객체 

		String newName = req.getParameter("name");
		String newEmail = req.getParameter("email");
		String newPassword = req.getParameter("password");
		
		if(!newName.equals(currentUser.getName())) { //빈값도 넘어오니 여기서 분류1
			currentUser.setName(newName);
		}
		
		if(!newEmail.equals(currentUser.getEmail())) { //빈값도 넘어오니 여기서 분류2
			currentUser.setEmail(newEmail);
		}
		
		if(!newPassword.equals(currentUser.getPassword())) { //빈값도 넘어오니 여기서 분류3
			currentUser.setPassword(newPassword);
		}
		
		userDao.updateProfile(currentUser); //db update
		
		session.removeAttribute("user"); // 기존꺼 지우고
		session.setAttribute("user", currentUser); // 재등록
		
		req.getRequestDispatcher("/WEB-INF/logged-in.jsp").forward(req, resp); 
	}
}
