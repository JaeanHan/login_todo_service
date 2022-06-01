package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.User;
import repository.UserDaoImpl;


@WebServlet("/update")
public class UpdateController extends HttpServlet {
	UserDaoImpl userDaoImpl = new UserDaoImpl();
	
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		HttpSession session = req.getSession();
////		User user = (User) session.getAttribute("user");
//		req.getRequestDispatcher("logged-in.jsp").forward(req, resp);
//		
//	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		User currentUser = (User) session.getAttribute("user"); //session¿¡¼­ °¡Á®¿È

		String newName = req.getParameter("name");
		String newEmail = req.getParameter("email");
		String newPassword = req.getParameter("password");
		
		if(!newName.equals(currentUser.getName())) { //form¿¡¼­ ºó°ªµµ °¡Á®¿È1
			currentUser.setName(newName);
		}
		
		if(!newEmail.equals(currentUser.getEmail())) { //form¿¡¼­ ºó°ªµµ °¡Á®¿È2
			currentUser.setEmail(newEmail);
		}
		
		if(!newPassword.equals(currentUser.getPassword())) { //form¿¡¼­ ºó°ªµµ °¡Á®¿È3
			currentUser.setPassword(newPassword);
		}
		
		userDaoImpl.updateProfile(currentUser); //db update
		
		session.removeAttribute("user"); // Áö¿ì°í
		session.setAttribute("user", currentUser); //session¿¡ Àçµî·Ï
		
		req.getRequestDispatcher("/WEB-INF/logged-in.jsp").forward(req, resp); 
	}
}
