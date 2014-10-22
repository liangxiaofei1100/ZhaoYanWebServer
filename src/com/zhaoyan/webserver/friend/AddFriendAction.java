package com.zhaoyan.webserver.friend;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhaoyan.webserver.common.Logger;

/**
 * Servlet implementation class AddFriendAction
 */
public class AddFriendAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AddFriendService mService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddFriendAction() {
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
		response.setContentType("text/html; charset=UTF-8");
		response.setStatus(HttpServletResponse.SC_OK);

		String respondMessage = "";
		respondMessage = addFriend(request, response);

		PrintWriter writer = response.getWriter();
		writer.print(respondMessage);
		writer.flush();
		writer.close();
	}

	private String addFriend(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String respondMessage = "";

		int userId = -1;
		int friendId = -1;
		try {
			userId = Integer.valueOf(request.getParameter("uid"));
			friendId = Integer.valueOf(request.getParameter("fid"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			Logger.loge("addFriend Parameter error." + e);
			respondMessage = "Parameter error";
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return respondMessage;
		}

		boolean result = mService.addFriend(userId, friendId);
		if (result) {
			respondMessage = "success";
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			respondMessage = "fail";
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}

		return respondMessage;
	}

	@Override
	public void init() throws ServletException {
		super.init();
		mService = new AddFriendDao();
	}

}
