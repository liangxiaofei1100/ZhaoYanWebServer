package com.zhaoyan.webserver.userinfomanage;

import java.util.Map;

import net.sf.json.JSONObject;

import com.zhaoyan.webserver.db.DBData.UserInfoTable;

public class UserInfoUtil {

	public static String userInfo2Json(Map<String, Object> map) {
		String userName = (String) map.get(UserInfoTable.USER_NAME);
		String nickName = (String) map.get(UserInfoTable.NICKNAME);
		String email = (String) map.get(UserInfoTable.EMAIL);
		int jifen = (Integer) map.get(UserInfoTable.JIFEN);
		String phone = (String) map.get(UserInfoTable.PHONE);
		JSONObject user = new JSONObject();
		user.put("userName", userName);
		user.put("nickname", nickName);
		user.put("email", email);
		user.put("phone", phone);
		user.put("jifen", jifen);
		return user.toString();
	}

}
