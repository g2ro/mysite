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

public class WriteBoard implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session == null || session.getAttribute("authUser") == null) {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/errors/500.jsp");
			rd.forward(request, response);
		} else if(session != null) {
			UserVo userVo = (UserVo) session.getAttribute("authUser");
			
			Long userId = userVo.getId();
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			
			
			BoardVo vo = new BoardVo();
			vo.setTitle(title);
			vo.setContents(content);
			vo.setUserId(userId);
			
			new BoardDao().insert(vo);
			
			response.sendRedirect(request.getContextPath() + "/board");
		}
		
		
	}

}
