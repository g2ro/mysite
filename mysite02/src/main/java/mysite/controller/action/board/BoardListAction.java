package mysite.controller.action.board;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.vo.BoardVo;

public class BoardListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String pageParam = request.getParameter("p");
		int currentPage = 0;
		
		if(pageParam == null) {
			currentPage = 1;
		}else {
			currentPage = Integer.parseInt(pageParam);
		}
		
		int pageSize = 5; // 한페이지에 볼 수 있는 게시글 수
		int pageBlock = 5; // 한번에 최대 몇개의 페이지를 볼 수 있는지 지정
		
		List<BoardVo> list = new ArrayList<>();
		int listSize = 0;
		String kwd = request.getParameter("kwd");
		if(kwd == null) {
			list = new BoardDao().findByPage(currentPage, pageSize);
			listSize = new BoardDao().findSize();
		}else {
			list = new BoardDao().findByPageWithSearch(currentPage, pageSize, kwd);
			listSize = new BoardDao().findByPageWithSearchCount(kwd);
			request.setAttribute("kwd", kwd);
		}

		
		int totalPage = (int) Math.ceil((double)listSize / pageSize);
		if(totalPage < 0) {
			totalPage = 1;
		}
		
		int startPage = currentPage - ((int)pageBlock/2);
		int endPage = currentPage + ((int)pageBlock/2);
		
		//1. startPage가 0보다 작을 경우
		if(startPage < 1) {
			startPage = 1;
			endPage = Math.min(pageBlock, totalPage);
		}
		
		//2. endPage가 totalPage보다 클 경우
		if(endPage>totalPage) {
			endPage = totalPage;
			startPage = Math.max((endPage - (pageBlock - 1)), 1);
		}
		
		request.setAttribute("list", list);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("listSize", listSize);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("totalPage", totalPage);
		request.setAttribute("pageSize", pageSize);
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/list.jsp");
		rd.forward(request, response);

	}

}
