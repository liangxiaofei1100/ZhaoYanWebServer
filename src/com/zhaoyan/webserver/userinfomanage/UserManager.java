package com.zhaoyan.webserver.userinfomanage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhaoyan.webserver.common.JDBCUtils;
import com.zhaoyan.webserver.db.DBData.UserInfoTable;

public class UserManager {

	private JDBCUtils mJdbcUtils;

	public UserManager() {
		mJdbcUtils = new JDBCUtils();
	}

	/**
	 * Get the user name, if user not exist, "" will be returned.
	 * 
	 * @return
	 */
	public String getUserName(String userNameOrEmail) {
		String userName = "";

		Map<String, Object> map = getUserInfo(userNameOrEmail);
		if (!map.isEmpty()) {
			userName = (String) map.get(UserInfoTable.USER_NAME);
		}

		return userName;

	}

	/**
	 * 
	 * @return
	 */
	public boolean isUserExist(String userNameOrEmail) {
		String userName = getUserName(userNameOrEmail);
		return "".equals(userName);
	}

	/**
	 * 
	 * @param userNameOrEmail
	 * @return
	 */
	public Map<String, Object> getUserInfo(String userNameOrEmail) {
		Map<String, Object> map = new HashMap<String, Object>();

		String sql = "select * from " + UserInfoTable.TABLE_NAME + " where "
				+ UserInfoTable.USER_NAME + "=? or " + UserInfoTable.EMAIL
				+ "=?";
		List<Object> params = new ArrayList<>();
		params.add(userNameOrEmail);
		params.add(userNameOrEmail);
		try {
			mJdbcUtils.getConnection();
			map = mJdbcUtils.findSingleResult(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mJdbcUtils.releaseConnection();
		}
		return map;
	}

	/**
	 * Get the user name, if user not exist, "" will be returned.
	 * 
	 * @param id
	 * @return
	 */
	public String getUserName(int id) {
		String userName = "";

		Map<String, Object> map = getUserInfo(id);
		if (!map.isEmpty()) {
			userName = (String) map.get(UserInfoTable.USER_NAME);
		}

		return userName;
	}

	/**
	 * Get user info
	 * 
	 * @param id
	 * @return
	 */
	public Map<String, Object> getUserInfo(int id) {
		Map<String, Object> map = new HashMap<String, Object>();

		String sql = "select * from " + UserInfoTable.TABLE_NAME + " where "
				+ UserInfoTable.ID + "=?";
		List<Object> params = new ArrayList<>();
		params.add(id);

		try {
			mJdbcUtils.getConnection();
			map = mJdbcUtils.findSingleResult(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mJdbcUtils.releaseConnection();
		}
		return map;
	}
}
