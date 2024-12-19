package mysite.controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.controller.action.main.MainAction;

@WebServlet({"/main",""}) // 공백은 아무것도을때 ex) mysite02/를 받겠
public class MainServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;
	@Override
	protected Action getAction(String actionName) {
		return new MainAction();
	}
}
