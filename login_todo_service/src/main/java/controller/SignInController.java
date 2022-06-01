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

@WebServlet("/sign-in")
public class SignInController extends HttpServlet {
	private UserDaoImpl userDaoImpl = new UserDaoImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		
		if(session==null || !req.isRequestedSessionIdValid()) {
			req.getRequestDispatcher("/WEB-INF/Index.html").forward(req, resp); // ���ΰ�ħ �ߴµ� �α����� ������ ���
			
		} else {// �α����� �ߴµ� ���ΰ�ħ�� ���� ���
			req.getRequestDispatcher("/WEB-INF/logged-in.jsp").forward(req, resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		User user = userDaoImpl.SignIn(username, password); //�´��� Ȯ��
		
		if(user != null) {
			HttpSession session = req.getSession();
			
			session.setAttribute("user", user); //session�� ���
			
			req.getRequestDispatcher("/WEB-INF/logged-in.jsp").forward(req, resp);
			
		} else {
			resp.sendRedirect("/login_todo_service/index"); //Ʋ���� �׳� �ٽ� index�� ������
		}
	}
}
