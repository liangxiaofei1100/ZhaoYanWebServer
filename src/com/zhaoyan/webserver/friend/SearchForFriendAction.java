package com.zhaoyan.webserver.friend;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhaoyan.webserver.common.Logger;

/**
 * Servlet implementation class SearchForFriendAction
 */
public class SearchForFriendAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SearchForFriendService mService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchForFriendAction() {
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
		respondMessage = searchForFriend(request, response);

		PrintWriter writer = response.getWriter();
		writer.print(respondMessage);
		writer.flush();
		writer.close();
	}

	private String searchForFriend(HttpServletRequest request,
			HttpServletResponse response) {
		String respondMessage = "";
		String friendName = request.getParameter("fname");
		int friendId = -1;
		try {
			friendId = Integer.valueOf(request.getParameter("fid"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		if ((friendName == null || friendName.length() == 0)
				&& (friendId == -1)) {
			Logger.loge("searchFriend Parameter is empty");
			respondMessage = "Parameter is empty";
			response.setStatus(400);
			return respondMessage;
		}

		if (friendId != -1) {
			// Search by id
			Friend friend = mService.searchFriendById(friendId);
			respondMessage = FriendUtil.friendToJson(friend);
			response.setStatus(200);
		} else {
			// Search by name
			List<Friend> friends = mService.searchFriendByName(friendName);
			respondMessage = FriendUtil.friendToJson(friends);
			response.setStatus(200);
		}
		return respondMessage;
	}

	@Override
	public void init() throws ServletException {
		super.init();
		mService = new SearchForFriendDao();
	}

}
