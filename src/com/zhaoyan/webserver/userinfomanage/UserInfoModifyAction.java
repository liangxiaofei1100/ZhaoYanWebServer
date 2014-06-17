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
public class UserInfoModifyAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserInfoModifyService mService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserInfoModifyAction() {
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
		String respondMessage = "";

		String action = request.getParameter("modifyAction");
		if (action.equalsIgnoreCase("password")) {
			respondMessage = modifyPassword(request, response);
		} else if (action.equalsIgnoreCase("basicInfo")) {
			respondMessage = modifyBasicInfo(request, response);
		}

		String path = request.getContextPath();
		response.sendRedirect(path + "/show_result.jsp?result="
				+ respondMessage);

		writer.write(respondMessage);
		writer.flush();
		writer.close();
	}

	private String modifyPassword(HttpServletRequest request,
			HttpServletResponse response) {
		String respondMessage = "";

		return respondMessage;
	}

	private String modifyBasicInfo(HttpServletRequest request,
			HttpServletResponse response) {
		String respondMessage = "";

		return respondMessage;
	}

	@Override
	public void init() throws ServletException {
		super.init();
		mService = new UserInfoModifyDao();
	}

}
