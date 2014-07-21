package com.zhaoyan.webserver.apps;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

public class AppInfoUtil {

	public static String appInfo2Json(List<Map<String, Object>> appInfos) {
		JSONArray jsonArray = JSONArray.fromObject(appInfos);
		return jsonArray.toString();
	}

}
