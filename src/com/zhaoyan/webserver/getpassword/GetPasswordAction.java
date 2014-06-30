package com.zhaoyan.webserver.getpassword;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhaoyan.webserver.common.GetPasswordConfig;
import com.zhaoyan.webserver.common.SendMailUtils;

/**
 * Servlet implementation class GetPasswordAction
 */
@WebServlet("/GetPasswordAction")
public class GetPasswordAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private GetPasswordService mGetPasswordService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetPasswordAction() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if ("reset".equals(action)) {
			resetPassword(request, response);
		} else {
			doPost(request, response);
		}
	}

	private void resetPassword(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("userid");
		String token = request.getParameter("token");

		System.out.println("resetPassword userid = " + userId + ", token = "
				+ token);
		request.getSession().setAttribute("userid", userId);
		request.getSession().setAttribute("token", token);
		request.getRequestDispatcher(
				request.getContextPath() + "/reset_password.jsp").forward(
				request, response);
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

		request.getContextPath();

		String respondMessage = getPasswordViaEmail(request, response);

		PrintWriter writer = response.getWriter();
		writer.print(respondMessage);
		writer.flush();
		writer.close();
	}

	private String getPasswordViaEmail(HttpServletRequest request,
			HttpServletResponse response) {
		String respondMessage = "";

		String usernameOrEmail = request.getParameter("usernameOrEmail");

		List<Object> userNameOrEmailParams = new ArrayList<>();
		userNameOrEmailParams.add(usernameOrEmail);
		System.out.println("getPasswordViaEmail usernameOrEmail = "
				+ usernameOrEmail);
		String username = mGetPasswordService
				.getUserName(userNameOrEmailParams);
		if (username == null || "".equals(username)) {
			respondMessage = "User not exist.";
			response.setStatus(400);
			return respondMessage;
		}

		String email = mGetPasswordService.getEmail(userNameOrEmailParams);
		if (email.length() == 0) {
			respondMessage = "Email not exist.";
			response.setStatus(400);
			return respondMessage;
		}

		List<Object> userNameParams = new ArrayList<>();
		userNameParams.add(username);

		if (mGetPasswordService.isEmailAlreadySent(userNameParams)) {
			respondMessage = sendEmailAgain(request, response, username, email);
		} else {
			respondMessage = sendEmail(request, response, username, email);
		}

		return respondMessage;
	}

	private String sendEmail(HttpServletRequest request,
			HttpServletResponse response, String userName, String email) {
		String respondMessage = "";

		String token = GetPasswordConfig.generateToken();
		List<Object> addParams = new ArrayList<>();
		addParams.add(userName);
		addParams.add(email);
		addParams.add(token);
		if (mGetPasswordService.addSendEmailRecordIntoDB(addParams)) {
			List<Object> userIdParams = new ArrayList<>();
			userIdParams.add(userName);
			int userId = mGetPasswordService.getUserId(userIdParams);
			String url = getResetPasswordURL(userId, token, request
					.getRequestURL().toString());

			String validTime = mGetPasswordService.getValidTime(userName, email, token);
			if (sendEmail(userName, email, url, validTime)) {
				respondMessage = "Send email success.";
				response.setStatus(200);
			} else {
				respondMessage = "Send email fail";
				response.setStatus(400);
			}
		} else {
			respondMessage = "Server error.";
			response.setStatus(400);
		}
		return respondMessage;
	}

	private String sendEmailAgain(HttpServletRequest request,
			HttpServletResponse response, String userName, String email) {
		String respondMessage = "";

		if (!mGetPasswordService.updateSendEmailRecordDB(userName, email)) {
			respondMessage = "Send email again error.";
			response.setStatus(400);
			return respondMessage;
		}
		
		String token = mGetPasswordService.getToken(userName, email);
		String validTime = mGetPasswordService.getValidTime(userName, email, token);
		
		List<Object> userIdParams = new ArrayList<>();
		userIdParams.add(userName);
		int userId = mGetPasswordService.getUserId(userIdParams);
		
		String url = getResetPasswordURL(userId, token, request.getRequestURL()
				.toString());

		if (sendEmail(userName, email, url, validTime)) {
			respondMessage = "Send email success.";
			response.setStatus(200);
		} else {
			respondMessage = "Send email fail";
			response.setStatus(400);
		}
		return respondMessage;
	}

	private boolean sendEmail(String userName, String email, String url, String validTime) {
		boolean result = false;
		SendMailUtils mail = new SendMailUtils();
		mail.setTo(email);
		mail.setFrom(GetPasswordConfig.SEND_MAIL_USERNAME);
		mail.setHost(GetPasswordConfig.SEND_MAIL_HOST);
		mail.setUsername(GetPasswordConfig.SEND_MAIL_USERNAME);
		mail.setPassword(GetPasswordConfig.SEND_MAIL_PASSWORD);
		mail.setSubject(GetPasswordConfig.getEmailSubject());
		mail.setContent(GetPasswordConfig.getEmailContent(userName, url, validTime));
		if (mail.sendMail()) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	private String getResetPasswordURL(int userId, String token, String urlBase) {
		String url = urlBase + "?action=reset&userid=" + userId + "&token="
				+ token;
		return url;
	}

	@Override
	public void init() throws ServletException {
		super.init();
		mGetPasswordService = new GetPasswordDAO();
	}

}
