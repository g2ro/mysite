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

public class ModifyFormBoard implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		Long id = Long.parseLong(idStr);
		
		String userIdStr = request.getParameter("userId");
		Long userId = Long.parseLong(userIdStr);
		
		HttpSession session = request.getSession(false);
		UserVo vo = (UserVo)session.getAttribute("authUser");
		
		if(vo.getId() == userId) {
			BoardVo Bvo = new BoardDao().findById(id);
			request.setAttribute("Bvo", Bvo);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/modify.jsp");
			rd.forward(request, response);
		}else {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/errors/500.jsp");
			rd.forward(request, response);
		}
		
		
	}

}
