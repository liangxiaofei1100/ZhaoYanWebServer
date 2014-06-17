package com.zhaoyan.webserver.userinfomanage;

import java.util.Map;

import net.sf.json.JSONObject;

import com.zhaoyan.webserver.db.DBData.UserInfoTable;

public class UserInfoUtil {

	public static String userInfo2Json(Map<String, Object> map) {
		String userName = (String) map.get(UserInfoTable.USER_NAME);
		String email = (String) map.get(UserInfoTable.EMAIL);
		int gold = (Integer) map.get(UserInfoTable.GOLD);
		String phone = (String) map.get(UserInfoTable.PHONE);
		JSONObject user = new JSONObject();
		user.put("userName", userName);
		user.put("email", email);
		user.put("phone", phone);
		user.put("gold", gold);
		return user.toString();
	}

}
