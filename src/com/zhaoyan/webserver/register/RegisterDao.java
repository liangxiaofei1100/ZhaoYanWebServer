package com.zhaoyan.webserver.register;

import java.util.List;

import com.zhaoyan.webserver.common.JDBCUtils;
import com.zhaoyan.webserver.db.DBData.UserInfoTable;

public class RegisterDao implements RegisterService {
	private JDBCUtils mJdbcUtils;

	public RegisterDao() {
		mJdbcUtils = new JDBCUtils();
	}

	@Override
	public boolean registerUser(List<Object> params) {
		boolean reuslt = false;
		mJdbcUtils.getConnection();
		String sql = "insert into " + UserInfoTable.TABLE_NAME + "("
				+ UserInfoTable.USER_NAME + "," + UserInfoTable.PASSWORD
				+ ") values (?,?)";
		try {
			reuslt = mJdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mJdbcUtils.releaseConnection();
		}
		return reuslt;
	}

}
