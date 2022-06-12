package viewController;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import repository.UserDao;
import repository.UserDaoImpl;

@WebServlet("/forgot-password")
public class ResetPasswordController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao =  new UserDaoImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/views/forgot-password.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String email = req.getParameter("email");
		String newPassword = req.getParameter("newPassword");
		int result = 0;
		
		result = userDao.resetPassword(username, email, newPassword);
		
		if (result == 1) {
			resp.sendRedirect("/login_todo_service/index");
			
		} else {
			resp.sendRedirect("/login_todo_service/forgot-password");
		}
	}
	
}
