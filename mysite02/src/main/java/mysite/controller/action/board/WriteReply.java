package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.vo.UserVo;

public class WriteReply implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session == null) {
			response.sendRedirect(request.getContextPath() + "/board");
		} else if (session != null) {
			UserVo userVo = (UserVo) session.getAttribute("authUser");
			
			Long userId = userVo.getId();
			String idStr = request.getParameter("id");
			Long id = Long.parseLong(idStr);
			String title = request.getParameter("title");
			String content = request.getParameter("content");

			request.setAttribute("id", id);

			new BoardDao().insertReply(id, title, content, userId);

			response.sendRedirect(request.getContextPath() + "/board");
		}
	}
}
