package com.zhaoyan.webserver.apps;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhaoyan.webserver.common.Logger;

/**
 * Servlet implementation class Update3rdAppAction
 */
@WebServlet("/Update3rdAppAction")
public class Update3rdAppAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Update3rdAppService mUpdate3rdAppService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Update3rdAppAction() {
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

		String respondMessage = update3rdApp(request, response);

		PrintWriter writer = response.getWriter();
		writer.print(respondMessage);
		writer.flush();
		writer.close();
	}

	private String update3rdApp(HttpServletRequest request,
			HttpServletResponse response) {
		String responseMessage = "";

		AppInfoParser appInfoParser = new AppInfoParser();
		String appInfoPath = getServletContext().getRealPath("/3rdApps/");
		List<AppInfo> appInfos = appInfoParser.parseAppInfo(appInfoPath);
		Logger.log("update3rdApp total " + appInfos.size());
		boolean result = mUpdate3rdAppService.update3rdApp(appInfos);
		if (result) {
			responseMessage = "Update success.";
			response.setStatus(200);
		} else {
			responseMessage = "Update fail.";
			response.setStatus(400);
		}
		return responseMessage;
	}

	@Override
	public void init() throws ServletException {
		super.init();
		mUpdate3rdAppService = new Update3rdAppDao();
	}

}
