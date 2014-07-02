package com.zhaoyan.webserver.userinfomanage;

import java.util.ArrayList;

import com.zhaoyan.webserver.common.JDBCUtils;
import com.zhaoyan.webserver.db.DBData.UserInfoTable;

public class ModifyUserInfoDao implements ModifyUserInfoService {
	private JDBCUtils mJdbcUtils;

	public ModifyUserInfoDao() {
		mJdbcUtils = new JDBCUtils();
	}

	@Override
	public boolean modifyPassword(String username, String newPassword) {
		boolean result = false;

		String sql = "update " + UserInfoTable.TABLE_NAME + " set "
				+ UserInfoTable.PASSWORD + "=? where "
				+ UserInfoTable.USER_NAME + "=?";
		ArrayList<Object> params = new ArrayList<>();
		params.add(newPassword);
		params.add(username);

		try {
			mJdbcUtils.getConnection();
			result = mJdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mJdbcUtils.releaseConnection();
		}

		return result;

	}

	@Override
	public boolean modifyEmail(String username, String email) {
		boolean result = false;

		String sql = "update " + UserInfoTable.TABLE_NAME + " set "
				+ UserInfoTable.EMAIL + "=? where " + UserInfoTable.USER_NAME
				+ "=?";
		ArrayList<Object> params = new ArrayList<>();
		params.add(email);
		params.add(username);

		try {
			mJdbcUtils.getConnection();
			result = mJdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mJdbcUtils.releaseConnection();
		}

		return result;

	}

	@Override
	public boolean modifyPhone(String username, String phone) {
		boolean result = false;

		String sql = "update " + UserInfoTable.TABLE_NAME + " set "
				+ UserInfoTable.PHONE + "=? where " + UserInfoTable.USER_NAME
				+ "=?";
		ArrayList<Object> params = new ArrayList<>();
		params.add(phone);
		params.add(username);

		try {
			mJdbcUtils.getConnection();
			result = mJdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mJdbcUtils.releaseConnection();
		}

		return result;

	}

}
