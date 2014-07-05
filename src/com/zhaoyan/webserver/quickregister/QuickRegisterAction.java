package com.zhaoyan.webserver.quickregister;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhaoyan.webserver.common.Logger;

public class QuickRegisterAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int MAX_QUICK_REGISTER_TRY_TIME = 10;
	private QuickRegisterService mQuickRegisterService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QuickRegisterAction() {
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

		String respondMessage = quickRegister(request, response);

		PrintWriter writer = response.getWriter();
		writer.print(respondMessage);
		writer.flush();
		writer.close();
	}

	private String quickRegister(HttpServletRequest request,
			HttpServletResponse response) {
		boolean checkRegisterOK = true;
		String respondMessage = "";

		String password = request.getParameter("password");

		if (password == null || password.equals("")) {
			respondMessage = "password is empty!";
			response.setStatus(400);
			checkRegisterOK = false;
		}

		boolean registerSuccess = false;
		if (checkRegisterOK) {
			List<Object> params = new ArrayList<>();
			params.add(password);
			String username = "";
			// try register for MAX_QUICK_REGISTER_TRY_TIME times to avoid
			// register fail.
			for (int i = 0; i < MAX_QUICK_REGISTER_TRY_TIME; i++) {
				username = UsernameGenerator.generateUsername();
				registerSuccess = mQuickRegisterService.registerUser(username,
						password);
				if (registerSuccess) {
					break;
				}
				Logger.log("quick register, try time = " + i + ", success?"
						+ registerSuccess);
			}

			if (registerSuccess) {
				respondMessage = "username:" + username;
				response.setStatus(200);
			} else {
				respondMessage = "Quick register fail.";
				Logger.loge("Quick register fail.");
				response.setStatus(400);
			}
		}
		return respondMessage;
	}

	@Override
	public void init() throws ServletException {
		super.init();
		mQuickRegisterService = new QuickRegisterDao();
	}

}
