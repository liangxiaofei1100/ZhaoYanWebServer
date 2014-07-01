package com.zhaoyan.webserver.gold;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhaoyan.webserver.userinfomanage.UserManager;

/**
 * Servlet implementation class GoldAction
 */
@WebServlet("/GoldServlet")
public class GoldAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private GoldService mGoldService;
	private UserManager mUserManager;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GoldAction() {
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
		if ("add".equals(action)) {
			respondMessage = addGold(request, response);
		} else if ("sub".equals(action)) {
			respondMessage = subGold(request, response);
		} else if ("set".equals(action)) {
			respondMessage = setGold(request, response);
		} else {
			respondMessage = "Unkown action.";
			response.setStatus(400);
		}

		PrintWriter writer = response.getWriter();
		writer.print(respondMessage);
		writer.flush();
		writer.close();
	}

	private String setGold(HttpServletRequest request,
			HttpServletResponse response) {
		String respondMessage = "";

		String usernameOrEmail = request.getParameter("usernameOrEmail");
		String gold = request.getParameter("gold");

		String checkParameterResult = checkParameters(usernameOrEmail, gold);
		if (!"OK".equals(checkParameterResult)) {
			respondMessage = checkParameterResult;
			response.setStatus(400);
			return respondMessage;
		}

		String username = mUserManager.getUserName(usernameOrEmail);
		if ("".equals(username)) {
			respondMessage = "User not exist.";
			response.setStatus(400);
		}

		boolean result = mGoldService.setGold(username, gold);
		if (result) {
			respondMessage = "set gold:" + gold;
			response.setStatus(200);
		} else {
			respondMessage = "set gold fail.";
			response.setStatus(400);
		}
		return respondMessage;
	}

	private String subGold(HttpServletRequest request,
			HttpServletResponse response) {
		String respondMessage = "";

		String usernameOrEmail = request.getParameter("usernameOrEmail");
		String gold = request.getParameter("gold");

		String checkParameterResult = checkParameters(usernameOrEmail, gold);
		if (!"OK".equals(checkParameterResult)) {
			respondMessage = checkParameterResult;
			response.setStatus(400);
			return respondMessage;
		}

		String username = mUserManager.getUserName(usernameOrEmail);
		if ("".equals(username)) {
			respondMessage = "User not exist.";
			response.setStatus(400);
		}

		boolean result = mGoldService.subGold(username, gold);
		if (result) {
			respondMessage = "sub gold:" + gold;
			response.setStatus(200);
		} else {
			respondMessage = "sub gold fail.";
			response.setStatus(400);
		}
		return respondMessage;
	}

	private String addGold(HttpServletRequest request,
			HttpServletResponse response) {
		String respondMessage = "";

		String usernameOrEmail = request.getParameter("usernameOrEmail");
		String gold = request.getParameter("gold");

		String checkParameterResult = checkParameters(usernameOrEmail, gold);
		if (!"OK".equals(checkParameterResult)) {
			respondMessage = checkParameterResult;
			response.setStatus(400);
			return respondMessage;
		}

		String username = mUserManager.getUserName(usernameOrEmail);
		if ("".equals(username)) {
			respondMessage = "User not exist.";
			response.setStatus(400);
		}

		boolean result = mGoldService.addGold(username, gold);
		if (result) {
			respondMessage = "add gold:" + gold;
			response.setStatus(200);
		} else {
			respondMessage = "add gold fail.";
			response.setStatus(400);
		}
		return respondMessage;
	}

	private String checkParameters(String usernameOrEmail, String gold) {
		String result = "OK";
		if (usernameOrEmail == null || usernameOrEmail.length() == 0
				|| gold == null || gold.length() == 0) {
			result = "parameter is empty.";
		} else {
			try {
				int goldInt = Integer.valueOf(gold);
				if (goldInt < 0) {
					result = "parameter gold < 0.";
				}
			} catch (Exception e) {
				result = "parameter gold not integer.";
			}
		}
		return result;
	}

	@Override
	public void init() throws ServletException {
		super.init();
		mGoldService = new GoldDao();
		mUserManager = new UserManager();
	}

}
