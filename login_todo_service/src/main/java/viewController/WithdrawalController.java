package viewController;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.User;
import repository.UserDao;
import repository.UserDaoImpl;


@WebServlet("/withdrawal")
public class WithdrawalController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserDao userDao = new UserDaoImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		int result=0;
		
		PrintWriter out = resp.getWriter();
		
		out.println("<script>confirm('for real?')</script>");
		
		result = userDao.deleteUserByUsername((User)session.getAttribute("user"));
		
		if(result == 1) {
			session.invalidate();
			resp.sendRedirect("/login_todo_service/index"); //삭제됐다면 index로 보내기
		} else {
			req.getRequestDispatcher("/WEB-INF/views/logged-in.jsp").forward(req, resp);
		}
	}
}
