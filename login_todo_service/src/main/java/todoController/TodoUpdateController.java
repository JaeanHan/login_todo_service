package todoController;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Todo;

@WebServlet("/todoUpdate")
public class TodoUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("TodoUpdateController");
		String todoStr = req.getParameter("upt_btn"); //이거 왜 object가 아니라 string으로 넘어오지
		String newState = req.getParameter("new_state"); //왜 null?
		String newImportance = req.getParameter("new_importance"); //왜 null?
		
		System.out.println("new: " + newState);
		System.out.println("new: " + newImportance);
		System.out.println(todoStr.getClass());
		System.out.println(todoStr);
		
		resp.sendRedirect("/login_todo_service/sign-in");
	}
}
