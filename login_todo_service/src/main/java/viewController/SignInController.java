package viewController;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.Todo;
import entity.User;
import repository.TodoDao;
import repository.TodoDaoImpl;
import repository.UserDao;
import repository.UserDaoImpl;

@WebServlet("/sign-in")
public class SignInController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao = new UserDaoImpl();
	private TodoDao todoDao = new TodoDaoImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		
		if(session == null ) {
			resp.sendRedirect("/login_todo_service/index"); // 로그인 안됐거나 세션 없어졌을 때
			
		} else { 
//			String username = ((User) session.getAttribute("user")).getUsername();
			int usercode = ((User) session.getAttribute("user")).getUsercode();
			
			ArrayList<Todo> todos = new ArrayList<Todo>(); //이거를 로그인 처음 할 때 한번 가져와서
			todos = todoDao.getTodosByUsercode(usercode); //로컬어딘가에 저장해놓고 가져오고 싶은데
			
			req.setAttribute("todos", todos); // request
			req.getRequestDispatcher("/WEB-INF/views/logged-in.jsp" ).forward(req, resp); 
			
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
//		if(userDao.checkValidString(username)==0 || userDao.checkValidString(password)==0) {
//			resp.sendRedirect("/login_todo_service/index");
//			return;
//		}
		
		User user = userDao.SignIn(username, password); // db에서 정보 확인하고 받아와서

		HttpSession session = req.getSession();
		
		if(user != null) { // 잘 받아왔으면
			session.setMaxInactiveInterval(60 * 20);
			session.setAttribute("user", user); // session에 저장
			
			resp.sendRedirect("/login_todo_service/sign-in"); // PRG
			
		} else {
			resp.sendRedirect("/login_todo_service/index"); // 회원가입이 안됐을 경우 index로 보내기
		}
	}
}
