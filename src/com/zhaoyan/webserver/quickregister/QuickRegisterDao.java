package com.zhaoyan.webserver.quickregister;

import java.util.ArrayList;

import com.zhaoyan.webserver.common.JDBCUtils;
import com.zhaoyan.webserver.db.DBData.UserInfoTable;

public class QuickRegisterDao implements QuickRegisterService {
	private JDBCUtils mJdbcUtils;

	public QuickRegisterDao() {
		mJdbcUtils = new JDBCUtils();
	}

	@Override
	public boolean registerUser(String username, String password) {
		boolean result = false;
		String sql = "insert into " + UserInfoTable.TABLE_NAME + "("
				+ UserInfoTable.USER_NAME + "," + UserInfoTable.PASSWORD
				+ ") values (?,?)";
		ArrayList<Object> params = new ArrayList<>();
		params.add(username);
		params.add(password);

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
