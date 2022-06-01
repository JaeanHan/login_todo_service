package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.User;
import repository.UserDaoImpl;


@WebServlet("/sign-up")
public class SignUpController extends HttpServlet {
	UserDaoImpl userDaoImpl = new UserDaoImpl();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = "annonymous";
		String email = req.getParameter("email");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		User user = User.builder()
				.name(name)
				.email(email)
				.username(username)
				.password(password)
				.build();
		
		int result = userDaoImpl.SignUp(user); //db signup
		if(result !=0 ) {
			resp.sendRedirect("/login_todo_service/index");
		}
	}
}
