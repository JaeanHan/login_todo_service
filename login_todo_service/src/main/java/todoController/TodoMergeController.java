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

@WebServlet("/merge")
public class TodoMergeController extends HttpServlet {
	private TodoDao todoDao = new TodoDaoImpl();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		int usercode = ((User)session.getAttribute("user")).getUsercode();
		
		String todoContents = (String)req.getParameter("todo");
		String option = (String) req.getParameter("submit");
		String state = (String)req.getParameter("state");
		
		int num;
		int todocode=0;
		
		System.out.println("Controller: " + session.getId());
		System.out.println(option);
		System.out.println(todoContents);

		if(option.equals("add")) {
			num = 1; //add
		} else {
			num = -1; //update
			todocode = todoDao.getTodoCode(usercode, todoContents);
			System.out.println(todocode);
		}
		
		Todo todo = Todo.builder()
					.todocode(todocode)
					.usercode(usercode)
					.todo(todoContents)
					.state(state)
					.importance(1)
					.build();
		
		int result = todoDao.addOrUpdateTodo(todo, num);
		
//		req.getRequestDispatcher("/WEB-INF/views/logged-in.jsp").forward(req, resp);
		resp.sendRedirect("/login_todo_service/sign-in");
	}
}
