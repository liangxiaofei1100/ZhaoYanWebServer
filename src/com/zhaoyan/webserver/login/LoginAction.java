package com.zhaoyan.webserver.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhaoyan.webserver.userinfomanage.GetUserInfoDAO;
import com.zhaoyan.webserver.userinfomanage.GetUserInfoService;
import com.zhaoyan.webserver.userinfomanage.UserInfoUtil;

/**
 * Servlet implementation class LoginAction
 */
@WebServlet("/LoginAction")
public class LoginAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private LoginService mLoginService;
	private GetUserInfoService mGetUserInfoService;

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

		String respondMessage = login(request, response);

		PrintWriter writer = response.getWriter();
		writer.print(respondMessage);
		writer.flush();
		writer.close();
	}

	private String login(HttpServletRequest request,
			HttpServletResponse response) {
		String respondMessage = "";

		String usernameOrEmail = request.getParameter("usernameOrEmail");
		String password = request.getParameter("password");

		List<Object> loginParams = new ArrayList<>();
		loginParams.add(usernameOrEmail);
		loginParams.add(usernameOrEmail);
		loginParams.add(password);
		boolean success = mLoginService.login(loginParams);
		if (success) {
			System.out.println("Login success user = " + usernameOrEmail);
			List<Object> getUserInfoParams = new ArrayList<>();
			getUserInfoParams.add(usernameOrEmail);
			getUserInfoParams.add(usernameOrEmail);
			Map<String, Object> userInfo = mGetUserInfoService
					.getUserInfo(getUserInfoParams);

			respondMessage = UserInfoUtil.userInfo2Json(userInfo);
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
		mGetUserInfoService = new GetUserInfoDAO();
	}

}
