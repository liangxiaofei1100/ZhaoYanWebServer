package com.zhaoyan.webserver.register;

import java.io.IOException;
import java.io.PrintWriter;
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
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);

		String respondMessage = register(request, response);

		PrintWriter writer = response.getWriter();
		writer.print(respondMessage);
		writer.flush();
		writer.close();
	}

	private String register(HttpServletRequest request,
			HttpServletResponse response) {
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
