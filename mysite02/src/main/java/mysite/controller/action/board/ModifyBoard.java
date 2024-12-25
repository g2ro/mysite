package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;

public class ModifyBoard implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		Long id = Long.parseLong(idStr);
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		new BoardDao().update(id, title, content);
		response.sendRedirect(request.getContextPath() + "/board?a=view&id=" + idStr);
	}

}
