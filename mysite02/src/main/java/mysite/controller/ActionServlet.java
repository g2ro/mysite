package mysite.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@SuppressWarnings("serial")
public abstract class ActionServlet extends HttpServlet {
	
	//factoryMethod
	protected abstract Action getAction(String actionName); // factoryMehod기능
	
	// operation
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// request.setCharacterEncoding("utf-8");
		Optional<String> optionalActionName = Optional.ofNullable(request.getParameter("a")); // action이 null인 경우 조치
				
//		Action action = getAction(optionalActionName.isEmpty() ? "" : optionalActionName.get());
		Action action = getAction(optionalActionName.orElse(""));

//		if(actionName == null) { // action a가 null일 경우 조치
//		actionName = "";
//		} // Optional.ofNullable(request.getParameter("a")).orElse(""); 의 동작이 다음과 같다.

		action.execute(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public static interface Action{ // 원래는 다른 class로 만들어야함
		void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
	}
}
