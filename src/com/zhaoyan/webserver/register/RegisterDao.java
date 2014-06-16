package com.zhaoyan.webserver.register;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zhaoyan.webserver.common.JDBCUtils;
import com.zhaoyan.webserver.db.DBData.UserInfoTable;

public class RegisterDao implements RegisterService {
	private JDBCUtils mJdbcUtils;

	public RegisterDao() {
		mJdbcUtils = new JDBCUtils();
	}

	@Override
	public boolean registerUser(List<Object> params) {
		boolean result = false;
		mJdbcUtils.getConnection();
		String sql = "insert into " + UserInfoTable.TABLE_NAME + "("
				+ UserInfoTable.USER_NAME + "," + UserInfoTable.PASSWORD
				+ ") values (?,?)";
		try {
			result = mJdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mJdbcUtils.releaseConnection();
		}
		return result;
	}

	@Override
	public boolean isUserNameExist(String username) {
		boolean result = false;
		String sql = "select * from " + UserInfoTable.TABLE_NAME + " where "
				+ UserInfoTable.USER_NAME + "=?";
		List<Object> params = new ArrayList<>();
		params.add(username);
		
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
