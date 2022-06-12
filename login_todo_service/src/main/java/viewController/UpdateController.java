package viewController;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.User;
import repository.Lambda;
import repository.UserDao;
import repository.UserDaoImpl;


@WebServlet("/update")
public class UpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao = new UserDaoImpl();
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		User currentUser = (User) session.getAttribute("user"); //session에 저장되어있는 user 객체 

		String newName = req.getParameter("name");
		String newEmail = req.getParameter("email");
		String newPassword = req.getParameter("password");
		
		if(!newName.equals(currentUser.getName()) && !newName.equals("") ) {
			currentUser.setName(newName);
		}
		
		if(!newEmail.equals(currentUser.getEmail()) && !newEmail.equals("")) {
			currentUser.setEmail(newEmail);
		}
		
		Lambda isValidPassword = (check) -> {
			Pattern pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{3,}$");
			if (check.equals("")) {
		        return true; 
		    }
		    return pattern.matcher(check).matches();
		};
		
		if(!isValidPassword.isTrue(newPassword)) {
			PrintWriter out = resp.getWriter();
			out.println("<script>alert('invalid password form!');location.href='/login_todo_service/sign-in';</script>");
			out.close();
			return;
		}
		
		if(!newPassword.equals(currentUser.getPassword()) && !newPassword.equals("")) {
			currentUser.setPassword(newPassword);
		}
		
		userDao.updateProfile(currentUser); //db update
		
		session.removeAttribute("user"); // 기존꺼 지우고
		session.setAttribute("user", currentUser); // 재등록
		
		resp.sendRedirect("/login_todo_service/sign-in");
	}
}
