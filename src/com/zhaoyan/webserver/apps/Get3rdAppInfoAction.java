package com.zhaoyan.webserver.apps;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Get3rdAppInfoAction
 */
@WebServlet("/Get3rdAppInfoAction")
public class Get3rdAppInfoAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Get3rdAppInfoService mGet3rdAppInfoService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Get3rdAppInfoAction() {
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

		String respondMessage = get3rdAppInfo(request, response);

		PrintWriter writer = response.getWriter();
		writer.print(respondMessage);
		writer.flush();
		writer.close();
	}

	private String get3rdAppInfo(HttpServletRequest request,
			HttpServletResponse response) {
		String respondMessage = "";
		List<Map<String, Object>> appInfos = mGet3rdAppInfoService
				.getAll3rdAppInfo();
		respondMessage = AppInfoUtil.appInfo2Json(appInfos);
		return respondMessage;
	}

	@Override
	public void init() throws ServletException {
		super.init();
		mGet3rdAppInfoService = new Get3rdAppInfoDao();
	}
}
