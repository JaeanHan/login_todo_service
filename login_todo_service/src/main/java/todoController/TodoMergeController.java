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
					.importance(1) // order by importance desc
					.build();
		
		int result = todoDao.addOrUpdateTodo(todo, num); // update db
		
		todos = todoDao.getTodosByUsername(user.getUsername());
		
		req.removeAttribute("todos"); // remove old todos		
		req.setAttribute("todos", todos); // new todos update
		req.getRequestDispatcher("/login_todo_service/sign-in").forward(req, resp); 
	}
}
