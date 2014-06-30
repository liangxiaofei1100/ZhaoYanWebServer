package com.zhaoyan.webserver.resetpassword;

import java.util.ArrayList;
import java.util.Map;

import com.zhaoyan.webserver.common.JDBCUtils;
import com.zhaoyan.webserver.db.DBData.UserInfoTable;
import com.zhaoyan.webserver.db.GetPasswordTable;

public class ResetPasswordDao implements ResetPasswordService {
	private JDBCUtils mJdbcUtils;

	public ResetPasswordDao() {
		mJdbcUtils = new JDBCUtils();
	}

	@Override
	public boolean resetPassword(String username, String password) {
		boolean result = false;

		String sql = "update " + UserInfoTable.TABLE_NAME + " set "
				+ UserInfoTable.PASSWORD + "=? where "
				+ UserInfoTable.USER_NAME + "=?";
		ArrayList<Object> params = new ArrayList<>();
		params.add(password);
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
	public boolean isRequestPasswordValid(String username, String token) {
		boolean result = false;
		String sql = "select * from " + GetPasswordTable.TABLE_NAME + " where "
				+ GetPasswordTable.USER_NAME + "=? and "
				+ GetPasswordTable.TOKEN + "=? and "
				+ GetPasswordTable.VALID_TIME + ">now()";
		ArrayList<Object> params = new ArrayList<>();
		params.add(username);
		params.add(token);
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
	public boolean removeResetPasswordRequestFromDB(String username,
			String token) {
		boolean result = false;
		String sql = "delete from " + GetPasswordTable.TABLE_NAME + " where "
				+ GetPasswordTable.USER_NAME + "=? and "
				+ GetPasswordTable.TOKEN + "=?";
		ArrayList<Object> params = new ArrayList<>();
		params.add(username);
		params.add(token);

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
