package com.zhaoyan.webserver.friend;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.zhaoyan.webserver.common.Logger;

/**
 * Servlet implementation class GetFriendAction
 */
public class GetFriendAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private GetFriendService mService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetFriendAction() {
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
		respondMessage = getFriend(request, response);

		PrintWriter writer = response.getWriter();
		writer.print(respondMessage);
		writer.flush();
		writer.close();
	}

	private String getFriend(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String respondMessage = "";

		int userId = -1;
		try {
			userId = Integer.valueOf(request.getParameter("uid"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			Logger.loge("getFriend Parameter error." + e);
			respondMessage = "Parameter error";
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return respondMessage;
		}

		List<Friend> friends = mService.getFriend(userId);
		String friendsInfo = FriendUtil.friendToJson(friends);

		respondMessage = friendsInfo;
		response.setStatus(HttpServletResponse.SC_OK);

		return respondMessage;
	}

	@Override
	public void init() throws ServletException {
		super.init();
		mService = new GetFriendDao();
	}

}
