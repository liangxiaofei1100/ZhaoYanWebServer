package com.zhaoyan.webserver.login;

import java.util.List;
import java.util.Map;

import com.zhaoyan.webserver.common.JDBCUtils;
import com.zhaoyan.webserver.db.DBData.UserInfoTable;

public class LoginDao implements LoginService {
	private JDBCUtils mJdbcUtils;

	public LoginDao() {
		mJdbcUtils = new JDBCUtils();
	}

	@Override
	public boolean login(List<Object> params) {
		boolean result = false;
		String sql = "select * from " + UserInfoTable.TABLE_NAME + " where ("
				+ UserInfoTable.USER_NAME + "=? or " + UserInfoTable.EMAIL
				+ "=?) and " + UserInfoTable.PASSWORD + "=?";

		try {
			mJdbcUtils.getConnection();
			Map<String, Object> map = mJdbcUtils.findSingleResult(sql, params);
			result = !map.isEmpty();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mJdbcUtils.releaseConnection();
		}
		return result;
	}

	@Override
	public boolean isUserExist(List<Object> params) {
		boolean result = false;
		String sql = "select * from " + UserInfoTable.TABLE_NAME + " where "
				+ UserInfoTable.USER_NAME + "=? or " + UserInfoTable.EMAIL
				+ "=?";

		try {
			mJdbcUtils.getConnection();
			Map<String, Object> map = mJdbcUtils.findSingleResult(sql, params);
			result = !map.isEmpty();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mJdbcUtils.releaseConnection();
		}
		return result;
	}
}
