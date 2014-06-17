package com.zhaoyan.webserver.userinfomanage;

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

import net.sf.json.JSONObject;

import com.zhaoyan.webserver.db.DBData.UserInfoTable;

/**
 * Servlet implementation class GetUserInfoAction
 */
@WebServlet("/GetUserInfoAction")
public class GetUserInfoAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private GetUserInfoService mService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetUserInfoAction() {
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

		String respondMessage = getUserInfo(request, response);

		PrintWriter writer = response.getWriter();
		writer.print(respondMessage);
		writer.flush();
		writer.close();
	}

	private String getUserInfo(HttpServletRequest request,
			HttpServletResponse response) {
		String respondMessage = "";

		String username = request.getParameter("usernameOrEmail");

		List<Object> params = new ArrayList<>();
		params.add(username);
		params.add(username);
		Map<String, Object> map = mService.getUserInfo(params);

		if (map.isEmpty()) {
			respondMessage = "User not exist.";
			response.setStatus(400);
		} else {
			respondMessage = UserInfoUtil.userInfo2Json(map);
			response.setStatus(200);
		}
		return respondMessage;
	}

	@Override
	public void init() throws ServletException {
		super.init();
		mService = new GetUserInfoDAO();
	}

}
