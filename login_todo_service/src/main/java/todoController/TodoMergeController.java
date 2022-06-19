package todoController;

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

@WebServlet("/merge")
public class TodoMergeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TodoDao todoDao = new TodoDaoImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ArrayList<Todo> todos = new ArrayList<Todo>();
		HttpSession session = req.getSession();
		User user = (User)session.getAttribute("user");
		int usercode = user.getUsercode();
		
		String todoContent = (String)req.getParameter("todo");
		String option = (String) req.getParameter("submit");
		String state = (String)req.getParameter("state");
		int importance = Integer.parseInt(req.getParameter("importance"));
		
		int num;
		int todocode=0;

		if(option.equals("add")) {
			num = 1; //add
		} else {
			num = -1; //update
			todocode = todoDao.getTodoCode(usercode, todoContent);
		}
		
		Todo todo = Todo.builder()
					.todocode(todocode)
					.usercode(usercode)
					.todo(todoContent)
					.state(state)
					.importance(importance) // order by importance desc
					.build();
		
		int result = todoDao.addOrUpdateTodo(todo, num); // update db
		
		todos = todoDao.getTodosByUsercode(user.getUsercode());
		
		req.removeAttribute("todos"); // remove old todos		
		req.setAttribute("todos", todos); // new todos update
		resp.sendRedirect("/login_todo_service/sign-in");
	}
}
