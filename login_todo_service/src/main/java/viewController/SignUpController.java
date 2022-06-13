package viewController;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.User;
import repository.Lambda;
import repository.UserDao;
import repository.UserDaoImpl;


@WebServlet("/sign-up")
public class SignUpController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		userDao = new UserDaoImpl(req, resp);
		
		String name = "friend"; // 임시로 부여
		String email = req.getParameter("email");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String role = "user";
		
//		"^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$" 
//		최소 3자리에 숫자, 문자, 특수문자 각각 1개 이상 포함
		
		Lambda isValidPassword = (check) -> {
			Pattern pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{3,}$");
			if (check == null) {
		        return false; 
		    }
		    return pattern.matcher(check).matches();
		};
		
		if(!isValidPassword.isTrue(password)) {
			req.setAttribute("reason", "password is in invalid form!");
			req.getRequestDispatcher("/issue").forward(req, resp);
			req.removeAttribute("reason");
			return;
		}
		
		User user = User.builder()
				.name(name)
				.email(email)
				.username(username)
				.password(password)
				.role(role)
				.build();
		
		int result = userDao.SignUp(user);
		
		if(result == 0) {
			return;
		}
		
		resp.sendRedirect("/login_todo_service/index");

	}
}
