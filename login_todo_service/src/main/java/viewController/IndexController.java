package viewController;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/index")
public class IndexController extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		if(session.getAttribute("user") == null ) {
			request.getRequestDispatcher("/WEB-INF/views/Index.jsp").forward(request, resp); //url요청시 index로 보내기
		} else {
			request.getRequestDispatcher("/WEB-INF/views/logged-in.jsp").forward(request, resp);			
		}
	}
}
