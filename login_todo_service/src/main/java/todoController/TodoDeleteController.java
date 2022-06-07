package todoController;

import java.io.IOException;

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

@WebServlet("/delete")
public class TodoDeleteController extends HttpServlet {
	private TodoDao todoDao = new TodoDaoImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		int todocode = Integer.parseInt(req.getParameter("del_btn"));
		System.out.println("TodoDeleteController: " + todocode);
		
		todoDao.deleteTodo(user.getUsercode(), todocode);
	
		resp.sendRedirect("/login_todo_service/sign-in");
	}
}
