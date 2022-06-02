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


@WebServlet("/sign-up")
public class SignUpController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserDao userDao = new UserDaoImpl();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = "friend"; // 디자인 때문에 name을 못받아와서 임시로 부여, 인풋박스 4개는 안이쁨.
		String email = req.getParameter("email");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		User user = User.builder()
				.name(name)
				.email(email)
				.username(username)
				.password(password)
				.build();
		
		int result = userDao.SignUp(user);
		if(result !=0 ) {
			resp.sendRedirect("/login_todo_service/index");
		}
	}
}
