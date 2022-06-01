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


@WebServlet("/withdrawal")
public class WithdrawalController extends HttpServlet {
	UserDaoImpl userDaoImpl = new UserDaoImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		int result=0;
		
		result = userDaoImpl.deleteUserByUsername((User)session.getAttribute("user"));
		
		if(result == 1) {
			resp.sendRedirect("/login_todo_service/index"); //Å»Åð ¼º°øÇÏ¸é index·Î º¸³»±â
		} else {
			req.getRequestDispatcher("/WEB-INF/logged-in.jsp").forward(req, resp);
		}
	}
}
