package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.vo.BoardVo;
import mysite.vo.UserVo;

public class DeleteBoard implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		Long id = Long.parseLong(idStr);
		UserVo user = null;
		HttpSession session = request.getSession(false);
		
		if(session == null || session.getAttribute("authUser") == null) {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/user/loginform.jsp");
			rd.forward(request, response);
			return;
		}
		
		user = (UserVo)session.getAttribute("authUser");
		
		System.out.println("hello");
		System.out.println(id);
		System.out.println(user.getId());
		new BoardDao().deleteById(id, user.getId());
		response.sendRedirect(request.getContextPath() + "/board");
	}

}
