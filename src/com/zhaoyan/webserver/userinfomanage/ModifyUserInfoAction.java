package com.zhaoyan.webserver.userinfomanage;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserInfoModifyAction
 */
@WebServlet("/UserInfoModifyAction")
public class ModifyUserInfoAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ModifyUserInfoService mService;
	private UserManager mUserManager;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ModifyUserInfoAction() {
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
		String respondMessage = "";

		String action = request.getParameter("action");
		String username = request.getParameter("username");

		if (username == null || !mUserManager.isUserExist(username)) {
			respondMessage = "User not exist.";
			response.setStatus(400);
		} else {
			if (action.equals("password")) {
				respondMessage = modifyPassword(request, response, username);
			} else if (action.equals("email")) {
				respondMessage = modifyEmail(request, response, username);
			} else if (action.equals("phone")) {
				respondMessage = modifyPhone(request, response, username);
			}
		}

		PrintWriter writer = response.getWriter();
		writer.print(respondMessage);
		writer.flush();
		writer.close();
	}

	private String modifyPhone(HttpServletRequest request,
			HttpServletResponse response, String username) {
		String respondMessage = "";

		String password = request.getParameter("password");
		String phone = request.getParameter("phone");

		boolean isPasswordMatch = mUserManager
				.matchPassword(username, password);
		if (!isPasswordMatch) {
			respondMessage = "password dismatch.";
			response.setStatus(400);
		} else {
			boolean modifyResult = mService.modifyPhone(username, phone);
			if (modifyResult) {
				respondMessage = "phone modify sucess.";
				response.setStatus(200);
			} else {
				respondMessage = "phone modify fail.";
				response.setStatus(400);
			}
		}

		return respondMessage;
	}

	private String modifyPassword(HttpServletRequest request,
			HttpServletResponse response, String username) {
		String respondMessage = "";

		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		boolean isPasswordMatch = mUserManager.matchPassword(username,
				oldPassword);
		if (!isPasswordMatch) {
			respondMessage = "password dismatch.";
			response.setStatus(400);
		} else {
			boolean modifyResult = mService.modifyPassword(username,
					newPassword);
			if (modifyResult) {
				respondMessage = "password modify sucess.";
				response.setStatus(200);
			} else {
				respondMessage = "password modify fail.";
				response.setStatus(400);
			}
		}
		return respondMessage;
	}

	private String modifyEmail(HttpServletRequest request,
			HttpServletResponse response, String username) {
		String respondMessage = "";

		String password = request.getParameter("password");
		String email = request.getParameter("email");

		boolean isPasswordMatch = mUserManager
				.matchPassword(username, password);
		if (!isPasswordMatch) {
			respondMessage = "password dismatch.";
			response.setStatus(400);
		} else {
			boolean modifyResult = mService.modifyEmail(username, email);
			if (modifyResult) {
				respondMessage = "email modify sucess.";
				response.setStatus(200);
			} else {
				respondMessage = "email modify fail.";
				response.setStatus(400);
			}
		}

		return respondMessage;
	}

	@Override
	public void init() throws ServletException {
		super.init();
		mService = new ModifyUserInfoDao();
		mUserManager = new UserManager();
	}

}
