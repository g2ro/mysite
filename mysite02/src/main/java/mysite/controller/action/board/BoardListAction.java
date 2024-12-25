package mysite.controller.action.board;

import java.io.IOException;
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
		int currentPage = 0; // 현재 페이지
		if(pageParam != null) {
			currentPage = Integer.parseInt(pageParam);
		}else {
			currentPage = 1;
		}
		int pageSize = 5; // 한 페이지당 출력된 게시글 갯수
		int listSize = new BoardDao().findSize(); //전체 게시글 수
		int totalPage = (listSize > 0) ? (int) Math.ceil((double) listSize / pageSize) : 1;
		int pageBlock = 5; // 몇개의 페이지를 보여줄지, 5일경우 1,2,3,4,5 총 5개의 page를 보여준다.
		
		int halfBlock = pageBlock / 2;

		// 시작 페이지와 끝 페이지 계산
		int startPage = currentPage - halfBlock;
		int endPage = currentPage + halfBlock;

		// 1. 시작 페이지가 1보다 작으면 조정
		if (startPage < 1) {
		    startPage = 1;
		    endPage = Math.min(pageBlock, totalPage); // endPage는 블록 크기와 전체 페이지 중 작은 값
		}

		// 2. 끝 페이지가 전체 페이지를 초과하면 조정
		if (endPage > totalPage) {
		    endPage = totalPage;
		    startPage = Math.max(1, totalPage - pageBlock + 1); // startPage는 1 이상
		}

		
		
		
		List<BoardVo> list = new BoardDao().findByPage(currentPage, pageSize);
		
		 // 5. JSP로 데이터 전달
		request.setAttribute("list", list);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("totalPage", totalPage);
		request.setAttribute("listSize", listSize);
		request.setAttribute("pageSize", pageSize);
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/list.jsp");
		rd.forward(request, response);
		
	}

}
