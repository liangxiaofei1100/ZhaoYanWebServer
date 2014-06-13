package com.zhaoyan.webserver.login;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginAction
 */
@WebServlet("/LoginAction")
public class LoginAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private LoginService mLoginService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		List<Object> params = new ArrayList<>();
		params.add(username);
		params.add(password);
		boolean success = mLoginService.login(params);
		if (success) {
			System.out.println("login success");
		} else {
			System.out.println("login fail");
		}
		
		String path = request.getContextPath();
		response.sendRedirect(path + "/show_result.jsp?result="
				+ URLEncoder.encode(success ? "登录成功" : "登录失败", "utf-8"));
	}

	@Override
	public void init() throws ServletException {
		super.init();
		mLoginService = new LoginDao();
	}

}
