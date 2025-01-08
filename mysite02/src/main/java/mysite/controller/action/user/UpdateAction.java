package mysite.controller.action.user;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.controller.ActionServlet.Action;
import mysite.dao.UserDao;
import mysite.vo.UserVo;

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession s = request.getSession();
		
		if(s != null) {
			UserVo vo = (UserVo) s.getAttribute("authUser");
			Long id = vo.getId();
		
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String gender = request.getParameter("gender");
	
			UserVo updateVo = new UserVo();
			updateVo.setId(id);
			updateVo.setName(name);
			updateVo.setPassword(password);
			updateVo.setGender(gender);
			
			vo.setName(name);
			
			s.setAttribute("authUser", vo);
			new UserDao().update(updateVo);
			
			response.sendRedirect(request.getContextPath() + "/user?a=updateform");
			
		} else {
			response.sendRedirect(request.getContextPath() + "/main");
		}

	}

}
