package com.zhaoyan.webserver.login;

import java.io.IOException;
import java.io.PrintWriter;
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
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String respondMessage = login(request, response);

		String path = request.getContextPath();
		response.sendRedirect(path + "/show_result.jsp?result="
				+ URLEncoder.encode(respondMessage, "utf-8"));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String respondMessage = login(request, response);

		response.setContentType("txt/html;charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.write(respondMessage);
		writer.flush();
		writer.close();
	}

	private String login(HttpServletRequest request,
			HttpServletResponse response) {
		String respondMessage = "";

		String usernameOrEmail = request.getParameter("usernameOrEmail");
		String password = request.getParameter("password");

		List<Object> params = new ArrayList<>();
		params.add(usernameOrEmail);
		params.add(usernameOrEmail);
		params.add(password);
		boolean success = mLoginService.login(params);
		if (success) {
			respondMessage = "Login success.";
			System.out.println("Login success user = " + usernameOrEmail);
			response.setStatus(200);
			
		} else {
			System.out.println("Login fail user = " + usernameOrEmail);
			List<Object> params2 = new ArrayList<>();
			params2.add(usernameOrEmail);
			params2.add(usernameOrEmail);
			if (!mLoginService.isUserExist(params2)) {
				respondMessage = "User not exist. user = " + usernameOrEmail;
			} else {
				respondMessage = "Password dismatch.";
			}
			response.setStatus(400);
		}

		return respondMessage;
	}

	@Override
	public void init() throws ServletException {
		super.init();
		mLoginService = new LoginDao();
	}

}
