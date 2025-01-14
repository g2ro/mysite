package mysite.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.service.UserService;
import mysite.vo.UserVo;

public class LoginInterceptor implements HandlerInterceptor {
	@Autowired
	private UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//		UserService service = new UserService(); 이렇게 생성자로 만들면 error => interceptor에서 UserService란 객체를 만드는 것 UserService 자체를 이용해야하는 거이기때문에?
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		UserVo authUser = userService.getUser(email, password);
		if(authUser == null) {
			request.setAttribute("email", email);
			request.setAttribute("result", "fail");
			request
				.getRequestDispatcher("/WEB-INF/views/user/login.jsp")
				.forward(request, response);
			
			return false;
		}
		
		/* login 처리 */
		HttpSession session = request.getSession();
		session.setAttribute("authUser", authUser);
		
		response.sendRedirect(request.getContextPath());
		
		System.out.println("[authUser:]" + authUser);
		return false;
	}
	
}
