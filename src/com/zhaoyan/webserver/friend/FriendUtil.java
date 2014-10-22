package com.zhaoyan.webserver.friend;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.zhaoyan.webserver.common.JDBCUtils;
import com.zhaoyan.webserver.db.DBData.UserInfoTable;

public class FriendUtil {

	public static Friend getFriendInfos(int friendId) {
		Friend friend = null;
		JDBCUtils jdbcUtils = new JDBCUtils();
		String sql = "select " + UserInfoTable.NICKNAME + " from "
				+ UserInfoTable.TABLE_NAME + " where " + UserInfoTable.ID + "="
				+ friendId;
		try {
			jdbcUtils.getConnection();
			Map<String, Object> result = jdbcUtils.findSingleResult(sql, null);
			if (!result.isEmpty()) {
				friend = new Friend();
				friend.id = friendId;
				friend.name = (String) result.get(UserInfoTable.NICKNAME);
				// TODO
				friend.headImagePath = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConnection();
		}
		return friend;
	}

	public static List<Friend> getFriendInfos(String friendName) {
		List<Friend> friends = new ArrayList<>();
		JDBCUtils jdbcUtils = new JDBCUtils();
		String sql = "select " + UserInfoTable.ID + " from "
				+ UserInfoTable.TABLE_NAME + " where " + UserInfoTable.NICKNAME
				+ "=?";
		List<Object> params = new ArrayList<>();
		params.add(friendName);
		try {
			jdbcUtils.getConnection();
			List<Map<String, Object>> results = jdbcUtils.findMultiResult(sql,
					params);
			for (Map<String, Object> map : results) {
				Friend friend = new Friend();
				friend.id = Integer.valueOf(map.get(UserInfoTable.ID)
						.toString());
				friend.name = friendName;
				// TODO
				friend.headImagePath = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConnection();
		}
		return friends;
	}

	public static String friendToJson(List<Friend> friends) {
		String friendsInfo = "";
		if (friends == null || friends.isEmpty()) {
			return friendsInfo;
		}

		JSONArray friendsJsonArray = new JSONArray();
		for (Friend friend : friends) {
			JSONObject friendJsonObject = new JSONObject();
			friendJsonObject.put("fid", friend.id);
			friendJsonObject.put("fname", friend.name);
			friendJsonObject.put("fhead", friend.headImagePath);
			friendsJsonArray.add(friends);
		}
		friendsInfo = friendsJsonArray.toString();
		return friendsInfo;
	}

	public static String friendToJson(Friend friend) {
		String friendInfo = "";
		if (friend == null) {
			return friendInfo;
		}

		JSONObject friendJsonObject = new JSONObject();
		friendJsonObject.put("fid", friend.id);
		friendJsonObject.put("fname", friend.name);
		friendJsonObject.put("fhead", friend.headImagePath);
		friendInfo = friendJsonObject.toString();

		return friendInfo;
	}
}
