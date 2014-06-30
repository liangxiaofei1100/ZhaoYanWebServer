package com.zhaoyan.webserver.resetpassword;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhaoyan.webserver.userinfomanage.UserManager;

/**
 * Servlet implementation class ResetPasswordAction
 */
@WebServlet("/ResetPasswordAction")
public class ResetPasswordAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ResetPasswordService mResetPasswordService;
	private UserManager mUserManager;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ResetPasswordAction() {
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
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);

		String respondMessage = resetPassword(request, response);
		
		request.setAttribute("result", respondMessage);
		request.getRequestDispatcher(
				request.getContextPath() + "/reset_password_result.jsp").forward(
				request, response);
	}

	private String resetPassword(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String respondMessage = "";
		String useridString = (String) request.getSession().getAttribute(
				"userid");
		String token = (String) request.getSession().getAttribute("token");
		String password = request.getParameter("password1");

		System.out.println("userid = " + useridString + ", token = " + token
				+ ", password = " + password);
		
		if (useridString == null || useridString.length() == 0 || token == null
				|| token.length() == 0 || password == null
				|| password.length() == 0) {
			respondMessage = "重置密码失败！无效的链接。";
			response.setStatus(400);
			return respondMessage;
		}

		int userId = Integer.valueOf(useridString);
		String username = mUserManager.getUserName(userId);
		
		System.out.println("username = " + username);
		
		if (mResetPasswordService.isRequestPasswordValid(username, token)) {
			// It is a valid reset password request.
			if (mResetPasswordService.resetPassword(username, password)) {
				// Reset password success.
				request.getSession().removeAttribute("userid");
				request.getSession().removeAttribute("token");

				mResetPasswordService.removeResetPasswordRequestFromDB(
						username, token);

				respondMessage = "重置密码成功！";
				response.setStatus(200);
			} else {
				// Reset password fail.
				respondMessage = "重置密码失败！服务器错误。";
				response.setStatus(400);
			}
		} else {
			// It is a invalid reset password request.
			respondMessage = "重置密码失败！链接已过期。";
			response.setStatus(400);
		}

		return respondMessage;
	}

	@Override
	public void init() throws ServletException {
		super.init();
		mResetPasswordService = new ResetPasswordDao();
		mUserManager = new UserManager();
	}
}
