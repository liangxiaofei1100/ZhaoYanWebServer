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
		String respondMessage = register(request, response);

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
		String respondMessage = register(request, response);

		PrintWriter writer = response.getWriter();
		writer.write(respondMessage);
		writer.flush();
		writer.close();
	}

	private String register(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("txt/html;charset=utf-8");
		boolean checkRegisterOK = true;
		String respondMessage = "";

		String userName = request.getParameter("username");
		String password = request.getParameter("password");

		if (userName == null || userName.equals("")) {
			respondMessage = "User name is empty!";
			System.err.println("User name is empty!");
			response.setStatus(400);
			checkRegisterOK = false;
		} else if (password == null || password.equals("")) {
			respondMessage = "Password is empty!";
			System.err.println("Password is empty!");
			response.setStatus(400);
			checkRegisterOK = false;
		}

		boolean registerSuccess = false;
		if (checkRegisterOK) {
			List<Object> params = new ArrayList<>();
			params.add(userName);
			params.add(password);
			registerSuccess = mRegisterService.registerUser(params);
			if (registerSuccess) {
				respondMessage = "Register success.";
				response.setStatus(200);
			} else {
				if (mRegisterService.isUserNameExist(userName)) {
					respondMessage = "User name is already exist.";
				} else {
					respondMessage = "Unkown error.";
				}
				response.setStatus(400);
			}
		}
		return respondMessage;
	}

	@Override
	public void init() throws ServletException {
		super.init();
		mRegisterService = new RegisterDao();
	}

}
