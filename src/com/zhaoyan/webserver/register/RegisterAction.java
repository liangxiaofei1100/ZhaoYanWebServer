package com.zhaoyan.webserver.register;

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
 * Servlet implementation class RegisterAction
 */
@WebServlet("/RegisterAction")
public class RegisterAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RegisterService mRegisterService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterAction() {
		super();
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
		response.setContentType("txt/html;charset=utf-8");
		PrintWriter writer = response.getWriter();

		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println("Username=" + userName);
		System.out.println("Password=" + password);

		if (userName == null || userName.equals("")) {
			System.err.println("User name is empty!!");
		}
		if (password == null || password.equals("")) {
			System.err.println("Password is empty");
		}

		List<Object> params = new ArrayList<>();
		params.add(userName);
		params.add(password);
		boolean success = mRegisterService.registerUser(params);
		if (success) {
			writer.write("Register success.");
		} else {
			writer.write("Register fail.");
		}
		String path = request.getContextPath();
		response.sendRedirect(path + "/show_result.jsp?result="
				+ URLEncoder.encode(success ? "注册成功" : "注册失败", "utf-8"));
		writer.flush();
		writer.close();
	}

	@Override
	public void init() throws ServletException {
		super.init();
		mRegisterService = new RegisterDao();
	}

}
