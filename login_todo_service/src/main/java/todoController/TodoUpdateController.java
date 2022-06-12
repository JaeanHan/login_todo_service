package todoController;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import repository.TodoDao;
import repository.TodoDaoImpl;

@WebServlet("/todoUpdate")
public class TodoUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TodoDao todoDao = new TodoDaoImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int todocode = Integer.parseInt(req.getParameter("upt_btn")); //item으로 받으면 toString() 결과가 넘어옴
		int newImportance = Integer.parseInt(req.getParameter("newImportance"));
		String newState = req.getParameter("newState");
				
		todoDao.updateTodobyBtn(todocode, newImportance, newState);
		
		resp.sendRedirect("/login_todo_service/sign-in");
	}
}
