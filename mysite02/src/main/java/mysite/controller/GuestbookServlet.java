package mysite.controller;

import java.io.IOException;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.controller.action.guest.AddAction;
import mysite.controller.action.guest.DeleteAction;
import mysite.controller.action.guest.DeleteFormAction;
import mysite.controller.action.guest.ListAction;

@WebServlet("/guestbook")
public class GuestbookServlet extends ActionServlet {
	
	private static final long serialVersionUID = 1L;
	
	private Map<String, Action> mapAction = Map.of(
			"insert", new AddAction(),
			"deleteform", new DeleteFormAction(),
			"delete" , new DeleteAction()
			);

	

		
		@Override
		protected Action getAction(String actionName) { //actionName이 null일 경우 error발생 -> null이 안 되도록 조정해야함.
			return mapAction.getOrDefault(actionName, new ListAction());
		}
		
//		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		request.setCharacterEncoding("utf-8");
//		String action = request.getParameter("a");
//		
//		if("insert".equals(action)) {
//			String name = request.getParameter("name");
//			String password = request.getParameter("pass");
//			String contents = request.getParameter("content");
//			
//			GuestbookVo vo = new GuestbookVo();
//			vo.setName(name);
//			vo.setPassword(password);
//			vo.setContents(contents);
//			
//			new GuestbookDao().insert(vo);
//			
//			response.sendRedirect(request.getContextPath() +"/guestbook");
//		} else if("deleteform".equals(action)) {
//			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/guestbook/deleteform.jsp");
//			rd.forward(request, response);
//			
//		} else if("delete".equals(action)) {
//			Long id = Long.parseLong(request.getParameter("id"));
//			String password = request.getParameter("password");
//			
//			new GuestbookDao().deleteByIdAndPassword(id, password);
//			response.sendRedirect(request.getContextPath() +"/guestbook");
//		} else {
//			List<GuestbookVo> list = new GuestbookDao().findAll();
//			request.setAttribute("list", list);
//			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/guestbook/list.jsp");
//			rd.forward(request, response);
//		}
//	}

//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		
//		doGet(request, response);
//	}

}
