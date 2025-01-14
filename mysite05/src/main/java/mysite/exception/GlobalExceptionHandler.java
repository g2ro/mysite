package mysite.exception;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.dto.JsonResult;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Log Log = LogFactory.getLog(GlobalExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	public void handler(
			HttpServletRequest request,
			HttpServletResponse response,
			Exception e) throws Exception{
		// 1. 로깅(logging)
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		System.out.println(errors.toString());
		Log.error(errors.toString());
	
		// 2. 요청 구분
		// 	json 요청: request header의 accept: application/json (o)
		// 	html 요청: request header의 accept: application/json (x) : json 요청에는 html이 들어갈 수 있지만, html에는 절대로 json 이 들어갈 수 없음.
		String accept = request.getHeader("accept");
		if(accept.matches(".*application/json.*")){ // matches내부에는 정규표현식
			// 3. json 응답
			JsonResult jsonResult = JsonResult.fail(errors.toString());
			String jsonString = new ObjectMapper().writeValueAsString(jsonResult);
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("application/json; charset=utf-8");
			OutputStream os = response.getOutputStream();
			os.write(jsonString.getBytes("utf-8"));
			os.close();
			
		} else {
			// 4. 사과 페이지 (종료)
			request.setAttribute("errors", errors.toString());
			request
				.getRequestDispatcher("/WEB-INF/views/errors/exception.jsp")
				.forward(request, response);
		}
		
	}
}
