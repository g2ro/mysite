package mysite.controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.controller.action.main.MainAction;

//@WebServlet({"/main",""}) // 공백은 아무것도을때 ex) mysite02/를 받겠
public class MainServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;
	@Override
	protected Action getAction(String actionName) {
		return new MainAction();
	}
	
	@Override
	public void init() throws ServletException { // main servlet 최초요청이 들어가면 제일 먼저 생성된 후 web.xml init-param이 실행됨?
		String config = getServletConfig().getInitParameter("config");
		System.out.println("MainController.init() called:" + config);
		super.init();
	}
	
}
